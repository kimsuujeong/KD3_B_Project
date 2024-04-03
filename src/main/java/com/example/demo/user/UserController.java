package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.mapping.Embedded.Empty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.mail.MailController;
import com.example.demo.mail.MailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
public class UserController { // 로그인, 아이디&비밀번호 찾기

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper usermapper; // test
	
	@Autowired
	private MailController mailController;
	

	@GetMapping("/Testmain") // test home
	public String Testmain() {
		return "TestHtml/user/Testmain";
	}

	// Login
	@GetMapping("/login") // login
	public String loginForm() {
		return "TestHtml/user/login";
	}

	@PostMapping("/login")
	public String loginForm(@RequestParam("userID") String userID, @RequestParam("password") String password,
			Model model) {
		User user = userService.Login(userID, password);

		// 예외처리로 변경하기
		if (userID.isEmpty()) {
			System.out.println("아이디를 입력해 주세요");
			return "login";
		}

		if (password.isEmpty()) {
			System.out.println("비밀번호를 입력해 주세요");
			return "login";
		}

		if (user != null) {
			// 로그인 성공
			model.addAttribute("loggedInUser", user); // 세션에 사용자 정보 저장
			System.out.println("성공입니다");
			return "login";
		} else {
			// 로그인 실패
			System.out.println("실패입니다");
			return "TestHtml/user/login";
		}

	}

	// FindID
	@GetMapping("/FindID")
	public String FindID() {
		return "TestHtml/user/FindID";
	}

	@PostMapping("/FindID") // test FindID
	// 아이디 찾기도 이메일로만 보내서 토큰받는걸로 바꾸기
	public String FindID(@RequestParam("email") String email) {
		userService.FindID(email);
		
		
		System.out.println(usermapper.FindID(email).getUserID());
		System.out.println(usermapper.FindID(email).getEmail());
		
		if (usermapper.FindID(email).getEmail() != null) {
//			System.out.println("있는 이메일 입니다.");
//			mailController.mailSend(email);
			
			
		}
		
		
		return "TestHtml/user/FindID";
	}

	// FindPassword
	@GetMapping("/FindPW")
	public String FindPW() {
		return "TestHtml/user/FindPW";
	}

	@PostMapping("/FindPW") // test FindPW 아이디랑 이메일로 보내서 토큰받는걸로 바꾸기
	public String FindPW(@RequestParam("email") String email, @RequestParam("userID") String userID) {
		User user = userService.FindPW(email, userID);

		System.out.println(usermapper.FindPW(email, userID));

		return "TestHtml/user/FindPW";
	}

}
