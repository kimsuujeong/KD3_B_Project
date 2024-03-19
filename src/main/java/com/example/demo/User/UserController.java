package com.example.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.DTO.UserDTO;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/Testmain") // test home
	public String Testmain() {
		return "Testmain";
	}

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/joinForm")
    public String joinForm(@ModelAttribute UserDTO userDTO) {
        userService.InsertUser(userDTO);
        return "joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        return "loginForm";
    }
	

}