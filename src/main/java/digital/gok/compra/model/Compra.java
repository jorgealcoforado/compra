package digital.gok.compra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    private String nomeCliente;
    private String cpf;
    private Produto produto;
    private int quantidade;
    private BigDecimal valorTotal;

    public static Compra of(Cliente cliente, CompraRaw cr, Map<Integer, Produto> produtoMap) {
        int codigo = Integer.parseInt(cr.getCodigo());
        Produto produto = produtoMap.get(codigo);
        return new Compra(
                cliente.getNome(),
                cliente.getCpf(),
                produto,
                cr.getQuantidade(),
                produto.getPreco().multiply(BigDecimal.valueOf(cr.getQuantidade()))
        );
    }

}