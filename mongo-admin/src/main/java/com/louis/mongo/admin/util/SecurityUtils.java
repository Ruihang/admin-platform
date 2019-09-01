package com.louis.mongo.admin.util;

import com.louis.mongo.admin.security.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtils {
    /**
     * 获取令牌并根据令牌获取登录认证信息
     * @param request
     */
    public static void checkAuthentication(HttpServletRequest request) {
        Authentication authentication = JwtTokenUtils.getAuthenticationFromToken(request);
    }

    public static Authentication getAuthentication() {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 系统登录认证
     * @param request
     * @param username
     * @param password
     * @param authenticationManager
     * @return
     */
    public static JwtAuthenticationToken login(HttpServletRequest request, String username, String password, AuthenticationManager authenticationManager) {
        //封装登录token
        JwtAuthenticationToken token = new JwtAuthenticationToken(username, password);
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //执行登录认证过程
        Authentication authentication = authenticationManager.authenticate(token);
        //认证成功，存储认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成令牌并返回给客户端
        token.setToken(JwtTokenUtils.generateToken(authentication));
        return token;
    }

    public static Object getUsername(Authentication authentication) {
        String username = null;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }
}
