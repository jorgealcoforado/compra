package digital.gok.compra.mapper;

import digital.gok.compra.dto.CompraDTO;
import digital.gok.compra.model.Compra;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProdutoMapper.class)
public interface CompraMapper {
    CompraDTO toDTO(Compra compra);
}
