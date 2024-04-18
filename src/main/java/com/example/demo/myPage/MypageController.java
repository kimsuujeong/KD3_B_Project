package com.example.demo.myPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Adminpage.AdminService;
import com.example.demo.user.User;
import com.example.demo.user.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController { // 관심목록, 인증, 개인정보 수정(비밀번호 수정), 로그아웃 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	AdminService adminService;
	
//	@RequestMapping("/mypage/userupdate") // 내 정보 수정
//	public String My_page2() {
//		return "/Mypage/mypage2";
//	}
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
	
//	중복된 savelist 삭제 -20240417 허서진
	
//	@RequestMapping("/authn") // 인증
//	public String authn() {
//		return "TestHtml/Mypage/authn";
//	}
	
	@RequestMapping("/userUpdate") // 개인정보 수정
	public String userUpdate(Model model, HttpSession httpSession) {
		User loggedInUser = (User) httpSession.getAttribute("loggedInUser");
	    
	    // 사용자가 관리자인지 여부를 확인합니다.
	    boolean isAdmin = false;
	    if (loggedInUser != null) {
	        isAdmin = adminService.isUserAdmin(loggedInUser.getUserID());
	    }
	    model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("loggedInUser", loggedInUser);
		return "MyPage/mypage2";
	}
	
	@RequestMapping("/logout") // 로그아웃 세션 만료
	public String logout(HttpSession httpSession) {
		httpSession.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/bye")
	public String byePage(HttpSession httpSession,Model model) {
		User loggedInUser = (User)httpSession.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		return "MyPage/bye"; // 비밀번호 확인을 위한 페이지로 이동
	}
	@PostMapping("/bye") // 회원탈퇴 세션 만료
	public String bye(@RequestParam("password1") String password,HttpSession httpSession) {
		User loggedInUser = (User) httpSession.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		String userID = loggedInUser.getUserID();
		// 비밀번호가 맞을 시 회원정보 삭제
		if(password.equals(loggedInUser.getPassword())) {
			userService.deleteUser(userID);
			httpSession.invalidate();
			return "redirect:/";
		}
		else {
			return "redirect/bye";
		}
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
