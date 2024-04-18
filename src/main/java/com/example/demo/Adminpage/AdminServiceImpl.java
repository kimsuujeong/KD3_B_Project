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
	
	// 관리자인지 확인
	@Override
	public boolean isUserAdmin(String userID) {
		List<AuthorizedUser> aUser=adminMapper.adminCheck(userID);
		// 관리자 권한 확인(authorizeID가 3이어야함
		for (AuthorizedUser userlist : aUser) {
			if(userlist!=null&& userlist.getAuthorizeID()==3) {
				return true;
			}
		}
		return false;
	}
	// 관계자인지 확인
	@Override
	public boolean isUserAuth(String userID) {
		List<AuthorizedUser> aUser=adminMapper.adminCheck(userID);
		// 관계자는 1이나2여야 함 관리자는 1로 관계자로 봄
		for (AuthorizedUser userlist : aUser) {
			if(userlist!=null&& (userlist.getAuthorizeID()==1||userlist.getAuthorizeID()==2 || userlist.getAuthorizeID()==3)) {
				return true;
			}
		}
		return false;
	}
	
	// 모든 신청 리스트 -- 이제 안씀
	@Override
	public List<AuthRequest> getAllAuthRequests() {
		return adminMapper.getAllAuthReq();
	}
	
	// 신청리스트에 status가 확인중인 것만 가져옴
	@Override
	public List<AuthRequest> getAuthRequestsStatus() {
		return adminMapper.getAuthReqStatus();
	}
	
	// 권한 수락
	@Override
	public void approveRequest(Integer requestID) {
		// 해당 신청의 아이디 가져옴
		AuthRequest authRequest = adminMapper.getAuthRequestByID(requestID);
		// status를 수락으로 변경하고 업데이트
        authRequest.setStatus("수락"); 
        adminMapper.updateAuthRequest(authRequest);	
        
        // 인증된 유저테이블에 넣기
        AuthorizedUser authorizedUser = new AuthorizedUser();
        authorizedUser.setAuthorizeID(authRequest.getAuthorizeID());
        authorizedUser.setUser_userID(authRequest.getUserID());
        authorizedUser.setFileID(authRequest.getFileID());
        adminMapper.insertAuthorizedUser(authorizedUser);
//        adminMapper.deleteAuthRequest(requestID);
	}

	// 권한 거부
	@Override
	public void rejectRequest(Integer requestID) {
		AuthRequest authRequest=adminMapper.getAuthRequestByID(requestID);
		// status를 거절로 변경
		authRequest.setStatus("거절");
		adminMapper.updateAuthRequest(authRequest);
	}
	// 신청아이디로 유저의 이메일 가져오기
	@Override
	public void getAuthrequestEmail(Integer requestID) {
		adminMapper.getAuthrequestEmail(requestID);
	}
	

	

}
