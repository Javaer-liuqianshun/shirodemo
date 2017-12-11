package com.liuqs.shiro1.realm;

import com.liuqs.shirodemo.dao.MyBatisDao;
import com.liuqs.shirodemo.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @ Author: liuqianshun
 * @ Description:认证方法
 * @ Date: Created in 2017-12-10
 * @ Modified:
 **/
public class ShiroRealm extends AuthorizingRealm {

    Logger logger = LoggerFactory.getLogger(ShiroRealm.class);


    @Autowired
    private MyBatisDao myBatisDao;

    /**
     *  shiro认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("----> shiroRealm;");

        // 1.把AuthenticationToken 装换为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        // 2.从UsernamePassToken中获取username
        String username = usernamePasswordToken.getUsername();
        // 3.调用数据库方法,从数据库中查询username对应的用户记录
        User user = myBatisDao.queryUserByUsername(username);
        // 4.若用户不存在,则可以抛出 异常UnKnownAccountException 异常
        if (null == user) {
            throw new UnknownAccountException("---->用户不存在!");
        }
        // 5.根据用户信息的情况,决定是否需要抛出其他的AuthenticationException 异常
        if ("linghl".equals(username)) {
            throw new UnsupportedTokenException("---->用户被锁!");
        }

        // 6. 用户存在,再由shiro框架提供的SimpleAuthenticationInfo对象来
        // 对比页面输入的password和数据库查询的密码,返回AuthenticationInfo 对象
        // SimpleAuthenticationInfo(Object principal, Object credentials, String realmName)
        // 1)principal:认证的信息,可以是username,也可以是查询数据库返回的实体类信心
        // 2)credentials:数据库中获取的密码
        // 3)realmName:当前Realm对象的name,调用getName()
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,user.getPassword(),getName());
        return authenticationInfo;
    }

    /**
     * shiro授权
     * @param principals
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("--->doGetAuthorizationInfo....");
        // 1.从PrincipalCollection中来获取登录用户的信息
        Object principal = principals.getPrimaryPrincipal();
        // 2.利用登录的用户的信息来获取当前用户的角色或权限
        Set<String> roles = new HashSet<String>();
        roles.add("user");
        if ("admin".equals(principal)){
            roles.add("admin");
        }
        // 3.创建SimpleAuthorizationInfo,并设置其reles属性
        AuthorizationInfo AuthorizationInfo = new SimpleAuthorizationInfo(roles);
        return AuthorizationInfo;
    }

//    public static void main(String[] args) {
//        // 加密方式
//        String hashAlgorithmName = "MD5";
//        // 密码
//        String credentials = "123456";
//        // 盐值
//        String salt = null;
//        // 加密次数
//        int hashIterations = 1024;
//        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
//        System.out.println(result);
//    }
}
