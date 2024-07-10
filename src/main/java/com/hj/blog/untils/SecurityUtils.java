package com.hj.blog.untils;

import org.springframework.util.DigestUtils;

import java.util.UUID;

import java.lang.String;
public class SecurityUtils {
   /**
    * @password 用户注册时输入的密码
    * @return 数据库中存储的信息
    */
    public static String encrypt(String password) {
        String salt = UUID.randomUUID().toString().replace("-", "");
        String finalPassword = DigestUtils.md5DigestAsHex((password+salt).getBytes());
        return salt + finalPassword;
    }

    /**
     * 验证密码是否正确
     * @param inputPassword 用户登录时输入的密码
     * @param sqlPassword 数据库中使用password字段存储的信息
     */
    public static boolean verify(String inputPassword, String sqlPassword) {
        if (!SecurityUtils.hasLength(inputPassword)){
            return false;
        }
        if (sqlPassword==null || sqlPassword.length()!=64){
            return false;
        }
        String salt = sqlPassword.substring(0,32);
        String finalPassword = DigestUtils.md5DigestAsHex((inputPassword+salt).getBytes());

        return (salt+finalPassword).equals(inputPassword);
    }

    private static boolean hasLength(String inputPassword) {
        return false;
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123456"));
        verify("123456","123456");
    }
}
