package com.example.assigmentthree.spring_security;

import jakarta.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SerializationUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import java.util.Base64;

@Configuration
public class EncryptionDecryption implements AttributeConverter<Object, String> {

    @Value("${aes.encryption.key}")
    private String encryptionKey;

    private static final String ENCRYPTION_ALGORITHM = "AES/ECB/PKCS5Padding";
    private SecretKey secretKey;

    private SecretKey getSecretKey() throws GeneralSecurityException, UnsupportedEncodingException {
        if (secretKey == null) {
            validateKeyLength(encryptionKey.length());
            secretKey = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        }
        return secretKey;
    }

    private void validateKeyLength(int length) throws GeneralSecurityException {
        if (length != 16 && length != 24 && length != 32) {
            throw new GeneralSecurityException("Invalid key length: must be 16, 24, or 32 bytes");
        }
    }

    private Cipher initializeCipher(int mode) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(mode, getSecretKey());
        return cipher;
    }

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            Cipher encryptionCipher = initializeCipher(Cipher.ENCRYPT_MODE);
            byte[] serializedAttribute = SerializationUtils.serialize(attribute);
            return Base64.getEncoder().encodeToString(encryptionCipher.doFinal(serializedAttribute));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to encrypt data", e);
        }
    }

    @Override
    public Object convertToEntityAttribute(String data) {
        if (data == null) {
            return null;
        }
        try {
            Cipher decryptionCipher = initializeCipher(Cipher.DECRYPT_MODE);
            byte[] decodedData = Base64.getDecoder().decode(data);
            byte[] deserializedData = decryptionCipher.doFinal(decodedData);
            return SerializationUtils.deserialize(deserializedData);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to decrypt data", e);
        }
    }
}
