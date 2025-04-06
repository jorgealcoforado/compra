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
public class RecomendacaoVinhoDTO {
    private String nomeCliente;
    private String cpf;
    private String tipoMaisComprado;
    private long quantidadeComprada;
    private BigDecimal totalGasto;
    private double percentualPreferencia;

    public String getCpf() {
        return FormatadorUtil.formatarCpf(cpf);
    }

    public String getTotalGastoFormatado() {
        return FormatadorUtil.formatarMoeda(totalGasto);
    }

    public String getPercentualFormatado() {
        return FormatadorUtil.formatarPercentual(percentualPreferencia);
    }
}
