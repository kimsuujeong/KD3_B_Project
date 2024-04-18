package com.example.demo.myPage;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.board.Board;

@Repository
public class MypageMapper {

	@Autowired
	SqlSession session;

	// 유저 아이디별 게시물 리스트
	public List<Board> getUserPosts(String userID) {
		return session.selectList("getUserPosts", userID);
	}
	
}
