package com.secmngsys.global.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.util.Base64;

/*
 * AES256 API
 */
public class AES256Util {

    protected final String key = "";

    public AES256Util(){
    }

    public AES256Util(String key, String plainText) {
		//String plainText = "";
		//String key = "";
		try {
			String encrypted = AES256Util.encryptAES256(plainText, key);
			System.out.println("key - " + key);
			System.out.println("AES-256 : enc - " + encrypted);
			System.out.println("AES-256 : dec - " + AES256Util.decryptAES256(encrypted, key));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public String getKey() throws Exception {
        //String content = Files.readString(Paths.get(getClass().getResource("/profile/prd/key.txt").toURI()));
        ClassPathResource resource = new ClassPathResource("profile/prd/key.txt");
        byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
        //Path path = Paths.get(resource.getURI());
        String content = new String(bdata, StandardCharsets.UTF_8);
        //String content = Files.readString(path);
        //String content = Files.readString(Paths.get(getClass().getResource("profile/prd/key.txt").toURI(), StandardCharsets.UTF_8);
        return decryptAES256(content, key);
    }

    public static String encryptAES256(String msg, String key) throws Exception {

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        byte[] saltBytes = bytes;

        // Password-Based Key Derivation function 2
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 70000, 256);

        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        // CBC : Cipher Block Chaining Mode
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
       
        // Initial Vector
        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(msg.getBytes("UTF-8"));
        byte[] buffer = new byte[saltBytes.length + ivBytes.length + encryptedTextBytes.length];
        System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
        System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
        System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length + ivBytes.length, encryptedTextBytes.length);
        return Base64.getEncoder().encodeToString(buffer);

    }

 
    public static String decryptAES256(String msg, String key) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(msg));

        byte[] saltBytes = new byte[20];
        buffer.get(saltBytes, 0, saltBytes.length);
        byte[] ivBytes = new byte[cipher.getBlockSize()];
        buffer.get(ivBytes, 0, ivBytes.length);
        byte[] encryoptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
        buffer.get(encryoptedTextBytes);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 70000, 256);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
        byte[] decryptedTextBytes = cipher.doFinal(encryoptedTextBytes);
        return new String(decryptedTextBytes);

    }
}
