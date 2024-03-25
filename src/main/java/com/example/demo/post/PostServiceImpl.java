package com.example.demo.post;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.board.Board;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostMapper postMapper;

	public List<Board> getBoardList() {
		List<Board> boardList = Collections.emptyList();

		int boardTotalCount = postMapper.selectBoardTotalCount();

		if (boardTotalCount > 0) {
			boardList = postMapper.selectBoardList();
		}

		return boardList;
	}

	public void saveBoard(Board board) {
		// TODO Auto-generated method stub
		postMapper.insert(board);
	}

	// board detail
	public Board getBoardDetail(Integer postID) {
		// TODO Auto-generated method stub
		return postMapper.selectBoardDetail(postID);
	}

	// board register
//	public boolean registerBoard(Board params) {
//		int queryResult = 0;
//
//		if (params.getPostID() == null) {
//			queryResult = postMapper.insertBoard(params);
//		} else {
//			queryResult = postMapper.updateBoard(params);
//		}
//
//		return (queryResult == 1) ? true : false;
//	}
}
