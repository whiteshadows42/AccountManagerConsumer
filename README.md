# Account Manager Consumer
Esta Interface Vaadin é um consumidor de um API REST que simula um sistema de back-office bancário  
voltado para gestão de contas e movimentações financeiras de clientes. 

## TECNOLOGIAS
É uma aplicação com um setup mínimo, contendo as seguints tecnologias:
* Java 17
* Vaadin 24.7.5
* Spring Boot 3.4.5
  
## Endpoints
A aplicação consome os seguintes endpoints:
* Cadastrar novo cliente : POST (http://localhost:8080/api/v1/clients)
* Cadastrar nova conta bancária pra cliente existente: POST(http://localhost:8080/api/v1/accounts)
* Consultar saldo da conta cadastrada : GET (http://localhost:8080/api/v1/accounts/{id}/balance)
* Realizar movimentação entre contas: POST(http://localhost:8080/api/v1/transactions)
* Consultar extrato de movimentações : GET (http://localhost:8080/api/v1/accounts/{id}/transactions?startDate=...&endDate=...)

## Fazendo Build da Aplicação e rodando a aplicação
Para realizar o build e rodar a aplicação deve executar o seguinte comando:
* Em modo desenvolvimento:
```bash
./mvnw
```
* Em modo produção:
```bash
./mvnw -Pproduction package
```
