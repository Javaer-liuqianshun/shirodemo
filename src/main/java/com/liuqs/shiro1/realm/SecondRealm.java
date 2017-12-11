package com.liuqs.shiro1.realm;

import com.liuqs.shirodemo.dao.MyBatisDao;
import com.liuqs.shirodemo.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ Author: liuqianshun
 * @ Description:认证方法
 * @ Date: Created in 2017-12-10
 * @ Modified:
 **/
public class SecondRealm extends AuthenticatingRealm {
    Logger logger = LoggerFactory.getLogger(SecondRealm.class);

    @Autowired
    private MyBatisDao myBatisDao;

    /**
     *  shiro认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("----> secondRealm;");

        // 1.把AuthenticationToken 装换为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        // 2.从UsernamePassToken中获取username
        String mobile = usernamePasswordToken.getUsername();
        // 3.调用数据库方法,从数据库中查询mobile对应的用户记录
        User user = myBatisDao.queryUserByMobile(mobile);
        // 4.若用户不存在,则可以抛出 异常UnKnownAccountException 异常
        if (null == user) {
            throw new UnknownAccountException("---->用户不存在!");
        }
        // 5.根据用户信息的情况,决定是否需要抛出其他的AuthenticationException 异常
        if ("17628095836".equals(mobile)) {
            throw new UnsupportedTokenException("---->用户被锁!");
        }

        // 6. 用户存在,再由shiro框架提供的SimpleAuthenticationInfo对象来
        // 对比页面输入的password和数据库查询的密码,返回AuthenticationInfo 对象
        // SimpleAuthenticationInfo(Object principal, Object credentials, String realmName)
        // 1)principal:认证的信息,可以是username,也可以是查询数据库返回的实体类信心
        // 2)credentials:数据库中获取的密码
        // 3)realmName:当前Realm对象的name,调用getName()
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(mobile,user.getMobile(),getName());
        return authenticationInfo;
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
