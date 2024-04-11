package com.example.demo.auth;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class AuthMapper {

	@Autowired
	SqlSession session;

	public void saveAuthRequest(AuthRequest authRequest) {
		session.insert("authRequest", authRequest);
	}

	public AuthRequest getCurrentReq() {
		
		return session.selectOne("getCurrentReq");
	}

	public AuthRequest getAuthrequest(String userID, int i) {
		
		return session.selectOne("getAuthreqeust",Map.of("userID", userID,"authorizeID",i));
	}

	
	
}
