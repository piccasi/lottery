package com.lottery.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lottery.dao.IUserDao;
import com.lottery.pojo.User;
import com.lottery.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;
	
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return this.userDao.selectByPrimaryKey(userId);
	}

}
