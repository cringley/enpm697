package com.umd.sdlc.example.sdlc_project.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.xml.bind.DatatypeConverter;
public class Hashing {
    
    public static final String MD5_STRING = "MD5";
    public static final String SHA_1_STRING = "SHA-1";
    public static final String SHA_256_STRING = "SHA-256";

    /**
     * Returns the hash representation of the string
     * @param stringToHash
     * @param hashType - type of hash to use, MD5, SHA-1, SHA-256
     * @return - Hash of input string
     */
    public static String hashString(String stringToHash, String hashType) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(hashType);
            messageDigest.update(stringToHash.getBytes());
            byte[] digest = messageDigest.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException exception) {
            return exception.getLocalizedMessage();
        }
    }
}
