# 🚀 Estudo de Microserviços: User & Email com RabbitMQ

Este repositório é um projeto prático focado na comunicação assíncrona entre microserviços utilizando **RabbitMQ** e o ecossistema **Spring Boot 3**.

O objetivo é demonstrar como desacoplar responsabilidades: o serviço de usuários foca no cadastro, enquanto o serviço de e-mail processa notificações em segundo plano (background jobs).

---

## 📐 Arquitetura do Sistema

O sistema utiliza o padrão **Producer-Consumer** mediado por um Broker:

1.  **User (Producer)**: Ao cadastrar um novo usuário, publica uma mensagem JSON na fila.
2.  **RabbitMQ (Broker)**: Gerencia a fila e garante a entrega da mensagem.
3.  **Email (Consumer)**: Escuta a fila e processa o envio do e-mail de boas-vindas.



---

## 🛠️ Tecnologias e Versões

* **Java 21**: Versão LTS.
* **Spring Boot 3.4+**: Framework base.
* **Spring AMQP** (RabbitMQ).
* **PostgreSQL**: Banco de dados relacional.
* **CloudAMQP**: Instância RabbitMQ gerenciada na nuvem.
* **Jackson 3.x**: Serialização JSON moderna (pacote `tools.jackson`).

---

## 🔄 Fluxo da Aplicação

1.  **Request**: O cliente envia um `POST` para criar um usuário.
2.  **Persistência**: O `User` salva os dados no banco PostgreSQL.
3.  **Evento**: O `User` dispara um evento para a fila `email-queue`.
4.  **Processamento**: O `Email` captura a mensagem, converte o JSON e envia o e-mail.

---

## ⚙️ Configuração

### 1. Variáveis de Ambiente (RabbitMQ)
Configure o arquivo `application.yml` em **ambos os serviços** com as credenciais do CloudAMQP.
*Nota: Para conexões externas seguras, recomenda-se o uso da porta 5671 e protocolo amqps.*

```yaml
spring:
  rabbitmq:
    addresses: amqps://sua-url-cloudamqp.com:5671
    username: seu-usuario
    password: sua-senha
    virtual-host: seu-vhost
```

### 2. Banco de Dados (Service User)
No serviço de usuários, configure a conexão com o PostgreSQL:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ms-user-db
    username: postgres
    password: sua_senha
  jpa:
    hibernate:
      ddl-auto: update
```

## 🚀 Como Executar

### 1. Clone o repositório:

  ```Bash
    git clone [https://github.com/seu-usuario/nome-do-projeto.git](https://github.com/seu-usuario/nome-do-projeto.git)
```
### 2. Ordem de Inicialização:

* 1º Passo: Inicie o serviço Email (Consumer). Isso garante que o RabbitAdmin crie as filas automaticamente ao subir.

* 2º Passo: Inicie o serviço User (Producer).

### 3. Teste de API: Envie uma requisição para cadastrar um usuário:
  ```Bash
    curl -X POST http://localhost:8080/users \
    -H "Content-Type: application/json" \
    -d '{ "name": "Filipe", "email": "filipe@teste.com" }'
```

## 🧠 Desafios e Aprendizados

Este projeto abordou cenários importantes de integração:

* Infraestrutura como Código: Utilização de @Bean para declaração automática de filas, exchanges e bindings.

* Conexão Segura: Configuração de SSL/TLS (AMQPS) para comunicação com brokers na nuvem (CloudAMQP).

* Atualização de Dependências: Adaptação para o novo ecossistema do Jackson 3 (tools.jackson), resolvendo avisos de depreciação das versões anteriores.

Desenvolvido por Filipe