package com.example.demo.chinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.User;

@Service
public class ChanInfoServiceImpi implements ChanInfoService{
	
	@Autowired
	ChanInfoMapper chanInMapper;
	
	@Override
	public void updateName(User userid) {
		chanInMapper.updateName(userid);
	}

	@Override
	public void updateEmail(User userid) {
		chanInMapper.updateEmail(userid);
	}

	@Override
	public void updatePw(User user) {
		chanInMapper.updatePw(user);
	}

	@Override
	public int checkpw(String userID, String oldPw) {
		return chanInMapper.checkpw(userID,oldPw);
	}

}
