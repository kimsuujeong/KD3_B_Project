package com.example.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	
	@GetMapping("joinForm") // 회원가입 From
	public String joinForm(Model model) {
		System.out.println(userService.getAll());
		model.addAttribute("user",userService.getAll());
		return "JoinForm";
	}

}