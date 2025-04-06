package digital.gok.compra.mapper;

import digital.gok.compra.dto.ProdutoDTO;
import digital.gok.compra.model.Produto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    ProdutoDTO toDTO(Produto produto);
}
