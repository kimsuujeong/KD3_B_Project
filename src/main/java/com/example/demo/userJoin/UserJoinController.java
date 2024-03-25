package com.example.demo.userJoin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.user.User;

public class UserJoinController {
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "TestHtml/user/joinForm";
	}

	@PostMapping("/joinForm")
	public String joinForm(@ModelAttribute User userDTO) {
		// join form 기능 따로 만들기
		return "TestHtml/user/joinForm";
	}

}
