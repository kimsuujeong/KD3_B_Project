package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.file.FileService;
import com.example.demo.user.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	@Autowired
	AuthService authService;
	
	@Autowired
	FileService fileService;
	
	@GetMapping("/authn") // 인증
	public String authn(Model model, HttpSession httpSession) {
		User loggedInUser = (User)httpSession.getAttribute("loggedInUser");
		model.addAttribute("loggedInUser", loggedInUser);
		
		return "/MyPage/mypage3";
	}
	
	@GetMapping("/authn/request")
	public String authorizeRequest(HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("loggedInUser", loggedInUser);
		
		
	    return "/MyPage/mypage3"; 

	}

	@GetMapping("/authn/request/company")
	public String authRequestCompany(HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("entercer", new AuthRequest());
		return "/MyPage/entercer";
	}
	@GetMapping("/authn/request/artist")
	public String authRequestArtist(HttpSession session, Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("artistcer", new AuthRequest());
		return "/MyPage/artistcer";
	}
	
	@PostMapping("authn/request/company")
	public String authRequestCompany(@RequestParam("file") MultipartFile file,
										@ModelAttribute("entercer")AuthRequest request,
										HttpSession session,Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		AuthRequest existingRequest = authService.getAuthRequestByUserIDAndType(loggedInUser.getUserID(), 1);
	    if (existingRequest != null && ("확인중".equals(existingRequest.getStatus()) || "수락".equals(existingRequest.getStatus()))) {
	        return "redirect:/authn";
	    }
		request.setFile(file);
		request.setUserID(loggedInUser.getUserID());
		authService.authRequest(request, request.getName(), request.getLink(), 1);
		session.setAttribute("entercer", request);
		return "redirect:/authn";
	}
	@PostMapping("/authn/request/artist")
	public String authRequestArtist(@RequestParam("file") MultipartFile file,
										@ModelAttribute("artistcer")AuthRequest request,
										HttpSession session,Model model) {
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		AuthRequest existingRequest = authService.getAuthRequestByUserIDAndType(loggedInUser.getUserID(), 2);
	    if (existingRequest != null && ("확인중".equals(existingRequest.getStatus()) || "수락".equals(existingRequest.getStatus()))) {
	        return "redirect:/authn"; 
	    }
		request.setFile(file);
		request.setUserID(loggedInUser.getUserID());
		authService.authRequest(request, request.getName(), request.getLink(), 2);
		session.setAttribute("artistcer", request);
		return "redirect:/authn";
	}

}
