package com.liuqs.shirodemo.dao;

import com.liuqs.shirodemo.pojo.User;

import java.util.List;

public interface MyBatisDao {

	List<User> queryAllUser();

    User queryUserByUsername(String username);

    User queryUserByMobile(String mobile);
}
