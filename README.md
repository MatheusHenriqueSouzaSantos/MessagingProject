## Messaging Project

Este projeto é uma aplicação Backend desenvolvida com Spring Boot, focado no uso de mensageria para o processamento assíncrono do processo de envio de emails.

O projeto consiste em um serviço que faz o gerenciamento dos usuários, e se comunica através do rabbitmq com outro serviço que consome essas mensagens,
e faz o envio de email através do mailtrap para o usuário que foi criado, atualizado ou deletado informando essa mudança.
