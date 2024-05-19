package org.unify4j.common;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;

public class Encryption4j {

    /**
     * Computes the MD5 hash of a file using a FileChannel and a direct ByteBuffer.
     *
     * @param file the file for which to compute the MD5 hash
     * @return the MD5 hash of the file as a hexadecimal string, or null if an error occurs
     */
    public static String fastMD5(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            return calculateFileHash(in.getChannel(), getMD5Digest());
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Computes the SHA-1 hash of a file using a FileChannel and a direct ByteBuffer.
     *
     * @param file the file for which to compute the SHA-1 hash
     * @return the SHA-1 hash of the file as a hexadecimal string, or null if an error occurs
     */
    public static String fastSHA1(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            return calculateFileHash(in.getChannel(), getSHA1Digest());
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Computes the SHA-256 hash of a file using a FileChannel and a direct ByteBuffer.
     *
     * @param file the file for which to compute the SHA-256 hash
     * @return the SHA-256 hash of the file as a hexadecimal string, or null if an error occurs
     */
    public static String fastSHA256(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            return calculateFileHash(in.getChannel(), getSHA256Digest());
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Computes the SHA-512 hash of a file using a FileChannel and a direct ByteBuffer.
     *
     * @param file the file for which to compute the SHA-512 hash
     * @return the SHA-512 hash of the file as a hexadecimal string, or null if an error occurs
     */
    public static String fastSHA512(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            return calculateFileHash(in.getChannel(), getSHA512Digest());
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Calculates the hash of a file using a given MessageDigest.
     *
     * @param ch the FileChannel to read from
     * @param d  the MessageDigest to update with the file data
     * @return the hash of the file as a hexadecimal string
     * @throws IOException if an I/O error occurs
     */
    public static String calculateFileHash(FileChannel ch, MessageDigest d) throws IOException {
        ByteBuffer bb = ByteBuffer.allocateDirect(65536);
        int nRead;
        while ((nRead = ch.read(bb)) != -1) {
            if (nRead == 0) {
                continue;
            }
            bb.position(0);
            bb.limit(nRead);
            d.update(bb);
            bb.clear();
        }
        return Byte4j.encode(d.digest());
    }

    /**
     * Calculates the MD5 hash of a byte array.
     *
     * @param bytes the byte array to hash
     * @return the MD5 hash as a hexadecimal string
     */
    public static String calculateMD5Hash(byte[] bytes) {
        return calculateHash(getMD5Digest(), bytes);
    }

    /**
     * Returns a MessageDigest instance for the specified algorithm.
     *
     * @param digest the name of the algorithm
     * @return the MessageDigest instance
     * @throws IllegalArgumentException if the algorithm is not available
     */
    public static MessageDigest getDigest(String digest) {
        try {
            return MessageDigest.getInstance(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(String.format("The requested MessageDigest (%s) does not exist", digest), e);
        }

    }

    /**
     * Returns a MessageDigest instance for MD5.
     *
     * @return the MD5 MessageDigest instance
     */
    public static MessageDigest getMD5Digest() {
        return getDigest("MD5");
    }

    /**
     * Calculates the SHA-1 hash of a byte array.
     *
     * @param bytes the byte array to hash
     * @return the SHA-1 hash as a hexadecimal string
     */
    public static String calculateSHA1Hash(byte[] bytes) {
        return calculateHash(getSHA1Digest(), bytes);
    }

    /**
     * Returns a MessageDigest instance for SHA-1.
     *
     * @return the SHA-1 MessageDigest instance
     */
    public static MessageDigest getSHA1Digest() {
        return getDigest("SHA-1");
    }

    /**
     * Calculates the SHA-256 hash of a byte array.
     *
     * @param bytes the byte array to hash
     * @return the SHA-256 hash as a hexadecimal string
     */
    public static String calculateSHA256Hash(byte[] bytes) {
        return calculateHash(getSHA256Digest(), bytes);
    }

    /**
     * Returns a MessageDigest instance for SHA-256.
     *
     * @return the SHA-256 MessageDigest instance
     */
    public static MessageDigest getSHA256Digest() {
        return getDigest("SHA-256");
    }

    /**
     * Calculates the SHA-512 hash of a byte array.
     *
     * @param bytes the byte array to hash
     * @return the SHA-512 hash as a hexadecimal string
     */
    public static String calculateSHA512Hash(byte[] bytes) {
        return calculateHash(getSHA512Digest(), bytes);
    }

    /**
     * Returns a MessageDigest instance for SHA-512.
     *
     * @return the SHA-512 MessageDigest instance
     */
    public static MessageDigest getSHA512Digest() {
        return getDigest("SHA-512");
    }

    /**
     * Creates a byte array suitable for use as an encryption key from a string key.
     *
     * @param key        the string key
     * @param bitsNeeded the number of bits needed for the encryption key
     * @return the byte array of the encryption key
     */
    public static byte[] createCipherBytes(String key, int bitsNeeded) {
        String word = calculateMD5Hash(key.getBytes(StandardCharsets.UTF_8));
        return word.substring(0, bitsNeeded / 8).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Creates an AES encryption cipher using a given key.
     *
     * @param key the encryption key
     * @return the AES encryption cipher
     * @throws Exception if an error occurs creating the cipher
     */
    public static Cipher createAesEncryptionCipher(String key) throws Exception {
        return createAesCipher(key, Cipher.ENCRYPT_MODE);
    }

    /**
     * Creates an AES decryption cipher using a given key.
     *
     * @param key the decryption key
     * @return the AES decryption cipher
     * @throws Exception if an error occurs creating the cipher
     */
    public static Cipher createAesDecryptionCipher(String key) throws Exception {
        return createAesCipher(key, Cipher.DECRYPT_MODE);
    }

    /**
     * Creates an AES cipher using a given key and mode.
     *
     * @param key  the encryption/decryption key
     * @param mode the cipher mode (Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE)
     * @return the AES cipher
     * @throws Exception if an error occurs creating the cipher
     */
    public static Cipher createAesCipher(String key, int mode) throws Exception {
        Key sKey = new SecretKeySpec(createCipherBytes(key, 128), "AES");
        return createAesCipher(sKey, mode);
    }

    /**
     * Creates a Cipher from the passed in key, using the passed in mode.
     *
     * @param key  the SecretKeySpec
     * @param mode the cipher mode (Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE)
     * @return the Cipher instance
     * @throws Exception if an error occurs creating the cipher
     */
    public static Cipher createAesCipher(Key key, int mode) throws Exception {
        MessageDigest d = getMD5Digest(); // Use password key as seed for IV (must be 16 bytes)
        d.update(key.getEncoded());
        byte[] iv = d.digest();
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");        // CBC faster than CFB8/NoPadding (but file length changes)
        cipher.init(mode, key, paramSpec);
        return cipher;
    }

    /**
     * Encrypts a string using AES-128 and returns the encrypted content as a hexadecimal string.
     *
     * @param key     the encryption key
     * @param content the content to be encrypted
     * @return the encrypted content as a hexadecimal string
     */
    public static String encrypt(String key, String content) {
        try {
            return Byte4j.encode(createAesEncryptionCipher(key).doFinal(content.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("Error occurred encrypting data", e);
        }
    }

    /**
     * Encrypts a byte array using AES-128 and returns the encrypted content as a hexadecimal string.
     *
     * @param key     the encryption key
     * @param content the byte array to be encrypted
     * @return the encrypted content as a hexadecimal string
     */
    public static String encryptBytes(String key, byte[] content) {
        try {
            return Byte4j.encode(createAesEncryptionCipher(key).doFinal(content));
        } catch (Exception e) {
            throw new IllegalStateException("Error occurred encrypting data", e);
        }
    }

    /**
     * Decrypts a hexadecimal string using AES-128 and returns the original content as a string.
     *
     * @param key    the decryption key
     * @param hexStr the encrypted content as a hexadecimal string
     * @return the original content as a string
     */
    public static String decrypt(String key, String hexStr) {
        try {
            return new String(createAesDecryptionCipher(key).doFinal(Objects.requireNonNull(Byte4j.decode(hexStr))));
        } catch (Exception e) {
            throw new IllegalStateException("Error occurred decrypting data", e);
        }
    }

    /**
     * Decrypts a hexadecimal string using AES-128 and returns the original content as a byte array.
     *
     * @param key    the decryption key
     * @param hexStr the encrypted content as a hexadecimal string
     * @return the original content as a byte array
     */
    public static byte[] decryptBytes(String key, String hexStr) {
        try {
            return createAesDecryptionCipher(key).doFinal(Objects.requireNonNull(Byte4j.decode(hexStr)));
        } catch (Exception e) {
            throw new IllegalStateException("Error occurred decrypting data", e);
        }
    }

    /**
     * Calculates a hash from a byte array using a given MessageDigest.
     *
     * @param d     the MessageDigest to update
     * @param bytes the byte array to hash
     * @return the hash as a hexadecimal string
     */
    public static String calculateHash(MessageDigest d, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        d.update(bytes);
        return Byte4j.encode(d.digest());
    }
}
