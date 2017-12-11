package com.liuqs.shirodemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2017-12-10
 * @ Modified:
 **/
@Controller
@RequestMapping(value = "/shiro")
public class ShiroController {

    Logger logger = LoggerFactory.getLogger(ShiroController.class);

    @RequestMapping(value = "/login",produces = "text/html;charset=UTF-8")
    public String login(String username,String password){
        // 获取当前的Subject对象
        Subject subject = SecurityUtils.getSubject();
        // 判断是否已经被认证
        if (!subject.isAuthenticated()){
            // 获取UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                // 执行登录
                subject.login(token);
            }catch (UnknownAccountException e){
                logger.info("登录失败 --->:" + e.getMessage());
            }catch (CredentialsException e){
                logger.info("登录失败 --->:密码错误!");
            }catch (UnsupportedTokenException e){
                logger.info("登录失败 --->:" + e.getMessage());
            }catch (AuthenticationException e){

            }
        }
        return "redirect:/list.jsp";
    }
}
