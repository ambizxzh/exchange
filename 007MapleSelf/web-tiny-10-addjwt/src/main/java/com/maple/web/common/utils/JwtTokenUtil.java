package com.maple.web.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *Jwt生成Token的工具类
 *@author zxzh
 *@date 2021/5/8
 */
@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String CLAIM_KEY_USERNAME = "sub";//负载的用户名(subject,主题)
    private static final String CLAIM_KEY_CREATED = "created";//负载的用户创建时间
    @Value("${jwt.secret}")//向yml中的自定义jwt参数取值
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /*
    * 根据负载生成JWT的token
    * */
    private  String generateToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)//设置负载
                .setExpiration(generateExpirationDate())//设置过期时间: 系统现在的时间 + 过期时间间隔
                .signWith(SignatureAlgorithm.HS512,secret)//生成算法 及所需的 密钥
                .compact();
    }
    /*
    * 根据用户信息生成token
    * */
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
    /*
    * 设置token的过期时间
    * */
    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /*
    * 从token中获取JWT中的负载
    * */
    private Claims getClaimsFromToken(String token){
        Claims claims = null;
        try{
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            LOGGER.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }
    /*
    * 从token中获取登录用户名
    * */
    public String getUserNameFromToken(String token){
        String username;
        try{
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        }catch (Exception e){
            username = null;
        }
        return username;
    }
    /*
    * 从token中获取过期时间
    * */
    private Date getExpiredDateFromToken(String token){
        Date date;
        try{
            Claims claims = getClaimsFromToken(token);
            date = claims.getExpiration();
        }catch (Exception e){
            date = null;
        }
        return date;
    }
    /*
    * 判断token是否因过期而失效,true代表失效,false代表还未过期
    * */
    private boolean isTokenExpired(String token){
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }
    /*
    * 验证token是否有效: 用户名正确&&没有过期
    * @param: token 客户端传入的token
    * @param userDetails 从数据库中查询出来的用户信息
    * */
    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /*
    * 判断token是否可以被刷新,当token还没过期时,可以进行刷新,以便进行续签
    * */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }
    /*
    * 刷新token
    * */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
}
