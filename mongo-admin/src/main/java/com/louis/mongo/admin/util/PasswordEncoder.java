package com.louis.mongo.admin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {

    private static final String[] hexDigest = new String[]{"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    public static final String ALGORITHM_MD5 = "MD5";
    public static final String ALGORITHM_SHA = "SHA";

    private final String salt;
    private final String algorithm;

    public PasswordEncoder(String salt) {
        this(salt,ALGORITHM_MD5);
    }

    public PasswordEncoder(String salt, String algorithm) {
        this.salt = salt;
        this.algorithm = algorithm;
    }

    public boolean matches(String encodePassword, String rawPassword) {
        String password1 = "" + encodePassword;
        String password2 = encode(rawPassword);
        return password1.equals(password2);
    }

    private String encode(String rawPassword) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(this.algorithm);
            // 密码和盐合并
            String mergePassword = mergePasswordAndSalt(rawPassword);
            // 加密生成摘要
            byte[] digest = md.digest(mergePassword.getBytes("UTF-8"));
            result = byteArraryToHexString(digest);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String byteArraryToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            sb.append(byteToHexString(b));
        }
        return sb.toString();
    }

    private String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigest[d1] + hexDigest[d2];
    }

    private String mergePasswordAndSalt(String password) {
        if (password == null) {
            password = "";
        }
        if (salt == null || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt +"}";
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        PasswordEncoder encoder = new PasswordEncoder("a");
        String s = encoder.byteArraryToHexString("你好".getBytes("UTF-8"));
        System.out.println(s);
    }
}
