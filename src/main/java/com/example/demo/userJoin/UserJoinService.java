package com.example.demo.userJoin;

import com.example.demo.user.User;

public interface UserJoinService {
	
	public int emailchk(String email);
	public void InsertUser (User user); // Join(insert)
	public int idchk(String userID);
	public int namechk(String userName);
}
