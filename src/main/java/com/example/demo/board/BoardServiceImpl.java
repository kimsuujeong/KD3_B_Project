package com.example.demo.board;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.post.ImageFile;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper boardMapper;
	
	// 모든 게시물 가져오기
	@Override
	public List<Board> getAllPosts() {
		return boardMapper.findAll();
	}

	// 카테고리 아이디별 게시물 가져오기
	@Override
	public Page<Board> getPostsByCategoryId(Integer categoryId, Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		RowBounds rowBounds = new RowBounds(offset, pageable.getPageSize());
		List<Board> posts = boardMapper.findByCategoryId(categoryId, rowBounds);
	
		int total = boardMapper.countAll(categoryId);
		return new PageImpl<>(posts, pageable, total);
	}

	// 게시물 아이디로 게시물 정보 가져오기
	@Override
	public Board getPostById(Integer postID) {
		return boardMapper.getPostById(postID);
	}

	// 조회수
	@Override
	public void visitCnt(Integer postID) {
		boardMapper.visitCnt(postID);
	}

	// 카테고리별 게시물 수
	@Override
	public int countAll(Integer categoryId) {
		return boardMapper.countAll(categoryId);
	}
	
	// 목록 가져오기
	@Override
	public Page<Board> getList(Pageable page, Integer categoryID) {
		return boardMapper.getList(page, categoryID);
	}

	// 검색 목록 가져오기
	@Override
	public Page<Board> searchCtg(Integer categoryID, Search search, String order, Pageable pageable) {
		return boardMapper.searchCtg(categoryID, search, order, pageable);
	}

	// 이미지 정보 가져오기
	@Override
	public ImageFile getImageFile(Integer fileID) {
		return boardMapper.getImageFile(fileID);
	}

	// 관심아이디로 게시물 정보 가져오기
	@Override
	public Board getPostByLikeID(Integer postID) {
		return boardMapper.getPostByLikeID(postID);
	}
}
