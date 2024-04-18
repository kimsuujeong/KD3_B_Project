package com.example.demo.Adminpage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.auth.AuthRequest;
import com.example.demo.file.FileService;
import com.example.demo.file.UploadFile;
import com.example.demo.mail.MailController;
import com.example.demo.user.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
    FileService fileService;
	
	@Autowired
	AdminMapper adminMapper;
	
	@Autowired
	MailController mailController;
	
	// 관리자의 관계자 인증 신청 리스트
	@GetMapping("/admin/auth")
	public String adminpage(HttpSession httpSession,Model model) {
		User loggedInUser = (User) httpSession.getAttribute("loggedInUser");
		// 로그인을 안 했거나 관리자가 아니면 로그인으로 감
		boolean isAdmin = false;
		
		if (loggedInUser == null || !adminService.isUserAdmin(loggedInUser.getUserID())) {
            
			return "redirect:/login";
        }
		
		// 신청목록중 status상태가 확인중인것만 리스트로 보임
		List<AuthRequest> authRequests = adminService.getAuthRequestsStatus();
		// 가져온 파일 링크 목록
		List<String> fileLinks= new ArrayList<>();
		for(AuthRequest request: authRequests) {
			// 파일아이디로 파일 정보 가져오기
			UploadFile file = fileService.getFileByID(request.getFileID());
            // 파일 정보로 주소 가져오기
            String fileLink = fileService.getDownLink(file.getSaveName());
			fileLinks.add(fileLink);
		}
		model.addAttribute("isAdmin", isAdmin);
	    model.addAttribute("authRequests", authRequests);
	    model.addAttribute("fileLinks", fileLinks);
        return "AdminPage/UserAuthor";
	}
	
	// 관계자 신청 수락
	@PostMapping("/admin/authorizeRequest/{requestID}/approve")
    public String approveRequest(@PathVariable("requestID") Integer requestID) {
		// 관계 신청 수락
		adminService.approveRequest(requestID);
		// 요청한 유저의 이메일 가져오기
		String email = adminMapper.getAuthrequestEmail(requestID);
		
		// 이메일로 수락 메시지 보내기
		mailController.approvalMailSend(email);
		
        return "redirect:/admin/auth";
    }

	// 관계자 신청 거부
    @PostMapping("/admin/authorizeRequest/{requestID}/reject")
    public String rejectRequest(@PathVariable("requestID") Integer requestID) {
    	// 관계 신청 거절 
    	adminService.rejectRequest(requestID);
    	
    	String email = adminMapper.getAuthrequestEmail(requestID);
    	
    	// 이메일로 거절 메시지 보내기
    	mailController.refusalMailSend(email);
    	
        return "redirect:/admin/auth";
    }
}
