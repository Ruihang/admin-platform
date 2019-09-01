package com.louis.mongo.admin.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.louis.mongo.admin.model.SysUser;
import com.louis.mongo.admin.security.JwtAuthenticationToken;
import com.louis.mongo.admin.service.SysUserService;
import com.louis.mongo.admin.util.PasswordUtils;
import com.louis.mongo.admin.util.SecurityUtils;
import com.louis.mongo.admin.vo.LoginBean;
import com.louis.mongo.core.http.HttpResult;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class SysLoginController {
    @Autowired
    private Producer producer;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = "captcha.jpg")
    public void cpatcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Cache-Control","no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存验证码到session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image,"jpg",out);
        IOUtils.closeQuietly(out);
    }

    @PostMapping(value = "/login")
    public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) {
        String username = loginBean.getAccount();
        String password = loginBean.getPassword();
        String captcha = loginBean.getCaptcha();
        // 从session中获取之前保存的验证码，跟前台传来的验证码进行匹配
        Object kaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (kaptcha == null) {
            return HttpResult.error("验证码已失效");
        }
        if (!captcha.equals(kaptcha)) {
            return HttpResult.error("验证码不正确");
        }
        //用户信息
        SysUser sysUser = sysUserService.findByName(username);
        if (sysUser == null) {
            return  HttpResult.error("账号不存在");
        }
        if (!PasswordUtils.matches(sysUser.getSalt(), password ,sysUser.getPassword())) {
            return HttpResult.error("密码不正确");
        }
        // 账号锁定
        if (sysUser.getStatus() == 0){
            return HttpResult.error("账号已被锁定，请联系管理员");
        }
        // 系统登录认证
        JwtAuthenticationToken token = SecurityUtils.login(request,username,password,authenticationManager);
        System.out.println(token);
        return HttpResult.ok(token);
    }
}
