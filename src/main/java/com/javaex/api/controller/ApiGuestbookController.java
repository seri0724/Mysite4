package com.javaex.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestbookService;
import com.javaex.vo.GuestbookVo;

@Controller
public class ApiGuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	//페이지 더 보기
	@ResponseBody
	@RequestMapping(value="/gb/api/list", method=RequestMethod.POST)
	public List<GuestbookVo> apiList(@RequestParam("page") int page) {
		
		List<GuestbookVo> guestbookList = guestbookService.getGuestbookListPage(page);
		//System.out.println(guestbookList.toString());
		return guestbookList;
	}
	
	//방명록에 글 작성하기(페이지 넘어가지 않고 기존에 있는 리스트에서 글 작성한거 하나씩 추가)
	@ResponseBody
	@RequestMapping(value="/gb/api/insert", method=RequestMethod.POST)
	public GuestbookVo apiInsert(@RequestBody GuestbookVo guestbookVo) {
		
		GuestbookVo guestbook = guestbookService.apiInsert(guestbookVo);
		return guestbook;
	}
	
	//방명록에 있는 글 삭제하기(삭제버튼 누르면 모달창 나와서 비밀번호 입력하여 삭제하기)
	@ResponseBody
	@RequestMapping(value="/gb/api/delete", method=RequestMethod.POST)
	public int apiDelete(@RequestBody GuestbookVo guestbookVo) {
		
		int no = guestbookVo.getNo();
		String password = guestbookVo.getPassword();
		
		guestbookService.apiDelete(no, password);
		return no;
	}
}