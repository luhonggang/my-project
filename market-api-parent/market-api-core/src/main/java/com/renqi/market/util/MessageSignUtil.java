package com.renqi.market.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 对接省呗平台签名和数据加密工具类
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/8/9 16:26
 */
@SuppressWarnings("all")
public class MessageSignUtil {

    public static final Charset CHARSET = Charset.forName("utf-8");

    public static final String ALGORITHMS_SHA1 = "SHA-1";

    public static final String ALGORITHMS_MD5 = "MD5";

    // 加密基础信息
    public static final byte keyStrSzie = 16;

    public static final String ALGORITHM = "AES";

    public static final String AES_CBC_NOPADDING = "AES/CBC/NoPadding";

    /**
     * 消息摘要，使用参数 algorithms 指定的算法
     *
     * @param algorithms
     * @param inputStr
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] sign(String algorithms, String inputStr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithms);
        messageDigest.update(inputStr.getBytes(CHARSET));
        return messageDigest.digest();
    }

    /**
     * byte 数组转 十六进制字符串
     *
     * @param byteArray
     * @return
     */
    public static String byte2HexStr(byte[] byteArray) {

        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }

        return new String(resultCharArray);
    }

    /**
     * 签名,并将签名结果转换成 十六进制字符串
     * @param algorithms
     * @param inputStr
     * @return
     */
    public static String signToHexStr(String algorithms, String inputStr) {
        try {
            return byte2HexStr(sign(algorithms, inputStr));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String signParams(String key, String sourceStr) {
        String md5Str1 = signToHexStr(ALGORITHMS_MD5, sourceStr);
        String sourceStr2 = md5Str1 + key;
        return signToHexStr(ALGORITHMS_MD5, sourceStr2);
    }


    public static void main(String[] args) {
        /**
         * 签名测试
         */
        String secretKey = "5C0706A63D4B4C6E";
        String mobile = "13717566001";
        String mobileNo = "13388534226";
        System.out.println("mobile-md5:" + signToHexStr(ALGORITHMS_MD5,mobileNo));
        System.out.println("sign:" + signParams(secretKey, signToHexStr(ALGORITHMS_MD5,mobileNo)));
        // b2e8e55399f80dce916d0b215855f037
        String guDingStr = "b2e8e55399f80dce916d0b215855f037";
        System.out.println("字符长度 ： "+guDingStr.length());

        /**
         *  参数加密测试
         */
        String key = "5C0706A63D4B4C6E";
        Map<String, Object> userAttr = new HashMap<>();
        userAttr.put("mobile", "13800138004");
        userAttr.put("username", "custName");
        userAttr.put("channelId", "chaoshi216");

        String userAttrStr = JSONObject.toJSONString(userAttr);

        String enUserAttr =  strEncodBase64(key, userAttrStr);
        System.out.println("encodedStr = " + enUserAttr);
        //System.out.println("md5加密多个参数 ： "+MD5Utils.encode2hex(enUserAttr));
        String jsonStr = new String(base64StrDecode(key,enUserAttr));
        // .trim().replace("\\u0000","")
        System.out.println("decodedStr = " + jsonStr.trim());
        JSONObject paramsObj = JSONObject.parseObject(jsonStr);
        System.out.println("获取的参数是 ："+paramsObj.getString("mobile"));

        //MessageSignUtil.signParams(secretKey,encryptData)
        System.out.println("+++++++++++++sign : "+signParams(key,enUserAttr));

        //MessageSignUtil.signParams(secretKey,encryptData)
    }

    /**
     * AES/CBC/NoPadding encrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     * @param input
     * @return
     */
    public static byte[] encryptCBCNoPadding(byte[] secretKeyBytes, byte[] intVectorBytes, byte[] input) {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVectorBytes);
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, ALGORITHM);
            int inputLength = input.length;
            int srcLength;

            Cipher cipher = Cipher.getInstance(AES_CBC_NOPADDING);
            int blockSize = cipher.getBlockSize();
            byte[] srcBytes;
            if (0 != inputLength % blockSize) {
                srcLength = inputLength + (blockSize - inputLength % blockSize);
                srcBytes = new byte[srcLength];
                System.arraycopy(input, 0, srcBytes, 0, inputLength);
            } else {
                srcBytes = input;
            }

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encryptBytes = cipher.doFinal(srcBytes);
            return encryptBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * AES/CBC/NoPadding decrypt
     * 16 bytes secretKeyStr
     * 16 bytes intVector
     *
     * @param input
     * @return
     */
    public static byte[] decryptCBCNoPadding(byte[] secretKeyBytes, byte[] intVectorBytes, byte[] input) {
        try {
            IvParameterSpec iv = new IvParameterSpec(intVectorBytes);
            SecretKey secretKey = new SecretKeySpec(secretKeyBytes, ALGORITHM);

            Cipher cipher = Cipher.getInstance(AES_CBC_NOPADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] encryptBytes = cipher.doFinal(input);
            return encryptBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     *
     * @param secretStr
     * @param inputStr
     * @return
     */
    public static byte[] encode(String secretStr, String inputStr) {
        if (keyStrSzie != secretStr.length()) {
            return null;
        }
        byte[] secretKeyBytes = secretStr.getBytes(CHARSET);
        byte[] ivBytes = Arrays.copyOfRange(secretKeyBytes, 0, 16);
        byte[] inputBytes = inputStr.getBytes(CHARSET);

        byte[] outputBytes = encryptCBCNoPadding(secretKeyBytes, ivBytes, inputBytes);
        return outputBytes;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     * 并对加密后的字节数组调用 sun.misc.BASE64Encoder.encode 方法，
     * 转换成 base64 字符串返回。
     *
     * @param secretStr
     * @param inputStr
     * @return
     */
    public static String strEncodBase64(String secretStr, String inputStr){
        String base64Str = Base64.encodeBase64String(encode(secretStr, inputStr));
        return base64Str;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     *
     * @param secretStr
     * @return
     */
    public static byte[] decode(String secretStr, byte[] inputBytes){
        if (keyStrSzie != secretStr.length()) {
            return null;
        }
        byte[] secretKeyBytes = secretStr.getBytes(CHARSET);
        byte[] ivBytes = Arrays.copyOfRange(secretKeyBytes, 0, 16);

        byte[] outputBytes = decryptCBCNoPadding(secretKeyBytes, ivBytes, inputBytes);
        return outputBytes;
    }

    /**
     * 用 AES 算法加密 inputStr。
     * 使用 secretStr 作为 key，secretStr 的前 16 个字节作为 iv。
     * 并对加密后的字节数组调用 sun.misc.BASE64Encoder.encode 方法，
     * 转换成 base64 字符串返回。
     *
     * （仅作为测试用途，具体加密流程以接口文档为准）
     *
     * @param secretStr
     * @param inputStr
     * @return
     * @throws IOException
     */
    public static String base64StrDecode(String secretStr, String inputStr){
        byte[] inputBytes;
        inputBytes = Base64.decodeBase64(inputStr);
        String outputStr = new String(decode(secretStr, inputBytes), CHARSET);
        System.out.println("base64Decode > base64 decrypt " + outputStr.trim().replace("\\u0000",""));
        return outputStr.trim().replace("\\u0000","");
    }
}
