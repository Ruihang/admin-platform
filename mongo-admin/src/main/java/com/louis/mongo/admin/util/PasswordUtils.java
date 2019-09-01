package com.louis.mongo.admin.util;

/**
 * 密码工具类
 */
public class PasswordUtils {
    /**
     * 匹配密码
     * @param salt 盐
     * @param rawPassword 明文
     * @param encPassword 密文
     * @return 匹配结果
     */
    public static boolean matches(String salt, String rawPassword, String encPassword) {
        return new PasswordEncoder(salt).matches(encPassword,rawPassword);
    }
}
