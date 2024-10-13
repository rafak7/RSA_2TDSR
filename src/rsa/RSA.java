package rsa;

import java.math.BigInteger;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

public class RSA {
    private BigInteger n, d, e;
    private static final int BLOCK_SIZE = 1;  // Tamanho do bloco para garantir que ele seja pequeno o suficiente

    // Usando valores fixos de P, Q, E e D
    public RSA() {
        BigInteger p = BigInteger.valueOf(7);   // P = 7
        BigInteger q = BigInteger.valueOf(181); // Q = 181

        // N = P * Q = 1267
        n = p.multiply(q);

        // E = 7 (exponente público)
        e = BigInteger.valueOf(7);

        // D = 463 (inverso modular de E em relação a r = (P-1)*(Q-1) = 1080)
        d = BigInteger.valueOf(463);
    }

    public RSA(BigInteger n, BigInteger e) {
        this.n = n;
        this.e = e;
    }

    // Método para criptografar a mensagem dividida em blocos
    public String encrypt(String message) {
        try {
            // Divida a mensagem em blocos de 1 caractere
            List<String> blocks = splitMessageIntoBlocks(message);

            List<String> encryptedBlocks = new ArrayList<>();
            for (String block : blocks) {
                // Converte o caractere para seu valor ASCII
                int asciiValue = (int) block.charAt(0);

                // Converte o valor ASCII para BigInteger
                BigInteger m = BigInteger.valueOf(asciiValue);

                // Verifica se o bloco é menor que N
                if (m.compareTo(n) >= 0) {
                    throw new IllegalArgumentException("Bloco muito longo para ser criptografado");
                }

                // Criptografa o bloco: c = m^e mod n
                BigInteger encryptedBlock = m.modPow(e, n);

                // Adiciona o bloco criptografado à lista
                encryptedBlocks.add(Base64.getEncoder().encodeToString(encryptedBlock.toByteArray()));
            }

            // Junta todos os blocos criptografados com um separador
            return String.join(":", encryptedBlocks);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Método para descriptografar a mensagem recebida em blocos
    public String decrypt(String encryptedMessage) {
        try {
            // Divide a mensagem criptografada em blocos separados por ":"
            String[] encryptedBlocks = encryptedMessage.split(":");

            StringBuilder decryptedMessage = new StringBuilder();
            for (String encryptedBlock : encryptedBlocks) {
                // Decodifica o bloco criptografado de Base64 para BigInteger
                BigInteger encrypted = new BigInteger(1, Base64.getDecoder().decode(encryptedBlock));

                // Descriptografa o bloco: m = c^d mod n
                BigInteger decryptedBlock = encrypted.modPow(d, n);

                // Converte o valor de volta para caractere ASCII
                char decryptedChar = (char) decryptedBlock.intValue();

                // Adiciona o caractere descriptografado à mensagem original
                decryptedMessage.append(decryptedChar);
            }

            // Retorna a mensagem descriptografada completa
            return decryptedMessage.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Erro ao descriptografar a mensagem: " + ex.getMessage();
        }
    }

    // Método para dividir a mensagem em blocos de tamanho fixo
    private List<String> splitMessageIntoBlocks(String message) {
        List<String> blocks = new ArrayList<>();
        for (int i = 0; i < message.length(); i += BLOCK_SIZE) {
            blocks.add(message.substring(i, i + BLOCK_SIZE));
        }
        return blocks;
    }

    public BigInteger getPublicKeyN() {
        return n;
    }

    public BigInteger getPublicKeyE() {
        return e;
    }
}
