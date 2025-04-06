package digital.gok.compra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private String nome;
    private String cpf;
    private List<CompraRaw> compras;
}
