package com.example.demo.board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.post.ImageFile;

public interface BoardService {

	public List<Board> getAllPosts();
	
    Page<Board> getPostsByCategoryId(Integer categoryId, Pageable pageable);
	
	public Board getPostById(Integer postID);
	
	public void visitCnt(Integer postID);
	
	public int countAll(Integer categoryId);
	
	public Page<Board> getList(Pageable page, Integer categoryID);
	
	public Page<Board> searchCtg(Integer categoryID, Search search, String order, Pageable pageable);

	public ImageFile getImageFile(Integer fileID);

	public Board getPostByLikeID(Integer postID);
	
	
}
