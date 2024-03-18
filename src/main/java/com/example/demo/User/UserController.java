package com.example.demo.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.DAO.UserRepository;
import com.example.demo.DTO.UserDTO;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	
	@RequestMapping("joinForm") // 회원가입 From
	public String joinForm(UserDTO userDTO) {
		return "JoinForm";
	}

}
