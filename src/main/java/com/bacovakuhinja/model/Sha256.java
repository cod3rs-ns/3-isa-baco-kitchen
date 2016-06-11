package com.bacovakuhinja.model;

import java.security.MessageDigest;

public class Sha256 {
    public static String getSha256(String text) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return String.format("%064x", new java.math.BigInteger(1, digest));
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
