package com.ramostear.jjwt;

import io.jsonwebtoken.Claims;

/**
 * @author : Created by Tan Chaohong (alias:ramostear)
 * @create-time 2019/8/2 0002-14:47
 * @modify by :
 * @since:
 */
public class JavaJWT {

    public static void main(String[] args) throws Exception {

        String jwt = JJWTUtils.create("jwt","{id:1001,name:ramostear,roles:admin}",600000);
        System.out.println(jwt);

        String signature = jwt;
        Claims claims = JJWTUtils.parse(signature);

        System.out.println(claims.toString());

    }
}
