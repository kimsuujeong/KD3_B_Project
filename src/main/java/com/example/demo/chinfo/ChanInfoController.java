package com.example.demo.chinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.User;
import com.example.demo.userJoin.UserJoinService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ChanInfoController {

	@Autowired
	ChanInfoService chanInfoService;

	@Autowired
	UserJoinService userService;

	@GetMapping("/userUpdate/namemodify")
	public String nameUpdate(HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedInUser");

		if (user == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("errorMessage","");
		model.addAttribute("user", user);
		
		return "MyPage/nrs";
	}

	@PostMapping("/userUpdate/namemodify")
	public String nameUpdate(@RequestParam("userName") String userName, HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("errorMessage","");
		int result = userService.namechk(userName);

		if (result > 0) {
			model.addAttribute("errorMessage", "이미 사용중인 이름입니다");
			return "MyPage/nrs";		}
		else {
			loggedInUser.setUserName(userName);
			chanInfoService.updateName(loggedInUser);
			return "redirect:/userUpdate";
		}
	}
	
	
	// 이메일 변경은 뻄
//	@GetMapping("/userUpdate/emmodify")
//	public String emailUpdate(HttpSession session, Model model) {
//		User loggedInUser = (User) session.getAttribute("loggedInUser");
//
//		if (loggedInUser == null) {
//			return "redirect:/login";
//		}
//		return "TestHtml/Mypage/email_modify";
//	}

	@PostMapping("/userUpdate/emmodify")
	public String emailUpdate(@RequestParam("email") String email, HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";  
		} 
                                                    
		int result = userService.emailchk(email);

		if (result > 0) {
			model.addAttribute("errorMessage", "이미 사용중인 이름입니다");
			return "TestHtml/Mypage/email_modify";
		} else {
			loggedInUser.setEmail(email);
			chanInfoService.updateEmail(loggedInUser);
			return "redirect:/userUpdate";
		}
	} 

	
	@GetMapping("/userUpdate/pwmodify")
	public String passwordUpdate(HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}
		
		return "Login/pwrs";
	}
	
	@PostMapping("/userUpdate/pwmodify")
	public String passwordUpdate(@RequestParam("oldPw") String oldPw,
			@RequestParam("newPw") String newPw,
			 HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		int result=chanInfoService.checkpw(loggedInUser.getUserID(),oldPw);
		
		if(result>0) {
			loggedInUser.setPassword(newPw);
			chanInfoService.updatePw(loggedInUser);
			return "redirect:/userUpdate";
		}else {
			model.addAttribute("errorMessage", "비밀번호가 틀립니다");
			return "Login/pwrs";
		}
		
	}
}
