package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper usermapper; // test

	@GetMapping("/Testmain") // test home
	public String Testmain() {
		return "TestHtml/User/Testmain";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "TestHtml/User/joinForm";
	}

	@PostMapping("/joinForm") // test join
	public String joinForm(@ModelAttribute User userDTO) {
		userService.InsertUser(userDTO);
		return "TestHtml/User/joinForm";
	}

	@GetMapping("/login") // login
	public String loginForm() {
		return "TestHtml/User/login";
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
			return "TestHtml/User/login";
		}
		
	}
	
	@GetMapping("/FindID")
	public String FindID() {
		return "TestHtml/User/FindID";
	}

	@PostMapping("/FindID") // test FindID
	// user의 name도 받는 기능으로 업그레이드 예정.
	public String FindID (@RequestParam("userName") String userName, @RequestParam("email") String email) {
		User user = userService.FindID(userName,email);
		// 같은 이메일이 있으면 이메일 인증 토큰을 보내 id를 출력 할 예정.
		// test code
		System.out.println(usermapper.FindID(userName, email));
		return "TestHtml/User/FindID";
	}
	
	@GetMapping("/FindPW")
	public String FindPW() {
		return "TestHtml/User/FindPW";
	}
	
	@PostMapping("/FindPW") // test FindPW
	public String FindPW (@RequestParam("email") String email,@RequestParam("userID") String userID) {
		User user = userService.FindPW(email,userID);
		
		System.out.println(usermapper.FindPW(email, userID));
		
		return "TestHtml/User/FindPW";
	}
	
	

}
