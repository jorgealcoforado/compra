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
public class ClienteFielDTO {
    private String nome;
    private String cpf;
    private int totalCompras;
    private int totalItensComprados;
    private BigDecimal valorTotalGasto;

    public String getCpf() {
        return FormatadorUtil.formatarCpf(cpf);
    }

    public String getValorTotalFormatado() {
        return FormatadorUtil.formatarMoeda(valorTotalGasto);
    }
}
