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
public class ProdutoDTO {
    private Integer codigo;
    private String tipoVinho;
    private BigDecimal preco;
    private String safra;
    private Integer anoCompra;

    public String getPrecoFormatado() {
        return FormatadorUtil.formatarMoeda(preco);
    }
}
