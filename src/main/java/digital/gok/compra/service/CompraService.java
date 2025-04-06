package digital.gok.compra.service;

import digital.gok.compra.dto.ClienteFielDTO;
import digital.gok.compra.dto.CompraDTO;
import digital.gok.compra.dto.RecomendacaoVinhoDTO;
import digital.gok.compra.exception.NotFoundException;
import digital.gok.compra.mapper.CompraMapper;
import digital.gok.compra.model.Cliente;
import digital.gok.compra.model.Compra;
import digital.gok.compra.model.Produto;
import digital.gok.compra.repository.CompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository repository;

    private final CompraMapper compraMapper;

    @Cacheable("compras-ordenadas")
    public List<CompraDTO> getComprasOrdenadas() {
        return repository.getClientes().stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(c -> Compra.of(cliente, c, repository.getProdutoMap())))
                .sorted(Comparator.comparing(Compra::getValorTotal))
                .map(compraMapper::toDTO)
                .toList();
    }

    @Cacheable(value = "maior-compra", key = "#ano")
    public CompraDTO getMaiorCompraDoAno(int ano) {
        return repository.getClientes().stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(c -> Compra.of(cliente, c, repository.getProdutoMap())))
                .filter(c -> c.getProduto().getAnoCompra().equals(ano))
                .max(Comparator.comparing(Compra::getValorTotal))
                .map(compraMapper::toDTO)
                .orElse(null);
    }

    @Cacheable("clientes-fieis")
    public List<ClienteFielDTO> getTop3ClientesFieis() {
        return repository.getClientes().stream()
                .map(cliente -> {
                    BigDecimal valorTotal = BigDecimal.ZERO;
                    int itens = 0;

                    for (var c : cliente.getCompras()) {
                        var produto = repository.getProdutoMap().get(Integer.parseInt(c.getCodigo()));
                        if (produto != null) {
                            valorTotal = valorTotal.add(produto.getPreco().multiply(BigDecimal.valueOf(c.getQuantidade())));
                            itens += c.getQuantidade();
                        }
                    }

                    return ClienteFielDTO.builder()
                            .nome(cliente.getNome())
                            .cpf(cliente.getCpf())
                            .totalCompras(cliente.getCompras().size())
                            .totalItensComprados(itens)
                            .valorTotalGasto(valorTotal)
                            .build();
                })
                .sorted(Comparator.comparing(ClienteFielDTO::getValorTotalGasto).reversed())
                .limit(3)
                .toList();
    }

    @Cacheable(value = "recomendacao", key = "#cpf")
    public RecomendacaoVinhoDTO recomendarTipoVinho(String cpf) {
        Cliente cliente = repository.getClientes().stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        Map<String, Long> tipoContagem = new HashMap<>();
        Map<String, BigDecimal> tipoValor = new HashMap<>();
        long totalItens = 0;

        for (var compra : cliente.getCompras()) {
            Produto produto = repository.getProdutoMap().get(Integer.parseInt(compra.getCodigo()));
            if (produto == null) continue;

            String tipo = produto.getTipoVinho();
            long qtd = compra.getQuantidade();
            BigDecimal valor = produto.getPreco().multiply(BigDecimal.valueOf(qtd));

            tipoContagem.put(tipo, tipoContagem.getOrDefault(tipo, 0L) + qtd);
            tipoValor.put(tipo, tipoValor.getOrDefault(tipo, BigDecimal.ZERO).add(valor));
            totalItens += qtd;
        }

        var tipoMaisComprado = tipoContagem.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new NotFoundException("Nenhuma compra encontrada"));

        String tipo = tipoMaisComprado.getKey();
        long qtd = tipoMaisComprado.getValue();
        BigDecimal valor = tipoValor.get(tipo);
        double percentual = totalItens > 0 ? (qtd * 100.0 / totalItens) : 0;

        return RecomendacaoVinhoDTO.builder()
                .nomeCliente(cliente.getNome())
                .cpf(cliente.getCpf())
                .tipoMaisComprado(tipo)
                .quantidadeComprada(qtd)
                .totalGasto(valor)
                .percentualPreferencia(Math.round(percentual * 100.0) / 100.0)
                .build();
    }

}