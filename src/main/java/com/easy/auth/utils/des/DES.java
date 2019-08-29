package com.easy.auth.utils.des;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;


/**
 * 加密文件 涉及的 DES 算法类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
class DES {
    private static final String Const_IV = "L+/~f4,I";
    private static final String Const_Key = "CIHY2K";

    /**
     * DES加密
     *
     * @param decrypt 明文字符串
     * @return 密文，非空
     */
    final byte[] Encrypt(byte[] decrypt) {
        try {
            return DES.Encrypt(decrypt, Const_Key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DES解密
     *
     * @param encrypt 密文字符串
     * @return 明文，非空
     */
    final byte[] Decrypt(byte[] encrypt) {
        return DES.Decrypt(encrypt, Const_Key);
    }

    /**
     * DES加密
     *
     * @param decrypt 明文字符串
     * @param key     密钥，为null是默认为DES.Const_Key
     * @return 密文，非空
     */
    private static byte[] Encrypt(byte[] decrypt, String key) throws NoSuchAlgorithmException {

        try {

            /*
             * 加密算法的参数接口，IvParameterSpec是它的一个实现
             */
            AlgorithmParameterSpec iv = new IvParameterSpec(Const_IV.getBytes(StandardCharsets.UTF_8));
            /*
             * 实例化密钥工厂
             */
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            /*
             * 生成秘钥
             */
            SecretKey deskey = keyFactory.generateSecret(new DESKeySpec(Const_Key.getBytes(StandardCharsets.UTF_8)));
            /*
             * 加、解密处理
             */
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            /*
             * 设置工作模式为加密模式，给出密钥和向量
             */
            cipher.init(Cipher.ENCRYPT_MODE, deskey, iv);
            /*
             * 转成BASE64
             */
            return cipher.doFinal(decrypt);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * DES解密
     *
     * @param encrypt 密文字符串
     * @param key     密钥，为null是默认为DES.Const_Key
     * @return 明文，非空
     */
    private static byte[] Decrypt(byte[] encrypt, String key) {
        if (!key.equals("")) {
            key = DES.Const_Key;
        }
        try {
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(Const_IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            return cipher.doFinal(encrypt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
