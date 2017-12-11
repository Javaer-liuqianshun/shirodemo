package com.liuqs.shirodemo.service.impl;

import com.liuqs.shirodemo.dao.MyBatisDao;
import com.liuqs.shirodemo.pojo.User;
import com.liuqs.shirodemo.service.MybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MybatisServiceImpl implements MybatisService {

	@Autowired
	private MyBatisDao myBatisDao;

	public List<User> queryAllUser() {
		return myBatisDao.queryAllUser();
	}

}
