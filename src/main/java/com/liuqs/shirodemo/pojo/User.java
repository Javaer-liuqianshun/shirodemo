package com.liuqs.shirodemo.pojo;

import java.io.Serializable;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2017-12-09
 * @ Modified:
 **/
public class User implements Serializable {

    //主键id
    private Integer id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 手机号
    private String mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
