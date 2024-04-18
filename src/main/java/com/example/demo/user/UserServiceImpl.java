package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	// 로그인
	@Override
	public User Login(String userID, String password) {
		return userMapper.Login(userID, password);
	}
	// 아이디 찾기
	@Override
	public User FindID(String email) {
		return userMapper.FindID(email);
	}
	// 비밀번호 찾기
	@Override
	public User FindPW(String usermail, String userID) {
		return userMapper.FindPW(usermail, userID);
	}

	@Override
	public int Update(String pwNew, String pwEmail) {
		// TODO Auto-generated method stub
		return userMapper.Update(pwNew, pwEmail);
	}

	// 회원 탈퇴
	@Override
	public void deleteUser(String userID) {
		// user,file,image,like,authrequest,authorizedUser,board의 정보 삭제
		userMapper.deletePostUser(userID);
		userMapper.deleteFileUser(userID);
		userMapper.deleteImageUser(userID);
		userMapper.deleteLikeUser(userID);
		userMapper.deleteAuthReqUser(userID);
		userMapper.deleteAuthedUser(userID);
		userMapper.deleteUser(userID);
	}



}
