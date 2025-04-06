package digital.gok.compra.dto;

import digital.gok.compra.util.FormatadorUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraDTO {
    private String nomeCliente;
    private String cpf;
    private ProdutoDTO produto;
    private int quantidade;
    private BigDecimal valorTotal;

    public String getCpf() {
        return FormatadorUtil.formatarCpf(cpf);
    }

    public String getValorTotalFormatado() {
        return FormatadorUtil.formatarMoeda(valorTotal);
    }
}
