package com.example.demo.userJoin;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.user.User;

@Repository
public class UserJoinMapper {

	@Autowired
	SqlSession sqlSession;
	

	public int emailchk(String email) {
		return sqlSession.selectOne("emailchk",email);
	}
	
	public void InsertUser(User userDTO) {
		sqlSession.insert("InsertUser", userDTO);
	}
	
	public int idchk(String userID) {
		return sqlSession.selectOne("idchk",userID);
	}


	public int namechk(String userName) {
		return sqlSession.selectOne("namechk",userName);
	}

}
