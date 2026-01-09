# API Spring Boot – Arquitetura DDD

API desenvolvida em **Java com Spring Boot**, utilizando **arquitetura DDD (Domain Driven Design)**, com persistência de dados em **MySQL (JPA/Hibernate)** e **MongoDB** para dados auxiliares e monitoramento.

---

## Competências utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- MongoDB
- Maven
- Hibernate
- Lombok
- RabbitMQ 3.13.7
- MailTrap
- CI/CD
- Docker

---

## Arquitetura do Projeto

O projeto segue os princípios do **DDD (Domain Driven Design)**, separando responsabilidades por camadas.

---

## Banco de Dados

- **MySQL**
    - Utilizado para dados relacionais principais
    - Persistência via JPA/Hibernate

- **MongoDB**
    - Utilizado para dados não relacionais
    - Monitoramento, logs ou controle de API Keys

---
## Configuração

Mude o nome do arquivo `application-example.yml` para `application.yml`. Altere as credenciais de conexão ao banco de dados.