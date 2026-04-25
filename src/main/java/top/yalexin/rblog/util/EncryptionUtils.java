package top.yalexin.rblog.util;

import java.util.ArrayList;
import java.util.List;

public class EncryptionUtils {
    static final String STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?/~`";
    static final int SALT_LENGTH = 16;
    // 输入密码，返回加密后的密码
    // 第一个元素是 salt，第二个元素是 md5(md5(md5(psw))+salt)
    public static List<String> encrypt(String psw) {
        String salt = generateSalt(SALT_LENGTH);
        String code = MD5Utils.code(MD5Utils.code(MD5Utils.code(psw) ) + salt);
        List<String> list = new ArrayList<>();
        list.add(salt);
        list.add(code);
        return list;
    }
    // 根据长度生成随机字符串
    public static String generateSalt(int length) {
        String str = "";
        for (int i = 0; i < length; i++) {
            str += STR.charAt((int) (Math.random() * STR.length()));
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123456"));
    }

}
