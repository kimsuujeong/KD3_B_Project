package com.example.demo.auth;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthMapper {

	@Autowired
	SqlSession session;

	// 신청 정보 저장
	public void saveAuthRequest(AuthRequest authRequest) {
		session.insert("authRequest", authRequest);
	}

	// 신청 내역 확인
	public AuthRequest getAuthrequest(String userID, int i) {

		return session.selectOne("getAuthreqeust", Map.of("userID", userID, "authorizeID", i));
	}

}
