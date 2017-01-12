package com.daou.setlist.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	/**
	 * 메인 페이지
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/")
	public String main(Model model) {
		log.debug("메인 페이지");
		
		model.addAttribute("welcome", "환영합니다."); 
		return "home";
	}
}