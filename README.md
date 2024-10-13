# 🔐 Socket TCP com Criptografia RSA

Bem-vindo ao **API Socket com Criptografia RSA**! Este projeto implementa a comunicação entre cliente e servidor utilizando Sockets TCP, com criptografia RSA para garantir a segurança da troca de mensagens. 🔒

## 🚀 Execução do Projeto

Para iniciar a comunicação segura entre o cliente e o servidor, siga os passos abaixo:

Compile o projeto: Compile as classes Server.java e Client.java usando o compilador Java:

javac Server.java Client.java
Inicie o Servidor: Primeiro, você precisa iniciar o servidor, que ficará aguardando conexões de clientes.

java Server
O servidor irá gerar uma chave pública e privada para a criptografia RSA.

Inicie o Cliente: Agora, execute o cliente que se conectará ao servidor e enviará uma mensagem criptografada.

java Client
Envio da Mensagem: Assim que a conexão for estabelecida, o cliente enviará a mensagem "Tranquilo", que será criptografada e enviada ao servidor para ser descriptografada.

Resposta: O servidor recebe a mensagem criptografada, a descriptografa usando sua chave privada e exibe o resultado.

# 🔑 Tecnologias Utilizadas
Java: Linguagem utilizada para implementar a comunicação socket.
Criptografia RSA: Utilizada para garantir a segurança na troca de mensagens entre o cliente e o servidor.
Sockets TCP/IP: Protocolo de comunicação utilizado para estabelecer a conexão entre o cliente e o servidor.

# 📁 Estrutura do Projeto

src/
├── Server.java
├── Client.java
└── README.md








