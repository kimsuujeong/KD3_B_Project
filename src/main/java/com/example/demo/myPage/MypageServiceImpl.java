package com.example.demo.myPage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.board.Board;

@Service
public class MypageServiceImpl implements MypageService{

	@Autowired
	MypageMapper mapper;

	// 유저 아이디별 게시물 리스트
	@Override
	public List<Board> getUserPosts(String userID) {
		return mapper.getUserPosts(userID);
	}
	

}
