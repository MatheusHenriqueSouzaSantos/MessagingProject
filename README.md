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
![swagger Image](images/swaggerIterface.png)

## Como Executar

### Requisitos 
- Ter uma conta no mailtrap
- Docker
- Docker Compose <br/>
obs: se Windows, o docker desktop já tem o docker e o docker compose, basta inicia-lo e seguir o processo

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

### 5. Acesse o mailtrap e pega as credencias da sua sandbox
Você deverá acessar o mailtrap e na barra lateral ou na tela principal mesmo deverá acessar sandboxes e,
acessar a mysandbox, nessa área você irá obter o seu username e o password, isso é necessário para a aplicação
acessar o mailtrap e conseguir manda um email via smtp.


### 6. Acesse o arquivo .env e Adicione os Valores das Variáveis de Ambiente
terá essas linhas: <br/>
-MAIL_USERNAME:YOUR_MAILTRAP_USERNAME <br/>
-MAIL_PASSWORD:YOUR_MAIL_TRAP_PASSWORD <br/>
a qual você deverá subistituir os valores após os dois pontos pelas credencias que você obteve no seu mailtrap

### 7. Rodar a aplicação
Após isso, basta abrir o terminal e executar o seguinte comando:

```bash
docker compose up --build
```

Agora a aplicação já esta rodando na porta 8080, e as rotas podem ser acessadas via swaggerUI em: http://localhost:8080/swagger-ui/index.html
