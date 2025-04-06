package digital.gok.compra.service;

import digital.gok.compra.dto.ClienteFielDTO;
import digital.gok.compra.dto.CompraDTO;
import digital.gok.compra.dto.RecomendacaoVinhoDTO;
import digital.gok.compra.exception.NotFoundException;
import digital.gok.compra.mapper.CompraMapper;
import digital.gok.compra.model.Cliente;
import digital.gok.compra.model.Compra;
import digital.gok.compra.model.CompraRaw;
import digital.gok.compra.model.Produto;
import digital.gok.compra.repository.CompraRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private CompraRepository repository;

    @Mock
    private CompraMapper mapper;

    @InjectMocks
    private CompraService service;

    @Test
    void deveGetComprasOrdenadasSucesso() {
        Cliente cliente = new Cliente("João", "12345678901", List.of(new CompraRaw("1", 2)));
        Produto produto = new Produto(1, "Tinto", BigDecimal.TEN, "2020", 2021);
        Compra compra = new Compra("João", "12345678901", produto, 2, BigDecimal.valueOf(20));
        CompraDTO compraDTO = CompraDTO.builder().cpf("12345678901").build();

        when(repository.getClientes()).thenReturn(List.of(cliente));
        when(repository.getProdutoMap()).thenReturn(Map.of(1, produto));
        when(mapper.toDTO(any(Compra.class))).thenReturn(compraDTO);

        var result = service.getComprasOrdenadas();

        assertEquals(1, result.size());
        verify(mapper, times(1)).toDTO(any());
    }

    @Test
    void deveGetMaiorCompraDoAnoSucesso() {
        Cliente cliente = new Cliente("Maria", "12345678901", List.of(new CompraRaw("2", 3)));
        Produto produto = new Produto(2, "Branco", BigDecimal.valueOf(100), "2021", 2023);
        Compra compra = new Compra("Maria", "12345678901", produto, 3, BigDecimal.valueOf(300));
        CompraDTO dto = CompraDTO.builder().cpf("12345678901").build();

        when(repository.getClientes()).thenReturn(List.of(cliente));
        when(repository.getProdutoMap()).thenReturn(Map.of(2, produto));
        when(mapper.toDTO(any(Compra.class))).thenReturn(dto);

        CompraDTO result = service.getMaiorCompraDoAno(2023);

        assertNotNull(result);
        verify(mapper).toDTO(any());
    }

    @Test
    void deveGetTop3ClientesFieisSucesso() {
        Cliente cliente = new Cliente("João", "12345678901", List.of(new CompraRaw("1", 2)));
        Produto produto = new Produto(1, "Tinto", BigDecimal.TEN, "2020", 2021);

        when(repository.getClientes()).thenReturn(List.of(cliente));
        when(repository.getProdutoMap()).thenReturn(Map.of(1, produto));

        List<ClienteFielDTO> result = service.getTop3ClientesFieis();

        assertEquals(1, result.size());
        assertEquals("João", result.get(0).getNome());
    }

    @Test
    void deveRecomendarTipoVinhoSucesso() {
        Cliente cliente = new Cliente("Ana", "98765432100", List.of(new CompraRaw("2", 5)));
        Produto produto = new Produto(2, "Branco", BigDecimal.valueOf(50), "2022", 2022);

        when(repository.getClientes()).thenReturn(List.of(cliente));
        when(repository.getProdutoMap()).thenReturn(Map.of(2, produto));

        RecomendacaoVinhoDTO dto = service.recomendarTipoVinho("98765432100");

        assertEquals("Ana", dto.getNomeCliente());
        assertEquals("Branco", dto.getTipoMaisComprado());
        assertEquals(5, dto.getQuantidadeComprada());
    }

    @Test
    void deveRecomendarTipoVinhoFalhaQuandoClienteNaoEncontrado() {
        when(repository.getClientes()).thenReturn(List.of());

        assertThrows(NotFoundException.class, () -> service.recomendarTipoVinho("00000000000"));
    }
}
