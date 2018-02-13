package com.javaex.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/joinform", method=RequestMethod.GET) //처리할 요청 URL을 지정, 실제 요청 URL 은 class의 @RequestMapping값과 메소드의 @RequestMapping값의 조합으로 지정
	public String joinform() {
		return "user/joinform";
	}
	@RequestMapping(value="/join", method=RequestMethod.POST) //name,email,password,gender
	public String join(@RequestParam Map<String,Object> map) {
		
		int result = userService.join(map);
		
		if(result == 1) {
			return "user/joinsuccess";
		} else {
			return "redirect:/main";
		}
	}
	@RequestMapping(value="/loginform", method=RequestMethod.GET) 
	public String loginForm() {									  
		return "user/loginform";
	}
	@RequestMapping(value="/login", method=RequestMethod.POST) //email,password
	public String login(@RequestParam Map<String, Object> map,
						HttpSession session) {
		
		UserVo authUser = userService.login(map);
		
		if(authUser != null) {
			session.setAttribute("authUser", authUser);
			return "main/index";
		} else {
			return "redirect:/user/loginform?result=fail";
		}
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/main"; 
	}
	@RequestMapping(value="/modifyform", method=RequestMethod.GET)
	public String modifyform(@RequestParam("no") int no, Model model) {
		UserVo modifyUser = userService.modifyform(no);
		model.addAttribute("modifyUser", modifyUser);
		return "user/modifyform";
	}
	@RequestMapping(value="/modify", method=RequestMethod.POST) //name,password,gender,no
	public String modify(@RequestParam Map<String, Object> map,
						 HttpSession session) {
		
		int result = userService.modify(map);
		UserVo loginUser = (UserVo) session.getAttribute("authUser");
		
		if(result == 1) {
			loginUser.setName((String)map.get("name"));
			return "main/index";
		} else {
			return "redirect:/user/modifyform";
		}
	}
}