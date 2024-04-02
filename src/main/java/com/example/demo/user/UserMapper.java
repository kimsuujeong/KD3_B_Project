package com.example.demo.user;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserMapper {
	
	@Autowired
	SqlSession sqlSession;


	public User Login(String userID, String password) {
		Map<String, String> loginInfo = new HashMap<>();
	    loginInfo.put("userID", userID);
	    loginInfo.put("password", password);
		return sqlSession.selectOne("Login", loginInfo);
	}



	public User FindID(String email) {
		 return sqlSession.selectOne("FindID",email);
	}


	public User FindPW(String email, String userID) {
		Map<String, String> FindPWInfo = new HashMap<>();
		FindPWInfo.put("email", email);
		FindPWInfo.put("userID", userID);
		 return sqlSession.selectOne("FindPW",FindPWInfo);
	}

}
