package com.example.demo.myPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MypageController { // 관심목록, 인증, 개인정보 수정(비밀번호 수정), 로그아웃 
	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private UserMapper usermapper; // test
	
	@RequestMapping("/mypage") // test home
	public String My_page() {
		return "TestHtml/Mypage/TestMy_page";
	}
	
	@RequestMapping("/savelist") // 관심목록
	public String savelist() {
		return "TestHtml/Mypage/savelist";
	}
	
	@RequestMapping("/authn") // 인증
	public String authn() {
		return "TestHtml/Mypage/authn";
	}
	
	@RequestMapping("/userUpdate") // 개인정보 수정
	public String userUpdate() {
		return "TestHtml/Mypage/userUpdate";
	}
	
	@RequestMapping("/logout") // 로그아웃
	public String logout() {
		return "TestHtml/Mypage/logout";
	}

}
