package com.example.demo.chinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.User;

@Service
public class ChanInfoServiceImpi implements ChanInfoService{
	
	@Autowired
	ChanInfoMapper chanInMapper;
	
	// 닉네임 변경
	@Override
	public void updateName(User userid) {
		chanInMapper.updateName(userid);
	}

	// 비밀번호 변경
	@Override
	public void updatePw(User user) {
		chanInMapper.updatePw(user);
	}

//	// 비밀번호 확인
//	@Override
//	public int checkpw(String userID, String oldPw) {
//		return chanInMapper.checkpw(userID,oldPw);
//	}

}
