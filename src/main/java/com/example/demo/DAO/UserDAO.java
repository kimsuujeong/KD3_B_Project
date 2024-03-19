package com.example.demo.DAO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.demo.DTO.UserDTO;

import jakarta.inject.Inject;

@Repository
public class UserDAO {
	
	@Inject
	SqlSession sqlSession;
	

	public void InsertUser(UserDTO userDTO) {
		sqlSession.insert("InsertUser", userDTO);
//		sqlSession.insert("userDTO.InsertUser", userDTO);
	}
	
	
	
}
