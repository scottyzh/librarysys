package com.gdufe.libsys.utils;

import java.security.MessageDigest;
import java.util.Base64;


public class Md5Util {

    public static String encode(String msg) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            return Base64.getEncoder().encodeToString(messageDigest.digest(msg.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
