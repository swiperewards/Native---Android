package com.nouvo.rewards.helpers.security;

import android.util.Base64;

import java.security.GeneralSecurityException;

public class CryptoHelper {


    public String encryptAES(String input, String password) {
        try {
//            return new AES256().encrypt(input,password);
            return AESCrypt.encrypt(password, input);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String decryptAES(String input, String password) {
        return AESCrypt.decryptJS(input, password);
    }

    public String encryptXOR(String input) {
        input = input.trim();
        input = Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
        return xor(input);
    }

    private String xor(String input) {
        input = input.trim();
        String result = "";
        for (int index = 0; index < input.length(); index++) {
            int a = input.charAt(index);
            int b = a ^ 123423;
            result = result + Character.toString((char) b);
        }
        return result.trim();
    }

    public String decryptXOR(String input) {
        String result = xor(input);
        result = new String(Base64.decode(result.getBytes(), Base64.DEFAULT));
        return result.trim();
    }

    public String encryptBase64(String input) {
        return Base64.encodeToString(input.trim().getBytes(), Base64.DEFAULT).trim();
    }

    public String decryptBase64(String input) {
        return new String(Base64.decode(input.trim().getBytes(), Base64.DEFAULT)).trim();
    }
}
