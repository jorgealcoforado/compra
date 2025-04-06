package digital.gok.compra.controller;

import digital.gok.compra.dto.ClienteFielDTO;
import digital.gok.compra.dto.CompraDTO;
import digital.gok.compra.dto.RecomendacaoVinhoDTO;
import digital.gok.compra.service.CompraService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
public class CompraController {

    private final CompraService compraService;

    @GetMapping("compras")
    public ResponseEntity<List<CompraDTO>> listarComprasOrdenadas() {
        List<CompraDTO> compras = compraService.getComprasOrdenadas();
        return compras.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(compras);
    }

    @GetMapping("maior-compra/{ano}")
    public ResponseEntity<CompraDTO> maiorCompraPorAno(@PathVariable int ano) {
        CompraDTO compra = compraService.getMaiorCompraDoAno(ano);
        return compra != null
                ? ResponseEntity.ok(compra)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("clientes-fieis")
    public ResponseEntity<List<ClienteFielDTO>> top3ClientesFieis() {
        List<ClienteFielDTO> clientes = compraService.getTop3ClientesFieis();
        return clientes.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(clientes);
    }

    @GetMapping("recomendacao/{cpf}/tipo")
    public ResponseEntity<RecomendacaoVinhoDTO> recomendarVinhoPorTipo(
            @PathVariable
            @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
            String cpf) {

        RecomendacaoVinhoDTO recomendacao = compraService.recomendarTipoVinho(cpf);
        return recomendacao != null
                ? ResponseEntity.ok(recomendacao)
                : ResponseEntity.notFound().build();
    }
}
