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

	// 관리자 확인
	public List<AuthorizedUser> adminCheck(String userID) {
		return session.selectList("admincheck", userID);
	}

	// 모든 신청리스트 -- 이제 안씀
	public List<AuthRequest> getAllAuthReq() {
		return session.selectList("getAllAuthReq");
	}

	// 신청리스트를 status에 따라 걸러서 가져옴
	public List<AuthRequest> getAuthReqStatus() {
		return session.selectList("getAuthReqStatus");
	}
	
	// 신청아이디 가져오기
	public AuthRequest getAuthRequestByID(Integer requestID) {
		return session.selectOne("getAuthReqID", requestID);
	}

	// 신청테이블 변경
	public void updateAuthRequest(AuthRequest authRequest) {
		session.update("updateStatus", authRequest);
	}

	// 인증된유저로 값 넣기
	public void insertAuthorizedUser(AuthorizedUser authorizedUser) {
		session.insert("insertAuthUser", authorizedUser);
	}

	// 신청 리스트 삭제 -- 이제 안씀 status만 변경하고 놔두기로해서
	public void deleteAuthRequest(Integer requestID) {
		session.delete("deleteAuthRequest", requestID);
	}

	public String getAuthrequestEmail(Integer requestID) {
		return session.selectOne("getAuthrequestEmail",requestID);
	}

	
	
}
