package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
    private UserMapper userMapper;
	
	public String getUserNameById(String userId) {
        return userMapper.findUserNameById(userId);
    }

	public User getUserById(User user) {
		return userMapper.findById(user);
	}
}
