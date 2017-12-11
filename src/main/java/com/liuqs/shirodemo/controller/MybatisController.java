package com.liuqs.shirodemo.controller;

import com.liuqs.shirodemo.pojo.User;
import com.liuqs.shirodemo.service.MybatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;

/**
 * 测试SSM是否整合成功
 */
@Controller
@RequestMapping("mapper")
public class MybatisController {

    private static Logger logger = LoggerFactory.getLogger(MybatisController.class);

	@Autowired
	private MybatisService mybatisService;

	@RequestMapping(value = "", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String fn1() {
		List<User> list = mybatisService.queryAllUser();
		Iterator<User> iterator = list.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		return "查询user表成功";
	}
}
