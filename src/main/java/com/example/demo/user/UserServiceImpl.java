package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	
	@Override
	public void InsertUser(User userDTO) {
		userMapper.InsertUser(userDTO);
	}

	@Override
	public User Login(String userID, String password) {
		return userMapper.Login(userID, password);
	}

	@Override
	public User FindID(String userName, String email) {
		return userMapper.FindID(userName, email);
	}

	@Override
	public User FindPW(String usermail, String userID) {
		return userMapper.FindPW(usermail, userID);
	}



}
