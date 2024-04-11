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
import com.example.demo.user.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
    FileService fileService;
	
	@GetMapping("/admin/auth")
	public String adminpage(HttpSession httpSession,Model model) {
		User loggedInUser = (User) httpSession.getAttribute("loggedInUser");
		
		if (loggedInUser == null || !adminService.isUserAdmin(loggedInUser.getUserID())) {
            return "redirect:/login";
        }
		List<AuthRequest> authRequests = adminService.getAuthRequestsStatus();
		List<String> fileLinks= new ArrayList<>();
		for(AuthRequest request: authRequests) {
			String fileLink=fileService.getLinkByFileID(request.getFileID());
			fileLinks.add(fileLink);
		}
	    model.addAttribute("authRequests", authRequests);
	    model.addAttribute("fileLinks", fileLinks);
        return "AdminPage/UserAuthor";
	}
	
	@PostMapping("/admin/authorizeRequest/{requestID}/approve")
    public String approveRequest(@PathVariable("requestID") Integer requestID) {

		adminService.approveRequest(requestID);
        return "redirect:/admin/auth";
    }

    @PostMapping("/admin/authorizeRequest/{requestID}/reject")
    public String rejectRequest(@PathVariable("requestID") Integer requestID) {

    	adminService.rejectRequest(requestID);
        return "redirect:/admin/auth";
    }
}
