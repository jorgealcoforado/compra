package digital.gok.compra.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import digital.gok.compra.model.Cliente;
import digital.gok.compra.model.Produto;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CompraRepository {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Getter
    private List<Produto> produtos;

    @Getter
    private List<Cliente> clientes;

    @Getter
    private final Map<Integer, Produto> produtoMap = new HashMap<>();

    @PostConstruct
    public void carregarDados() throws Exception {
        String produtosJson = restTemplate.getForObject("https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json", String.class);
        String clientesJson = restTemplate.getForObject("https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json", String.class);

        produtos = objectMapper.readValue(produtosJson, new TypeReference<>() {});
        clientes = objectMapper.readValue(clientesJson, new TypeReference<>() {});

        for (Produto p : produtos) {
            produtoMap.put(p.getCodigo(), p);
        }
    }
}