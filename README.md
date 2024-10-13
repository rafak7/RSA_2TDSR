# 🔐 Socket TCP com Criptografia RSA

Bem-vindo ao **API Socket com Criptografia RSA**! Este projeto implementa a comunicação entre cliente e servidor utilizando Sockets TCP, com criptografia RSA para garantir a segurança da troca de mensagens. 🔒

# 🚀 Execução do Projeto
Para iniciar a comunicação segura entre o cliente e o servidor, siga os passos abaixo:

Compile o projeto: Compile as classes Server.java e Client.java usando o compilador Java:

javac Server.java Client.java
Inicie o Servidor: Execute o servidor, que ficará aguardando conexões de clientes:

java Server
O servidor irá gerar um par de chaves RSA (pública e privada) para criptografar e descriptografar as mensagens.

Inicie o Cliente: Agora, execute o cliente, que se conectará ao servidor e enviará uma mensagem criptografada:

java Client
Envio da Mensagem: Após estabelecer a conexão, o cliente enviará a mensagem "Tranquilo", que será criptografada e enviada ao servidor.

Descriptografia e Resposta: O servidor receberá a mensagem criptografada, a descriptografará utilizando sua chave privada e exibirá o conteúdo original.

# 🔑 Tecnologias Utilizadas
Java: Linguagem utilizada para implementar a comunicação socket.
Criptografia RSA: Utilizada para garantir a segurança na troca de mensagens entre o cliente e o servidor.
Sockets TCP/IP: Protocolo de comunicação utilizado para estabelecer a conexão entre o cliente e o servidor.









