package com.atguigu.srb.base.util;


import com.atguigu.srb.common.exception.BusinessException;
import com.atguigu.srb.common.result.ResponseEnum;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtUtils {

    //token有效时间
    private static long tokenExpiration = 1000*60*60*3;

    //用于加密的字符串
    private static String tokenSignKey = "s1r2b3ABC";

    //生成密钥
    private static Key getKeyInstance(){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] bytes = DatatypeConverter.parseBase64Binary(tokenSignKey);
        return new SecretKeySpec(bytes,signatureAlgorithm.getJcaName());
    }

    public static String createToken(Long userId, String userName) {
        String token = Jwts.builder()
                .setSubject("SRB-USER")
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                //载荷：自定义信息
                .claim("userId", userId)
                .claim("userName", userName)
                //设置签名（加密）
                .signWith(SignatureAlgorithm.HS512, getKeyInstance())
                .compressWith(CompressionCodecs.GZIP)
                //组装JWT
                .compact();

        return token;
    }

    /**
     * 判断token是否有效
     * @param token
     */
    public static boolean checkToken(String token) {
        if(StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            //解析token
            Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("解析token时发生错误："+e);
            return false;
        }
    }


    public static Long getUserId(String token) {
        Claims claims = getClaims(token);
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }

    public static String getUserName(String token) {
        Claims claims = getClaims(token);
        return (String)claims.get("userName");
    }

    public static void removeToken(String token) {
        //JWT Token无需删除，客户端扔掉即可。
    }

    /**
     * 校验token并返回Claims
     * @param token
     */
    private static Claims getClaims(String token) {
        if(StringUtils.isEmpty(token)) {
            // LOGIN_AUTH_ERROR(-211, "未登录"),
            throw new BusinessException(ResponseEnum.LOGIN_AUTH_ERROR);
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return claims;
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.LOGIN_AUTH_ERROR);
        }
    }
}

