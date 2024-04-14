package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	

	@Override
	public User Login(String userID) {
		return userMapper.Login(userID);
	}

	@Override
	public User FindID(String email) {
		return userMapper.FindID(email);
	}

	@Override
	public User FindPW(String usermail, String userID) {
		return userMapper.FindPW(usermail, userID);
	}

	@Override
	public int Update(String pwNew, String pwEmail) {
		// TODO Auto-generated method stub
		return userMapper.Update(pwNew, pwEmail);
	}



}
