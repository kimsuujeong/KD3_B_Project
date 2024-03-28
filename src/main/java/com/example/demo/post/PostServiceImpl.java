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

	// board delete
	public void delete(Integer postID) {
		postMapper.delete(postID);
	}

	public void updateBoard(Board board) {
		// TODO Auto-generated method stub
		postMapper.update(board);
	}
}
