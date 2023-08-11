<h1 align="center">
  LoanGenius API
</h1>

<p align="center">
 <img src="https://img.shields.io/static/v1?label=Dev&message=Kevin Rodrigues&color=8257E5&labelColor=000000" alt="@Antikevin" />
 <img src="https://img.shields.io/static/v1?label=Tipo&message=Projeto Open-Source&color=8257E5&labelColor=000000" alt="projeto" />
</p>

<p align="center">
<img src="https://socialify.git.ci/antikevin/LoanGeniusAPI/image?description=1&descriptionEditable=Calculador%20e%20Organizador%20de%20empr%C3%A9stimos%20e%20investimentos%20pessoais&font=Inter&forks=1&issues=1&language=1&name=1&pattern=Solid&pulls=1&stargazers=1&theme=Light" alt="LoanGeniusAPI" width="640" height="320"/>
</p>

API para gerenciar, calcular e organizar empréstimos e investimentos pessoais via (CRUD) com spring boot.

## Tecnologias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
- [JWT](https://jwt.io/libraries?language=Java)

## Práticas adotadas

- Layers Architecture
- SOLID, DRY, YAGNI, KISS
- API REST
- Consultas com Spring Data JPA
- Injeção de Dependências
- Tratamento de respostas de erro
- Geração automática do Swagger com a OpenAPI 3
- Geração de Token JWT para segurança da aplicação

## Como Executar

- Clonar repositório git
- Construir o projeto:
```
$ ./mvnw clean package
```
- Executar a aplicação:
```
$ java -jar <...caminhoParaSeuJar>
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080).
O Swagger poderá ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
para mais informações e testes de endpoints abra o swagger da aplicação citado acima.
