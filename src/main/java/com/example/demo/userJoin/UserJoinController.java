package com.example.demo.userJoin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.user.User;

@Controller
public class UserJoinController {
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "TestHtml/user/join";
	}
//
//	@PostMapping("/joinForm")
//	public String joinForm(@ModelAttribute User userDTO) {
//		// join form 기능 따로 만들기
//		return "TestHtml/user/joinForm";
//	}

}
