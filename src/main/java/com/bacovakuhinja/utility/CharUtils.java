package com.bacovakuhinja.utility;


public class CharUtils {
    public static String getASCIIFromString(String text){
        StringBuilder sb = new StringBuilder();
        for(char ch : text.toCharArray()){
            sb.append("&#");
            sb.append(CharToASCII(ch));
            sb.append(";");
        }
        return sb.toString();
    }

    private static int CharToASCII(final char character){
        return (int)character;
    }
}
