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
	

	public void InsertUser(User userDTO) {
		sqlSession.insert("InsertUser", userDTO);
	}


//	public void InsertUser(String usermail) {
//		sqlSession.selectOne("InsertUser", usermail);
//		// FindID (for e-mail)
//	}


	public User Login(String userID, String password) {
		Map<String, String> loginInfo = new HashMap<>();
	    loginInfo.put("userID", userID);
	    loginInfo.put("password", password);
		return sqlSession.selectOne("Login", loginInfo);
	}

}
