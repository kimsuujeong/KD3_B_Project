package com.example.demo.myPage;

import java.util.List;

import com.example.demo.board.Board;

public interface MypageService {

	List<Board> getUserPosts(String userID);


}
