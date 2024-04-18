package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Adminpage.AdminService;
import com.example.demo.board.BoardService;
import com.example.demo.file.FileService;
import com.example.demo.user.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	@Autowired
	AuthService authService;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	BoardService boardService;
	
	@GetMapping("/authn") // 인증
	public String authn(Model model, HttpSession httpSession) {
		User loggedInUser = (User)httpSession.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/login";
		}
	    
	    // 사용자가 관리자인지 여부를 확인합니다.
		boolean isAdmin = false;
	    
	    isAdmin = adminService.isUserAdmin(loggedInUser.getUserID());
	    
		model.addAttribute("loggedInUser", loggedInUser);
		model.addAttribute("isAdmin", isAdmin);
		return "/MyPage/mypage3";
	}
	
	// 관계자 인증 신청 페이지
	@GetMapping("/authn/request")
	public String authorizeRequest(HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}
		boolean isAdmin = false;
	    
	    isAdmin = adminService.isUserAdmin(loggedInUser.getUserID());
	    
		model.addAttribute("loggedInUser", loggedInUser);
		model.addAttribute("isAdmin", isAdmin);
		
	    return "/MyPage/mypage3"; 

	}

	// 기업 인증 작성폼
	@GetMapping("/authn/request/company")
	public String authRequestCompany(HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}
		boolean isAdmin = false;
	    
	    isAdmin = adminService.isUserAdmin(loggedInUser.getUserID());
	    
		// 기존 기업 인증 신청내역 확인
		AuthRequest existingRequest = authService.getAuthRequestUserIDType(loggedInUser.getUserID(), 1);
		
		if (existingRequest != null) {// 이미 있다면 작성폼으로 가지않음
	        if ("확인중".equals(existingRequest.getStatus())) {
	            model.addAttribute("message1", "이미 기업 인증 요청이 진행 중입니다.");
	            return "/MyPage/mypage3";
	        } else if ("수락".equals(existingRequest.getStatus())) {
	            model.addAttribute("message1", "기업 인증이 승인되었습니다.");
	            return "/MyPage/mypage3";
	        }
//	        return "redirect:/authn";
	    }
		model.addAttribute("isAdmin", isAdmin);
		
		model.addAttribute("entercer", new AuthRequest());
		return "/MyPage/entercer";
	}
	@GetMapping("/authn/request/artist")
	public String authRequestArtist(HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		boolean isAdmin = false;
	    
	    isAdmin = adminService.isUserAdmin(loggedInUser.getUserID());
	    
		// 기존 예술 인증 신청내역 확인
		AuthRequest existingRequest = authService.getAuthRequestUserIDType(loggedInUser.getUserID(), 2);
		if (existingRequest != null) {// 이미 있다면 작성폼으로 가지 않음
	        if ("확인중".equals(existingRequest.getStatus())) {
	            model.addAttribute("message2", "이미 예술 인증 요청이 진행 중입니다.");
	            return "/MyPage/mypage3";
	        } else if ("수락".equals(existingRequest.getStatus())) {
	            model.addAttribute("message2", "예술 인증이 승인되었습니다.");
	            return "/MyPage/mypage3";
	        }
//	        return "redirect:/authn";
	    }
		model.addAttribute("isAdmin", isAdmin);
		
		model.addAttribute("artistcer", new AuthRequest());
		return "/MyPage/artistcer";
	}
	// 기업인 권한 신청 작성 폼
	@PostMapping("authn/request/company")
	public String authRequestCompany(@RequestParam("file") MultipartFile file,
										@ModelAttribute("entercer")AuthRequest request,
										HttpSession session,Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		// 파일 업로드하고 보냄
		request.setFile(file);
		request.setUserID(loggedInUser.getUserID());
		// 데이터베이스에 저장
		authService.authRequest(request, request.getName(), request.getLink(), 1);
		session.setAttribute("entercer", request);
		return "redirect:/authn";
	}
	// 예술인 권한 신청 작성 폼
	@PostMapping("/authn/request/artist")
	public String authRequestArtist(@RequestParam("file") MultipartFile file,
										@ModelAttribute("artistcer")AuthRequest request,
										HttpSession session,Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		request.setFile(file);
		request.setUserID(loggedInUser.getUserID());
		authService.authRequest(request, request.getName(), request.getLink(), 2);
		session.setAttribute("artistcer", request);
		return "redirect:/authn";
	}

}
