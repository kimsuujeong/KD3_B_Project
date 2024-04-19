package com.example.demo.myPage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Adminpage.AdminService;
import com.example.demo.board.Board;
import com.example.demo.board.BoardService;
import com.example.demo.file.FileService;
import com.example.demo.post.ImageFile;
import com.example.demo.user.User;
import com.example.demo.user.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController { // 관심목록, 인증, 개인정보 수정(비밀번호 수정), 로그아웃 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	MypageService mypageService;
	@Autowired
	BoardService boardService;
	@Autowired
	FileService fileService ;

	@RequestMapping("/userUpdate") // 개인정보 수정
	public String userUpdate(Model model, HttpSession httpSession) {
		User loggedInUser = (User) httpSession.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/login";
		}
	    // 사용자가 관리자인지 여부를 확인합니다.
	    boolean isAdmin = false;
	    if (loggedInUser != null) {
	        isAdmin = adminService.isUserAdmin(loggedInUser.getUserID());
	    }
	    model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("loggedInUser", loggedInUser);
		return "MyPage/mypage2";
	}
	
	@RequestMapping("/mypost") // 내 글 목록
	public String userPost(Model model, HttpSession httpSession) {
		User loggedInUser = (User) httpSession.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		// 사용자가 관리자인지 여부를 확인합니다.
		boolean isAdmin = false;
		if (loggedInUser != null) {
			isAdmin = adminService.isUserAdmin(loggedInUser.getUserID());
		}
		// 유저별 게시물 가져오기
		List<Board> userPosts = mypageService.getUserPosts(loggedInUser.getUserID());
		List<String> imageLinks = new ArrayList<>();
	    for (Board post : userPosts) {
	    	if (post.getFileID() != null) {
	    		// 이미지 파일 주소 가져오기
	            ImageFile file = boardService.getImageFile(post.getFileID());
	            String fileLink = fileService.getDownLink(file.getSaveImName());
	            imageLinks.add(fileLink);
	        } else {
	            // 이미지가 없는 경우 빈 문자열을 추가합니다.
	            imageLinks.add("");
	        }
	    }
		model.addAttribute("userPosts", userPosts);
		model.addAttribute("imageLinks", imageLinks);
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("loggedInUser", loggedInUser);
		return "MyPage/mypage4";
	}
	
	@RequestMapping("/logout") // 로그아웃 세션 만료
	public String logout(HttpSession httpSession) {
		httpSession.invalidate();
		return "redirect:/";
	}
	
	// 회원탈퇴
	@GetMapping("/bye")
	public String byePage(HttpSession httpSession,Model model) {
		User loggedInUser = (User)httpSession.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		return "MyPage/bye"; // 비밀번호 확인을 위한 페이지로 이동
	}
	
	// 회원탈퇴 세션 만료
	@PostMapping("/bye") 
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
