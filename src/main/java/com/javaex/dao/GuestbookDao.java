package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	@Autowired
	private SqlSession sqlsession;
	
	//리스트
	public List<GuestbookVo> guestbookList() {
		return sqlsession.selectList("gb.list");
	}
	
	//방명록에 글 작성하기
	public int insert(Map<String, Object> map) {
		return sqlsession.insert("gb.insert", map);
	}
	//ajax방명록에 글 작성하기
	public void apiInsert(GuestbookVo guestbookVo) {
		sqlsession.insert("gb.apiInsert", guestbookVo);
	}
	public GuestbookVo apiSelect(int no) {
		return sqlsession.selectOne("gb.selectOneByNo", no);
	}
	
	//방명록에 있는 글 삭제하기
	public int delete(Map<String, Object> map) {
		return sqlsession.delete("gb.delete", map);
	}
	//ajax방명록에 있는 글 삭제하기
	public void apiDelete(int no, String password) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("password", password);
		sqlsession.delete("gb.delete", map);
	}
	
	//페이지 더 보기
	public List<GuestbookVo> selectGuestbookListPage(int page) {
		return sqlsession.selectList("gb.selectListByPage", page);
	}	
}