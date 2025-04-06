package digital.gok.compra.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    private Integer codigo;

    @JsonProperty("tipo_vinho")
    private String tipoVinho;

    private BigDecimal preco;
    private String safra;

    @JsonProperty("ano_compra")
    private Integer anoCompra;

}