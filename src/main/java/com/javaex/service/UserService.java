package com.javaex.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public int join(Map<String, Object> map) {
		return userDao.join(map);
	}
	public UserVo login(Map<String, Object> map) {
		return userDao.getUser(map);
	}
	public UserVo modifyform(int no) {
		return userDao.modifyUser(no);
	}
	public int modify(Map<String, Object> map) {
		return userDao.update(map);
	}
	public boolean emailCheck(String email) {
		boolean result;
		UserVo userVo = userDao.getUser(email);
		if(userVo != null) {
			result = false;
		} else {
			result= true;
		}
		return result;
	}
}