package org.aleks4ay.hotel.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

/**
 * Created by aser on 01.08.2021.
 */
public final class Encrypt {

    public static String hash(String input, String algorithm) {
        MessageDigest messageDigest;
        byte[] hashByte = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(input.getBytes());
            hashByte = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
//            logger.log(Level.INFO, e.getMessage());
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
