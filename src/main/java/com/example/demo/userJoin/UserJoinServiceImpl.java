package com.example.demo.userJoin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.User;

@Service
public class UserJoinServiceImpl implements UserJoinService{

	@Autowired
	UserJoinMapper userMapper;
	
	@Override
	public int emailchk(String email) {
		return userMapper.emailchk(email);
	}
	
	@Override
	public void InsertUser(User userDTO) {
		userMapper.InsertUser(userDTO);
	}

	@Override
	public int idchk(String userID) {
		return userMapper.idchk(userID);
	}

	@Override
	public int namechk(String userName) {
		return userMapper.namechk(userName);
	}
}
