package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    private BigInteger n, d, e;
    private int bitlen = 1024;

    // Construtor para gerar as chaves públicas e privadas (usado no servidor)
    public RSA(int bits) {
        bitlen = bits;
        SecureRandom random = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(bitlen / 2, random);
        BigInteger q = BigInteger.probablePrime(bitlen / 2, random);
        n = p.multiply(q);

        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.probablePrime(bitlen / 2, random);

        while (phi.gcd(e).intValue() > 1) {
            e = e.add(BigInteger.ONE);
        }

        d = e.modInverse(phi);
    }

    // Novo construtor que recebe n e e (usado no cliente)
    public RSA(BigInteger n, BigInteger e) {
        this.n = n;
        this.e = e;
    }

    // Criptografa a mensagem usando a chave pública
    public String encrypt(String message) {
        BigInteger m = new BigInteger(message.getBytes());
        return m.modPow(e, n).toString();
    }

    // Descriptografa a mensagem usando a chave privada (somente o servidor tem a chave privada)
    public String decrypt(String encryptedMessage) {
        BigInteger m = new BigInteger(encryptedMessage);
        return new String(m.modPow(d, n).toByteArray());
    }

    // Método para enviar a chave pública (n, e)
    public BigInteger getPublicKeyN() {
        return n;
    }

    public BigInteger getPublicKeyE() {
        return e;
    }
}
