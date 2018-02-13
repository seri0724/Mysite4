package com.javaex.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlsession;
	
	public int join(Map<String, Object> map) {
		return sqlsession.insert("user.insert", map);
	}
	public UserVo getUser(Map<String, Object> map) {
		return sqlsession.selectOne("user.selectUserByEmailPW", map);
	}
	public UserVo modifyUser(int no) {
		return sqlsession.selectOne("user.selectUserByNo", no);
	}
	public int update(Map<String, Object> map) { 
		return sqlsession.update("user.update", map);
	}
	public UserVo getUser(String email) {
		UserVo userVo = sqlsession.selectOne("user.selectUserByEmail", email);
		return userVo;		
	}
}