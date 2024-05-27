package org.unify4j;

import org.junit.Test;
import org.unify4j.common.Encryption4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import static org.junit.Assert.*;

public class Encryption4jTest {

    @Test
    public void testCalculateMD5Hash() {
        String input = "test";
        String expectedHash = "098F6BCD4621D373CADE4E832627B4F6"; // Precomputed MD5 hash of "test"
        String actualHash = Encryption4j.calculateMD5Hash(input.getBytes(StandardCharsets.UTF_8));
        System.out.println(actualHash);
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void testCalculateSHA1Hash() {
        String input = "test";
        String expectedHash = "A94A8FE5CCB19BA61C4C0873D391E987982FBBD3"; // Precomputed SHA-1 hash of "test"
        String actualHash = Encryption4j.calculateSHA1Hash(input.getBytes(StandardCharsets.UTF_8));
        System.out.println(actualHash);
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void testEncryptDecrypt() {
        String key = "key";
        String content = "Hello, World!";
        String encrypted = Encryption4j.encrypt(key, content);
        String decrypted = Encryption4j.decrypt(key, encrypted);
        assertEquals(content, decrypted);
    }

    @Test
    public void testFastMD5() {
        File file = new File("src/main/resources/sample.txt");
        assertTrue(file.exists());
        String expectedHash = "6FEA7ECEC6AA4622AFA8DB071B426F35"; // Precomputed MD5 hash of file content
        String actualHash = Encryption4j.fastMD5(file);
        System.out.println(actualHash);
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void testFastSHA256() {
        File file = new File("src/main/resources/sample.txt");
        assertTrue(file.exists());
        String expectedHash = "6F78263FD0F8D1FD47747ED9B9AAE6476E6A560FADA212A6A2174E91AD063CF2"; // Precomputed SHA-256 hash of file content
        String actualHash = Encryption4j.fastSHA256(file);
        System.out.println(actualHash);
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void testGetDigest() {
        MessageDigest md5Digest = Encryption4j.getDigest("MD5");
        assertNotNull(md5Digest);
        assertEquals("MD5", md5Digest.getAlgorithm());

        MessageDigest sha256Digest = Encryption4j.getDigest("SHA-256");
        assertNotNull(sha256Digest);
        assertEquals("SHA-256", sha256Digest.getAlgorithm());
    }
}
