package com.ramostear.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Created by Tan Chaohong (alias:ramostear)
 * @create-time 2019/8/2 0002-14:44
 * @modify by :
 * @since:
 */
public class JJWTUtils {

    public static final String SECRET_KEY = "admin-123456";
    /**
     * 创建JWT
     * @param type          数据类型，例如JWT
     * @param subject      主题
     * @param ttlMillis    过期时长
     * @return
     * @throws Exception
     */
    public static String create(String type,String subject,long ttlMillis) throws Exception{

        SignatureAlgorithm alg = SignatureAlgorithm.HS256;  //签名算法

        long startMillis = System.currentTimeMillis();
        Date now = new Date(startMillis);                   //签名时间

        Map<String,Object> claims = new HashMap<>();        //创建有效载荷(payload)
        claims.put("oid","b08f86af-35da-48f2-8fab-cef3904660bd");
        claims.put("org","www.ramostear.com");

        SecretKey secret = key();

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(type)
                .setIssuedAt(now)                           //签发时间
                .setSubject(subject)                        //主题：是JWT的主题，即JWT的拥有者（如UID,email,roles等）
                .signWith(alg,secret);
        if(ttlMillis > 0){
            long expMillis = startMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);                     //签名过期时间
        }

        return builder.compact();                           //生成JWT
    }

    /**
     * 解析JWT
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parse(String jwt) throws  Exception{
        SecretKey secret = key();                       //1.获取签名秘钥
        Claims claims = Jwts.parser()                   //2.开始解析JWT
                .setSigningKey(secret)                  //3.设置秘钥信息
                .parseClaimsJws(jwt).getBody();         //4.解析主体信息
        return claims;
    }

    /**
     * 生成签名秘钥
     * @return
     */
    private static SecretKey key(){
        byte[] encodeKey = Base64Codec.BASE64.decode(SECRET_KEY);
        SecretKey key = new SecretKeySpec(encodeKey,0,encodeKey.length,"AES");
        return key;
    }
}
