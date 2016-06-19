package com.bacovakuhinja.utility;

import java.security.MessageDigest;
import java.util.UUID;

public class PasswordHelper {
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

    public static String randomPassword(){
        String randomStr = UUID.randomUUID().toString().replace("-", "").substring(0,8).toUpperCase();
        return randomStr;
    }
}
