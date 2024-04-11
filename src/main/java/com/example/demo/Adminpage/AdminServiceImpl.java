package com.example.demo.Adminpage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.auth.AuthRequest;
import com.example.demo.auth.AuthorizedUser;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminMapper adminMapper;
	
	@Override
	public boolean isUserAdmin(String userID) {
		AuthorizedUser aUser=adminMapper.adminCheck(userID);
				
		if(aUser!=null&& aUser.getAuthorizeID()==3) {
			return true;
		}
		return false;
	}

	@Override
	public List<AuthRequest> getAllAuthRequests() {
		return adminMapper.getAllAuthReq();
	}
	
	@Override
	public List<AuthRequest> getAuthRequestsStatus() {
		return adminMapper.getAuthReqStatus();
	}
	
	@Override
	public void approveRequest(Integer requestID) {
		AuthRequest authRequest = adminMapper.getAuthRequestByID(requestID);
        authRequest.setStatus("수락"); 
        adminMapper.updateAuthRequest(authRequest);	
        
        AuthorizedUser authorizedUser = new AuthorizedUser();
        authorizedUser.setAuthorizeID(authRequest.getAuthorizeID());
        authorizedUser.setUser_userID(authRequest.getUserID());
        authorizedUser.setFileID(authRequest.getFileID());
        adminMapper.insertAuthorizedUser(authorizedUser);
//        adminMapper.deleteAuthRequest(requestID);
	}

	@Override
	public void rejectRequest(Integer requestID) {
		AuthRequest authRequest=adminMapper.getAuthRequestByID(requestID);
		authRequest.setStatus("거절");
		adminMapper.updateAuthRequest(authRequest);
	}

	

}
