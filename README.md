## Messaging Project

Este projeto é uma aplicação Backend desenvolvida com Spring Boot, focado no uso de mensageria para o processamento assíncrono do processo de envio de emails.

## Arquitetura
O projeto consiste em um serviço que faz o gerenciamento dos usuários, e se comunica através do rabbitmq com outro serviço que consome essas mensagens,
e faz o envio de email através do mailtrap (sandbox) para o usuário que foi criado, atualizado ou deletado informando essa mudança.

![Arquitetura](images/Stream.drawio.png)

## Funcionalidades
- CRUD de Usuários
- Autenticação JWT
- Swagger/OpenAPI
- RabbitMQ
- Worker Consumidor
- Envio de Email
- MailTrap sandbox (teste)

## Swagger

## Como Executar

### Requisitos 
- Docker
- Docker Compose
- Ter uma conta no mailtrap

### 1. Abra o terminal 

### 2. Clone o Repositório

```bash
git clone https://github.com/MatheusHenriqueSouzaSantos/MessagingProject
```

### 3. Entre na pasta do projeto

```bash
cd MessagingProject
```

### 4. Criando o Arquivo das Variáveis de Ambiente (Com base no arquivo de exemplo)

#### Linux
```bash
cp .env.example .env
```

#### Windows 
```cmd
copy .env.example .env  
```

### 5. contruindo...

