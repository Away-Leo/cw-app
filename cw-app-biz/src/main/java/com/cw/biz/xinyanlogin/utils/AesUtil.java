package com.cw.biz.xinyanlogin.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

/**
 * Aes算法加解密
 *
 * @author yingmuhuadao
 * @version 5.0
 * @date 2018/4/2
 */
@Slf4j
public class AesUtil {

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     * PKCS5Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final String IV_STRING = "DEVICE-AES000000";

    /**
     * 使用AES算法进行加密
     *
     * @param source 加密源
     * @param key    秘钥
     * @return 密文
     */
    public static String encrypt(String source, String key) {
        try {
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(source)) {
                return null;
            }
            byte[] keyBytes = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(source.getBytes(Charset.forName("UTF-8")));
            return Base64.encode(encrypted);
        } catch (Exception e) {
            log.warn("encrypt Exception source {}, key {}", source, key);
        }
        return null;
    }

}
