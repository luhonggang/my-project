package com.renqi.market.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.renqi.market.exception.HandleTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用标准的jwt来实现token工具类
 * @author luhonggang
 * @date 2018/9/8
 * @since 1.0
 */
public class JwtTokenUtil {
    // 私钥 base64EncodedSecretKey
    final private static String BASE64_ENCODED_SECRETKEY = "www.renqi.market";
    // 过期时间,测试使用60秒 用户登录会话时间
    private static final long TOKEN_EXP = 1000 * 60;
    private static final String APP_KEY = "www.renqi.market"; //进行数字签名的私钥，一定要保管好，不能和我一样写到博客中。。。。。

    public static String getToken(String userName) {
        //String tokenExpireTime = System.currentTimeMillis() + TOKEN_EXP;
        return Jwts.builder()
                .setSubject(userName)
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP)) /*过期时间*/
                .signWith(SignatureAlgorithm.HS256, BASE64_ENCODED_SECRETKEY)
                .compact();
    }

    /**
     * 检查token,只要不正确就会抛出异常
     * @param token   传递的token
     * @throws ServletException 异常
     */
    public static void checkToken(String token) throws ServletException {
        try {
            final Claims claims = Jwts.parser().setSigningKey(BASE64_ENCODED_SECRETKEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e1) {
            throw new ServletException("token expired");
        } catch (Exception e) {
            // throw new HandleTokenException(null,SystemCode.TOKEN_IS_INVALID.getCode(),SystemCode.TOKEN_IS_INVALID.getMsg());
            throw new ServletException("other token exception");
        }
    }

    public static String createToken() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)//header
                .withClaim("name", "zwz")//payload
//                .withClaim("age", "18")
                .sign(Algorithm.HMAC256("secret"));//加密
        return token;
    }

    public static void verifyToken(String token,String key) throws Exception {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key))
                .build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        System.out.println(claims.get("name").asString());

    }

    /**
     * 一个JWT实际上就是一个字符串，它由三部分组成，头部(Header)、载荷(Payload)与签名(Signature)
     * @param id 当前用户ID
     * @param issuer 该JWT的签发者，是否使用是可选的
     * @param subject 该JWT所面向的用户，是否使用是可选的
     * @param ttlMillis 什么时候过期，这里是一个Unix时间戳，是否使用是可选的
     * @param audience 接收该JWT的一方，是否使用是可选的
     * @return
     */
    public static String createJWT(String id,String issuer,String subject,long ttlMillis, String audience){


        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(APP_KEY);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm,signingKey);
        //设置Token的过期时间
        if(ttlMillis >=0){
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            jwtBuilder.setExpiration(exp);
        }

        return jwtBuilder.compact();

    }

    /**
     * 解密token
     * @param jwtToken
     */
    public static void checkTokenIsExpiredTime(String jwtToken) throws HandleTokenException {
        try{
            final Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(APP_KEY))
                    .parseClaimsJws(jwtToken).getBody();
        } catch(ExpiredJwtException e){
            e.printStackTrace();
            throw new HandleTokenException(e.getMessage(),SystemCode.TOKEN_IS_INVALID.getCode(),SystemCode.TOKEN_IS_INVALID.getMsg());
        }
    }

    //私钥解密token信息
    public static Claims verifyToken(String jwt) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(APP_KEY))
                .parseClaimsJws(jwt).getBody();

    }
    public static void main(String[] args) throws Exception {
//        String token = JwtTokenUtil.getToken("123456789");
//        System.out.println("token : "+token);
//        checkToken(token);
//        System.out.println("------执行成功------");
//
//        // https://github.com/auth0/java-jwt
//        String token2 = JwtTokenUtil.createToken();
//        verifyToken(token2,"secret");

        //
        String userId = "WKSH121321KKsdfk";
        String issuer = "http://whytfjybj.com";
        String subject = "师范学院";
        long ttlMillis = 1000 * 60;
        String audience = "schoolNo";
        String token3 = JwtTokenUtil.createJWT(userId,issuer,subject,ttlMillis,audience);
        //打印出token信息
        System.out.println("标准的token : "+token3);


//        解密token信息
        Claims claims = JwtTokenUtil.verifyToken("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJXS1NIMTIxMzIxS0tzZGZrIiwic3ViIjoi5biI6IyD5a2m6ZmiIiwiaWF0IjoxNTM2NDYxNDQzLCJpc3MiOiJodHRwOi8vd2h5dGZqeWJqLmNvbSIsImF1ZCI6InNjaG9vbE5vIiwiZXhwIjoxNTM2NDYxNTAzfQ.keejkSy0-Za27XcprZYAN8QkvK0oCmH5K32G3ggf0n8");
        System.out.println("---------------------------解密的token信息----------------------------------");
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());

}
}
