package com.renqi.market.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.renqi.market.util.Coder.decryptBASE64;

/**
 * RSA签名验签工具类
 * @auth luhonggang
 * @date 2018/8/8
 * @since 1.0
 */
@SuppressWarnings("all")
public class RSASignatureUtil {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** 签名算法1 */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     *  签名算法2
     */
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * 签名编码
     */
    private static final String ENCODE_SIGN = "UTF-8";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * RSA签名
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @return 签名值
     */
    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(ENCODE_SIGN));
            byte[] signed = signature.sign();
            return Base64Utils.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA验签名检查
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @return 布尔值
     */
    public static boolean doCheck(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64Utils.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(ENCODE_SIGN));
            boolean bverify = signature.verify(Base64Utils.decode(sign));
            return bverify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String signWithSign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes());
            byte[] signed = signature.sign();
            return Base64Utils.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 签名校验
     * @param content   签名的原始内容
     * @param sign      签名
     * @param publicKey 公钥
     * @return
     */
    public static boolean doCheckWithSign(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64Utils.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            boolean bverify = signature.verify(Base64Utils.decode(sign));
            return bverify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        //解密公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        //构造X509EncodedKeySpec对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //取公钥匙对象
        PublicKey publicSignKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicSignKey);
        signature.update(data);
        //验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    // 平台的公钥和私钥（卡牛）
    public  static String platformPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnrmYrFdRQKcAfyUq3Tg8OEuwoYhyhx+bc3x7I\n" +
            "7K2TpILYB9cP7boNINpu99VGeNEo5+/9J6wdSE8Qi2VRPyRQqPYNnrhPzZhBjzJjj1I178RvkXI2\n" +
            "1Sps8uDjgQsj6C4eGB6uriDy2JHZ64K8+fjes+KJGt5k40v5FiIBJ6AmhQIDAQAB";
    public  static String platformPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKeuZisV1FApwB/JSrdODw4S7Chi\n" +
            "HKHH5tzfHsjsrZOkgtgH1w/tug0g2m731UZ40Sjn7/0nrB1ITxCLZVE/JFCo9g2euE/NmEGPMmOP\n" +
            "UjXvxG+RcjbVKmzy4OOBCyPoLh4YHq6uIPLYkdnrgrz5+N6z4oka3mTjS/kWIgEnoCaFAgMBAAEC\n" +
            "gYEApbyaXZG5BfcOYByszhvIg6euZof7NukIRl3+5qgRNPKRJYgySRs3H0zOuooZ8wrt6dm+euGg\n" +
            "nFzhUUAGx9R6ReRnNBeFUpmFXPlc3LuAjMhgVAGlIovrjIpim3iTfWKplE2/Quzgrgk21dBGVybN\n" +
            "CHJvNBz1ND/76qyREwOdd40CQQD0RoOdJktnxI4Z67M7UI1vCRz01TNnW3TRONJ8rRlGr8MZx7Cp\n" +
            "O9XsRgFuAtwuzhLn18xj3MTJ88FoWRypWE/jAkEAr7q/vy3DCTIZ2BvgTN85H5UuJIPMetaBAout\n" +
            "/5v5SezDXnhwgzYD3yDvPNT+ucUH1JBaURwzr7rrqOq2LkgsdwJAAW9cnJK6BK0J7KsOzX1mp01V\n" +
            "ZzbNa4EFCamcyvC1Yk3sBn2+0u+wDQhIP94ybu/0+nmxELEeUtVkwHktiu0i4wJARsDnQT9YntQ4\n" +
            "SaX3qpvZ8RhsirEyXGcPbIn21crkFVqp0tilXH8cEfO9v312zi9BltlENBIp0WBuUAWosnvGwwJA\n" +
            "VbGIRbnAdFWBiGjz5zDtSDl1YNVRdX9+8VFvSwuXTzIZtDk2cmZTxVVGR4LKDPRFMRsQWxXxeKb0\n" +
            "bVss2XRotQ==";

    // 卡牛的公钥
    public static String thePublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk9REkQcA_ZpvWpAb_OGpcltPwGHB_CaJ0oQqOoq7L6Zgm_7fBlWmsdbGhoWfZje6tUMBP4Ji0QtOUqK_SbUIjJtIk5cE8JifmLfPQaLNozUMf3wFoOIKkNvE9OEStdeJc6P-VVNh6KddPaNIm-yD0QbgqxlgKe3Dzy8hdf6nWlMVs8gMcrOSFx5zZCy8hm_XdI_PC1bt1Jpd29s7jW7aQ-g-pc2qQHPruz06qfZWQYVk0YxyycHeQ18xaLb1NAXyvl-yZvDCFrXnaoh5zM8gpS47psLApYSYRhQZOX4MPUfJrcpx5wB1RI5UuLX_Ii6orZ2rr3w6WMhGKiQiyv2T8wIDAQAB";
        public static void main(String[] args) throws Exception {
            // 18589072284
            System.out.println("---------------私钥签名过程------------------");
            String sign="maskType=1&phoneNo=3f90baac9ace816c8f0fb95d2740de29&timestamp=1526267875026";
            String signstr= RSASignatureUtil.sign(sign,platformPrivateKey);
            System.out.println("签名原串："+sign);
            System.out.println("签名串："+signstr);
            // 卡牛私钥签名串
            //String signstr = "CQtT7HzFsEw5VP2ruVjTlwpZaxGHe5_hEPdMoutlv6JkVKWy-7Ka1iKW4tgbBfg9cJ3W1EPThjkOeN8boyjBqWI8TZcMkxp3lQ-J5VvNyUQ-21GfKxeAjeWjDMDKkbyCADVZjypaodE3FeZ0i39K2AOHFHAh-6Z8PI1K8gl02YdTmAfNRSgWsH7uqqQOAZWBj_Iq71eAzZ8CNuFoXRY0FjarMtQb3OXkhJza5x6HW1GcBaiRwfns1RTRIHholBb1iLECebcOumYSKgJYNVH6UsMZMf_q974H7hcngNOivumNB_mQ75P9Ak6Ai08C9SaFohW8vHfd0fghTolpeapabQ";

            // 获取到平台用私钥加密的签名
            System.out.println("---------------公钥校验签名------------------");
            System.out.println("签名原串2："+sign);
            System.out.println("签名串2："+signstr);
            System.out.println("验签结果2："+ RSASignatureUtil.doCheck(sign, signstr,platformPublicKey));

        }

}