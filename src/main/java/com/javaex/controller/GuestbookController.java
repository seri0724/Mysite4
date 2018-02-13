package com.javaex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.GuestbookService;
import com.javaex.vo.GuestbookVo;

@Controller
@RequestMapping("/gb")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model) {
		
		List<GuestbookVo> list = guestbookService.list();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST) //no,name,password,content,reg_date
	public String add(@RequestParam Map<String, Object> map) {
		
		int result = guestbookService.insert(map);
		
		if(result == 1) {
		} else {
		} return "redirect:/gb/list";
	}
	@RequestMapping(value="/deleteform", method=RequestMethod.GET)
	public String deleteform() {
		return "guestbook/deleteform";
	}
	@RequestMapping(value="/delete", method=RequestMethod.POST) //no,password
	public String delete(@RequestParam Map<String, Object> map) {
		
		int result = guestbookService.delete(map);
		
		if(result == 1) { 
			return "redirect:/gb/list";
		} else {
			return "redirect:/gb/list";
		}
	}
	@RequestMapping(value="/listajax", method=RequestMethod.GET) 
	public String listajax() {
		return "guestbook/listajax";
	}
}