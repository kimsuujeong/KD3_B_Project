package com.example.demo.userJoin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.user.User;

@Controller
public class UserJoinController {
	
	@Autowired
	UserJoinService userService;
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "TestHtml/user/join";
	}

	@PostMapping("/joinForm") // test join
	public String joinForm(@ModelAttribute User userDTO, Model model) {
		int idr = userService.idchk(userDTO.getUserID());
		int emr = userService.emailchk(userDTO.getEmail());
		int unr = userService.namechk(userDTO.getUserName());
		try {
			if (idr == 1) {
				System.out.println("중복된 아이디가 있습니다");
				model.addAttribute("errorMessage", "이미 사용중인 값이 있습니다");
				return "TestHtml/user/joinForm";
			} else if (emr == 1) {
				System.out.println("중복된 이메일이 있습니다");
				model.addAttribute("errorMessage", "이미 사용중인 값이 있습니다");
				return "TestHtml/user/joinForm";
			} else if (unr == 1) {
				System.out.println("중복된 닉네임이 있습니다");
				model.addAttribute("errorMessage", "이미 사용중인 값이 있습니다");
				return "TestHtml/user/joinForm";
			} else if (idr == 0 && emr == 0 && unr == 0) {
				userService.InsertUser(userDTO);
				return "redirect:/login";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/Testmain";
	}

	@ResponseBody
	@RequestMapping(value = "/idchk")
	public int idchk(@RequestParam("userID") String userID) {
		return userService.idchk(userID);
	}

	@ResponseBody
	@RequestMapping(value = "/emailchk")
	public int emailchk(@RequestParam("email") String email) {
		return userService.emailchk(email);
	}

	@ResponseBody
	@RequestMapping(value = "/namechk")
	public int namechk(@RequestParam("userName") String userName) {
		return userService.namechk(userName);
	}

}
