package com.example.demo.chinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.User;
import com.example.demo.userJoin.UserJoinService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/userUpdate")
public class ChanInfoController {

	@Autowired
	ChanInfoService chanInfoService;

	@Autowired
	UserJoinService userService;

	@GetMapping("namemodify")
	public String nameUpdate(HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedUser");

		if (user == null) {
			return "redirect:/login";
		}

		model.addAttribute("user", user);
		return "TestHtml/Mypage/name_modify";
	}

	@PostMapping("namemodify")
	public String nameUpdate(@RequestParam("userName") String userName, HttpSession session, Model model) {
		User loggedUser = (User) session.getAttribute("loggedUser");

		if (loggedUser == null) {
			return "redirect:/login";
		}
		int result = userService.namechk(userName);

		if (result > 0) {
			model.addAttribute("errorMessage", "이미 사용중인 이름입니다");
			return "TestHtml/Mypage/name_modify";
		} else {
			loggedUser.setUserName(userName);
			chanInfoService.updateName(loggedUser);
			return "redirect:/userUpdate";
		}
	}

	@GetMapping("emmodify")
	public String emailUpdate(HttpSession session, Model model) {
		User loguser = (User) session.getAttribute("loggedUser");

		if (loguser == null) {
			return "redirect:/TestHtml/login";
		}
		return "TestHtml/Mypage/email_modify";
	}

	@PostMapping("emmodify")
	public String emailUpdate(@RequestParam("email") String email, HttpSession session, Model model) {
		User loggedUser = (User) session.getAttribute("loggedUser");

		if (loggedUser == null) {
			return "redirect:/login";
		}

		int result = userService.emailchk(email);

		if (result > 0) {
			model.addAttribute("errorMessage", "이미 사용중인 이름입니다");
			return "TestHtml/Mypage/email_modify";
		} else {
			loggedUser.setEmail(email);
			chanInfoService.updateEmail(loggedUser);
			return "redirect:/userUpdate";
		}
	}

	@GetMapping("pwmodify")
	public String passwordUpdate(HttpSession session, Model model) {
		User loggedUser = (User) session.getAttribute("loggedUser");

		if (loggedUser == null) {
			return "redirect:/login";
		}
		return "TestHtml/Mypage/passwd_modify";
	}
	
	@PostMapping("pwmodify")
	public String passwordUpdate(@RequestParam("oldPw")String oldPw,
			@RequestParam("newPw")String newPw,
			@ModelAttribute User user, HttpSession session, Model model) {
		User loggedUser = (User) session.getAttribute("loggedUser");
		
		if (loggedUser == null) {
			return "redirect:/login";
		}
		int result=chanInfoService.checkpw(loggedUser.getUserID(),oldPw);
		
		if(result>0) {
			loggedUser.setPassword(newPw);
			chanInfoService.updatePw(loggedUser);
			return "redirect:/userUpdate";
		}else {
			model.addAttribute("errorMessage", "비밀번호가 틀립니다");
			return "TestHtml/Mypage/passwd_modify";
		}
		
	}
}
