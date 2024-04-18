package com.example.demo.chinfo;

import com.example.demo.user.User;

public interface ChanInfoService {

	void updateName(User loguser);

	void updatePw(User loggedUser);

	int checkpw(String userID, String oldPw);
	
}
