package com.example.demo.Adminpage;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.auth.AuthRequest;
import com.example.demo.auth.AuthorizedUser;

@Repository
public class AdminMapper {

	@Autowired
	SqlSession session;

	public AuthorizedUser adminCheck(String userID) {
		return session.selectOne("admincheck", userID);
	}

	public List<AuthRequest> getAllAuthReq() {
		return session.selectList("getAllAuthReq");
	}

	public List<AuthRequest> getAuthReqStatus() {
		return session.selectList("getAuthReqStatus");
	}
	
	public AuthRequest getAuthRequestByID(Integer requestID) {
		return session.selectOne("getAuthReqID", requestID);
	}

	public void updateAuthRequest(AuthRequest authRequest) {
		session.update("updateStatus", authRequest);
	}

	public void insertAuthorizedUser(AuthorizedUser authorizedUser) {
		session.insert("insertAuthUser", authorizedUser);
	}

	public void deleteAuthRequest(Integer requestID) {
		session.delete("deleteAuthRequest", requestID);
	}

	
	
}
