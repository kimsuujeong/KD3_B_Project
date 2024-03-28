package com.example.demo.userJoin;

import com.example.demo.user.User;

public interface UserJoinService {
	
	public void InsertUser (User user); // Join(insert)
	public int idchk(String userID);
	public int emailchk(String email);
	public int namechk(String userName);
}
