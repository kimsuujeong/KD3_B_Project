package com.example.demo.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
    BoardMapper boardMapper;

	@Override
	public List<Board> getAllPosts() {
		return boardMapper.findAll();
	}

	@Override
	public List<Board> getPostsByCategoryId(Integer categoryId) {
		return boardMapper.findByCategoryId(categoryId);
	}

	@Override
	public Board getPostById(Integer postID) {
		return boardMapper.findPostById(postID);
	}

	@Override
	public void visitCnt(Integer postID) {
		boardMapper.visitCnt(postID);
	}

	@Override
	public int countAll() {
		return boardMapper.countAll();
	}

	@Override
	public Page<Board> getList(Pageable page) {
		return boardMapper.getList(page);
	}
		
}
