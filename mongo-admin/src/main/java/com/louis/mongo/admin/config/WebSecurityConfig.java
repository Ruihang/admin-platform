package com.louis.mongo.admin.config;

import com.louis.mongo.admin.security.JwtAuthenticationFilter;
import com.louis.mongo.admin.security.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解，如 @PreAuthorize()
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable() // 禁用cors，由于使用jwt所以这里不需要csrf
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // 不拦截 跨域预请求
                .antMatchers("/webjars/**").permitAll()             // 不拦截 web jars
                .antMatchers("/druid/**").permitAll()               // 不拦截 查看SQL监控
                .antMatchers("/").permitAll()                       // 不拦截 首页
                .antMatchers("/login").permitAll()                  // 不拦截 登录
                .antMatchers("/swagger-ui.html").permitAll()        // 不拦截 swagger
                .antMatchers("/swagger-resources/**").permitAll()   // ~
                .antMatchers("/v2/api-docs").permitAll()            // ~
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()    // ~
                .antMatchers("/captcha.jpg**").permitAll()          // 不拦截 验证码
                .antMatchers("/actuator/**").permitAll()            // 不拦截 服务器监控
                .anyRequest().authenticated();  // 其他所有请求需要身份认证

        // 退出登录处理器
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        // Token验证过滤器
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
