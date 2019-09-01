package com.louis.mongo.admin.util;

import com.louis.mongo.admin.security.GrantedAuthorityImpl;
import com.louis.mongo.admin.security.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class JwtTokenUtils {

    private static final String SECRET = "abcdefgh";
    private static final String AUTHORITIES = "authorities";
    private static final String USERNAME = "username";
    private static final String CREATED = "created";
    private static final long EXPIRE_TIME = 60*60*1000*12;//12小时

    public static Authentication getAuthenticationFromToken(HttpServletRequest request) {
        Authentication authentication = null;
        String token = JwtTokenUtils.getToken(request);
        if (token != null) {
            // 请求令牌不能为空
            if (SecurityUtils.getAuthentication() == null) {
                Claims claims = getClaimsFromToken(token);
                if (claims == null) {
                    return null;
                }
                String username = claims.getSubject();
                if (username == null) {
                    return null;
                }
                if (isTokenExpired(token)) {
                    return null;
                }
                Object authors = claims.get(AUTHORITIES);
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (authors != null && authors instanceof List) {
                    for (Object author : (List) authors) {
                        authorities.add(new GrantedAuthorityImpl((String)((Map) author).get("authority")));
                    }
                }
                authentication = new JwtAuthenticationToken(username,null,authorities,token);
            }
        }
        return authentication;
    }

    private static boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
            e.printStackTrace();
        }
        return claims;
    }

    private static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String tokenHead = "Bearer ";
        if (token == null) {
            token = request.getHeader("token");
        } else {
            token = token.substring(tokenHead.length());
        }
        if ("".equals(token)) {
            token = null;
        }
        return token;
    }

    /**
     * 生成令牌
     * @param authentication
     * @return
     */
    public static String generateToken(Authentication authentication) {
        Map<String,Object> claims = new HashMap<>();
        claims.put(USERNAME,SecurityUtils.getUsername(authentication));
        claims.put(CREATED,new Date());
        claims.put(AUTHORITIES,authentication.getAuthorities());
        return generateToken(claims);
    }

    private static String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512,SECRET).compact();
    }
}
