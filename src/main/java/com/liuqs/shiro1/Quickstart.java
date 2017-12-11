package com.liuqs.shiro1;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class Quickstart {

    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);


    public static void main(String[] args) {

        // The easiest way to create a Shiro SecurityManager with configured
        // realms, users, roles and permissions is to use the simple INI config.
        // We'll do that by using a factory that can ingest a .ini file and
        // return a SecurityManager instance:

        // Use the shiro.ini file at the root of the classpath
        // (file: and url: prefixes load from files and urls respectively):
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        // for this simple example quickstart, make the SecurityManager
        // accessible as a JVM singleton.  Most applications wouldn't do this
        // and instead rely on their container configuration or web.xml for
        // webapps.  That is outside the scope of this simple quickstart, so
        // we'll just do the bare minimum so you can continue to get a feel
        // for things.
        SecurityUtils.setSecurityManager(securityManager);

        // Now that a simple Shiro environment is set up, let's see what you can do:

        // 获取当前Subject.调用SecurityUtils.getSubject()
        Subject currentUser = SecurityUtils.getSubject();

        // 使用session.调用Subject的getSession()
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("---->使用了shiro中的session!");
        }

        // 判断当前用户是否已经被认证(即是否已经登录)
        if (!currentUser.isAuthenticated()) {
            log.info("---->当前用户没有认证!");
            // 把用户名和密码封装成UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            // shiro rememberMe功能,登录一次后,记录登录
            token.setRememberMe(true);
            try {
                // 使用Subject的login(参数为UsernamePasswordToken对象)方法进行登录
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                log.info("---->没有找到该用户名" + token.getPrincipal());
                return;
            } catch (IncorrectCredentialsException ice) {
                log.info("---->用户 " + token.getPrincipal() + " 密码错误!");
            } catch (LockedAccountException lae) {
                log.info("---->" + token.getPrincipal() + "账号被锁!");
            }
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        //say who they are:
        //print their identifying principal (in this case, a username):
        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

        // 测试当前用户是否有某个角色
        if (currentUser.hasRole("schwartz")) {
            log.info("---->May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }

        // 测试当前用户是否具备某个行为
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("---->You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        //a (very powerful) Instance Level permission:
        if (currentUser.isPermitted("user:delete:zhangsan")) {
            log.info("---->You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        //all done - log out!
        currentUser.logout();

        System.exit(0);
    }
}
