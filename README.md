---

## 📦 Projeto `compra` - Microserviço de Compras com Spring Boot 3 + Redis

Este projeto implementa um microserviço REST para gerenciar e analisar dados de **clientes**, **produtos** e **compras**, utilizando as melhores práticas com **Spring Boot 3**, **Java 17** e **Redis** para cache. A arquitetura está dividida em camadas claras com uso de **MapStruct** para mapeamento e **Lombok** para reduzir o boilerplate.

---

## 🚀 Endpoints Disponíveis

### `GET /compras`
Lista todas as compras ordenadas pelo valor total (crescente).

📦 **Retorna**: Nome do cliente, CPF formatado, produto comprado, quantidade e valor total formatado.

---

### `GET /maior-compra/{ano}`
Busca a maior compra realizada em determinado ano.

📅 **Parâmetro**: ano (ex: 2021)  
📦 **Retorna**: Dados completos da compra com maior valor.

---

### `GET /clientes-fieis`
Lista os **Top 3 clientes mais fiéis**, considerando volume de itens e total gasto.

📦 **Retorna**: Nome, CPF formatado, total de compras, quantidade de itens e valor total gasto formatado.

---

### `GET /recomendacao/{cpf}/tipo`
Sugere o tipo de vinho mais comprado por um cliente específico com base em seu histórico de compras.

🧍 **Parâmetro**: CPF (11 dígitos)  
📦 **Retorna**: Nome, CPF formatado, tipo mais comprado, quantidade, total gasto e percentual de preferência.

---

## 🛠️ Tecnologias Utilizadas

- ✅ Java 17
- ✅ Spring Boot 3
- ✅ Spring Web
- ✅ Spring Cache + Redis
- ✅ Lombok
- ✅ MapStruct
- ✅ Jackson
- ✅ JUnit 5 + Mockito
- ✅ SpringDoc OpenAPI (Swagger)

---

## ⚙️ Como Executar Localmente

### 1. Suba o Redis com Docker:
```bash
docker run --name redis -p 6379:6379 -d redis
```

### 2. Clone o repositório
```bash
git clone <url-do-projeto>
cd compra
```

### 3. Compile e execute a aplicação
```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em:  
🌐 [http://localhost:8080](http://localhost:8080)

---

## 📂 Estrutura do Projeto

```
src/main/java/digital/gok/compra
├── CompraApplication.java
├── config
│   └── RedisConfig.java
├── controller
│   └── CompraController.java
├── dto
│   └── CompraDTO, ClienteFielDTO, ProdutoDTO, RecomendacaoVinhoDTO
├── exception
│   └── GlobalExceptionHandler.java, NotFoundException.java
├── mapper
│   └── CompraMapper.java
├── model
│   └── Produto.java, Cliente.java, CompraRaw.java, Compra.java
├── repository
│   └── CompraRepository.java
├── service
│   └── CompraService.java
├── util
│   └── FormatadorUtil.java
```

---

## ✅ Testes

Os testes unitários estão localizados em:

```
src/test/java/digital/gok/compra/service/CompraServiceTest.java
```

Cobrem todos os fluxos de sucesso e falha usando **JUnit 5** e **Mockito**.

---