package rsa;

import java.math.BigInteger;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

public class RSA {
    private BigInteger n, d, e;
    private static final int BLOCK_SIZE = 1;
    public RSA() {
        BigInteger p = BigInteger.valueOf(7);
        BigInteger q = BigInteger.valueOf(181);

        n = p.multiply(q);

        e = BigInteger.valueOf(7);

        d = BigInteger.valueOf(463);
    }

    public RSA(BigInteger n, BigInteger e) {
        this.n = n;
        this.e = e;
    }

    public String encrypt(String message) {
        try {
            List<String> blocks = splitMessageIntoBlocks(message);

            List<String> encryptedBlocks = new ArrayList<>();
            for (String block : blocks) {

                int asciiValue = (int) block.charAt(0);

                BigInteger m = BigInteger.valueOf(asciiValue);

                if (m.compareTo(n) >= 0) {
                    throw new IllegalArgumentException("Bloco muito longo para ser criptografado");
                }

                BigInteger encryptedBlock = m.modPow(e, n);

                encryptedBlocks.add(Base64.getEncoder().encodeToString(encryptedBlock.toByteArray()));
            }

            return String.join(":", encryptedBlocks);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String decrypt(String encryptedMessage) {
        try {
            String[] encryptedBlocks = encryptedMessage.split(":");

            StringBuilder decryptedMessage = new StringBuilder();
            for (String encryptedBlock : encryptedBlocks) {
                BigInteger encrypted = new BigInteger(1, Base64.getDecoder().decode(encryptedBlock));

                BigInteger decryptedBlock = encrypted.modPow(d, n);

                char decryptedChar = (char) decryptedBlock.intValue();

                decryptedMessage.append(decryptedChar);
            }

            return decryptedMessage.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Erro ao descriptografar a mensagem: " + ex.getMessage();
        }
    }


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
