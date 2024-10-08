package com.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CMSHashManager {

    private static final String SALT = "3D5900AE-111A-45BE-96B3-D9E4606CA793";
    private static final int HASH_ITERATIONS_MAX = 10;

    // Metodo per ottenere l'hash della password con salt e iterazioni
    public static String getPasswordHash(String plaintextPassword) throws NoSuchAlgorithmException {
        String hashData = plaintextPassword;
        for (int hashLimit = 0; hashLimit < HASH_ITERATIONS_MAX; hashLimit++) {
            hashData = getHash(SALT + hashData);
        }
        return hashData;
    }

    // Metodo per verificare se l'hash della password è corretto
    public static boolean verifyHashedPassword(String plaintextPassword, String encryptedPassword) {
        try {
            String hashData = getPasswordHash(plaintextPassword);
            return encryptedPassword.equals(hashData);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 not available", e);
        }
    }

    // Metodo privato per calcolare l'hash SHA-512 della stringa
    private static String getHash(String unhashedData) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashData = digest.digest(unhashedData.getBytes());
        return Base64.getEncoder().encodeToString(hashData);
    }

    // Metodo di test
    public static void main(String[] args) {
        try {
            String plaintext = "12345";
            String encrypted = "NgkuakH7UsCQwGHMQOhVXI3nW6M+1AtREY4Qx35osQo87p/whZIzy8cZU7+R7XnmyzgMzLWSvX+rTiW‌​‌​zfGTPsA==";
            System.out.println("Testo in chiaro: " + plaintext);
            System.out.println("Password cifrata attesa: " + encrypted);
            String result = getPasswordHash(plaintext);
            System.out.println("Password cifrata ottenuta: " + result);
            System.out.println("Verifica: " + verifyHashedPassword(plaintext, encrypted));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
