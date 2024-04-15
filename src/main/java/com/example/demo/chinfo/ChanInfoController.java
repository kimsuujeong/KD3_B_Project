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
	
	@GetMapping("/pwcheck")
    public String passwordCheck(HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}
		return "/Login/pwcheck2";
    }

    @PostMapping("/pwcheck")
    public String checkPassword(@RequestParam("password") String password,HttpSession session, Model model) {
    	User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}
        String correctPassword = loggedInUser.getPassword(); 
        if (!password.equals(correctPassword)) {
            // 올바르지 않은 경우 다시 입력하도록 페이지로 이동
            return "redirect:/pwcheck?error";
        }
        session.setAttribute("pwcheckComplete", true);
        // 올바른 경우 비밀번호 변경 페이지로 이동
        return "redirect:/userUpdate/pwmodify";
    }

	@GetMapping("/userUpdate/pwmodify")
	public String passwordUpdate(HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}
		if(session.getAttribute("pwcheckComplete")==null) {
			return "redirect:/pwcheck";
		}
		return "Login/pwrs2";
	}
	
	@PostMapping("/userUpdate/pwmodify")
	public String passwordUpdate(@RequestParam("password1") String password1,
			@RequestParam("password2") String password2,
			 HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		if(session.getAttribute("pwcheckComplete")==null) {
			return "redirect:/pwcheck";
		}
		
		if(!password1.equals(password2)) { 
	        return "redirect:/userUpdate/pwmodify";
	    }
		loggedInUser.setPassword(password2);
		chanInfoService.updatePw(loggedInUser);
		
		return "redirect:/userUpdate";
	}
	
	

}
