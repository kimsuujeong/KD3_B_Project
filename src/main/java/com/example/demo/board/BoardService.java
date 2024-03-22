package com.example.demo.board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

	public List<Board> getAllPosts();
	
	public List<Board> getPostsByCategoryId(Integer categoryId);
	
	public Board getPostById(Integer postID);
	
	public void visitCnt(Integer postID);
	
	public int countAll();
	
	public Page<Board> getList(Pageable page);
	
//	public List<Board> getBoardList(Search search);
	
	
}
