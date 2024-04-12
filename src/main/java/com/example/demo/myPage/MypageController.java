package com.example.demo.myPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.user.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController { // 관심목록, 인증, 개인정보 수정(비밀번호 수정), 로그아웃 
	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private UserMapper usermapper; // test
	
	@RequestMapping("/mypage/userupdate") // 내 정보 수정
	public String My_page2() {
		return "/Mypage/mypage2";
	}
	@GetMapping("/mypage/userupdate/namemodify") //닉네임 수정
	public String MY_page2namemodify() {
		return "/Login/nrs";
	}
	
	@GetMapping("/mypage/userupdate/emmodify") //이메일 수정 
	public String MY_page2emmodify() {
		return "/Login/ers";	
	}	
	@GetMapping("/mypage/userupdate/emmodify/cere") //이메일 재설정
	public String MY_page2emmodifycere() {
		return "/Login/cerE";	
	}	
	@GetMapping("/mypage/userupdate/pwmodify") //비밀번호 수정 (1.비밀번호 재확인)
	public String MY_page2pwmodify() {
		return "/Login/pwcheck";
	}
	
	@RequestMapping("/mypage/savelist") // 관심 목록
	public String My_page1() {
		return "/Mypage/mypage1";
	}
	@RequestMapping("/mypage/authn") // 관계자 인증
	public String My_page3() {
		return "/Mypage/mypage3";
	}
	@RequestMapping("/mypage/mypage4") // 내 글 확인
	public String My_page4() {
		return "/Mypage/mypage4";
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
	public String userUpdate(Model model, HttpSession httpSession) {
		User loggedInUser = (User)httpSession.getAttribute("loggedInUser");
		model.addAttribute("loggedInUser", loggedInUser);
		return "TestHtml/Mypage/userUpdate";
	}
	
	@RequestMapping("/logout") // 로그아웃
	public String logout() {
		return "TestHtml/Mypage/logout";
	}
	
	@RequestMapping("/mypage/qa") // q&a
	public String My_pageqa() {
		return "/QAPage/qa";
	}

	@RequestMapping("/mypage/notice") // 공지사항
	public String My_pagenotice() {
		return "/QAPage/notice";
	}
	@RequestMapping("/mypage/serviceintroduce") // 서비스 소개
	public String My_pageserviceintroduce() {
		return "/ServiceIntroducePage/ServiceIntroducePage";
	}
}
