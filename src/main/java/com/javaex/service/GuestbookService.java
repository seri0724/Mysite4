package com.javaex.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookDao guestbookDao;
	
	//리스트
	public List<GuestbookVo> list() {
		return guestbookDao.guestbookList();
	}
	
	//방명록에 글 작성하기
	public int insert(Map<String, Object> map) {
		return guestbookDao.insert(map);
	}
	//ajax방명록에 글 작성하기
	public GuestbookVo apiInsert(GuestbookVo guestbookVo) {
		guestbookDao.apiInsert(guestbookVo);
		int no = guestbookVo.getNo();
		return guestbookDao.apiSelect(no);
	}
	
	//방명록에 있는 글 삭제하기
	public int delete(Map<String, Object> map) {
		return guestbookDao.delete(map);
	}
	//ajax방명록에 있는 글 삭제하기
	public void apiDelete(int no, String password) {
		guestbookDao.apiDelete(no, password);
	}
	
	//페이지 더 보기
	public List<GuestbookVo> getGuestbookListPage(int page) {
		return guestbookDao.selectGuestbookListPage(page);
	}	
}