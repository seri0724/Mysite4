package com.javaex.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(@RequestParam(value="crtPage", required=false, defaultValue="1") Integer crtPage, 
					   @RequestParam(value="kwd", required=false, defaultValue="") String kwd, Model model) {
		Map<String, Object> bMap = boardService.getBoardList(crtPage, kwd);
		model.addAttribute("bMap", bMap);
		return "board/list";
	}
	
	@RequestMapping(value="/writeform", method=RequestMethod.GET)
	public String writeform(HttpSession session) {
		if(session.getAttribute("authUser") != null) { //로그인상태일때
			return "board/writeform";
		} else {                                       //로그인 상태가 아닐때
			return "redirect:/user/loginform";
		}
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@ModelAttribute BoardVo boardVo, HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		boardService.write(boardVo);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/read/{no}", method=RequestMethod.GET)
	public String read(@PathVariable("no") int no, Model model) {
		BoardVo boardVo = boardService.getBoardForRead(no);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("newLine", "\r\n"); //줄바꿈 문자 치환용
		return "board/read";
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.GET)
	public String delete(@ModelAttribute BoardVo boardVo, HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		boardService.remove(boardVo);	
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/modifyform", method=RequestMethod.GET)
	public String modifyform(@RequestParam("no") int no, Model model) {
		BoardVo boardVo = boardService.getBoardForModify(no);
		model.addAttribute("boardVo", boardVo);
		return "board/modifyform";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modifyform(@ModelAttribute BoardVo boardVo, HttpSession session, Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		int userNo = authUser.getNo();
		boardVo.setUserNo(userNo);
		boardService.modifyBoard(boardVo);
		return "redirect:/board/list";
	}
}