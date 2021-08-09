package org.aleks4ay.hotel.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

public final class Encrypt {
    private static final Logger log = LogManager.getLogger(Encrypt.class);

    public static String hash(String input, String algorithm) {
        MessageDigest messageDigest;
        byte[] hashByte = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(input.getBytes());
            hashByte = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            log.warn("Exception during getting '{}' algorithm for encrypting. {}", algorithm, e.getMessage());
        }
        return bytesToHex(hashByte);
    }

    private static String bytesToHex(byte[] hashInBytes) {
        if (hashInBytes == null) {
            throw new NoSuchElementException();
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString().toUpperCase();
    }
}
