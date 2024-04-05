package com.example.demo.board;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper boardMapper;

	@Override
	public List<Board> getAllPosts() {
		return boardMapper.findAll();
	}

	@Override
	public Page<Board> getPostsByCategoryId(Integer categoryId, Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		RowBounds rowBounds = new RowBounds(offset, pageable.getPageSize());
		List<Board> posts = boardMapper.findByCategoryId(categoryId, rowBounds);
		int total = boardMapper.countAll();
		return new PageImpl<>(posts, pageable, total);
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

	@Override
	public Page<Board> searchCtg(Integer categoryID, Search search, String order, Pageable pageable) {
		return boardMapper.searchCtg(categoryID, search, order, pageable);
	}
}
