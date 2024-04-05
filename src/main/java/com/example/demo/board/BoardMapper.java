package com.example.demo.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class BoardMapper {

	@Autowired
	SqlSession sqlSession;

	public List<Board> findAll() {
		return sqlSession.selectList("findAll");
	}

	public List<Board> findAllPage(RowBounds rowBounds) {
		return sqlSession.selectList("findAllPage", rowBounds);
	}

	public List<Board> findByCategoryId(Integer categoryId, RowBounds rowBounds) {
		return sqlSession.selectList("findByCategoryId", categoryId, rowBounds);
	}

	public Board findPostById(Integer postId) {
		return sqlSession.selectOne("findByPostId", postId);
	}

//	Board findById(@Param("postID") Integer postID) {
//		return sqlSession.selectOne("findByPostId",postID);
//	}

	public void visitCnt(@Param("postID") Integer postID) {
		sqlSession.update("visitCnt", postID);
	}

	public int countAll() {
		return sqlSession.selectOne("countAll");
	}

// 	List<Board> getBoardList(Search search);
	public Page<Board> getList(Pageable page) {
		int total = countAll();
		int offset = page.getPageNumber() * page.getPageSize();

		RowBounds rowBounds = new RowBounds(offset, page.getPageSize());
		List<Board> content = sqlSession.selectList("findAllPage", null, rowBounds);

		return new PageImpl<>(content, page, total);
	}

	public Page<Board> search(@Param("search") Search search, Pageable pageable){
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		RowBounds rowBounds = new RowBounds(offset, pageable.getPageSize());
		List<Board> searchResults = sqlSession.selectList("search", search, rowBounds);
		int total = sqlSession.selectOne("countSearchResults", search);
		
		return new PageImpl<>(searchResults, pageable, total);
	}
	
	public Page<Board> searchCtg(Integer categoryID, Search search, String order, Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		RowBounds rowBounds = new RowBounds(offset, pageable.getPageSize());
		List<Board> searchCResults = sqlSession.selectList("searchCtg",
				Map.of("categoryID", categoryID, "search", search, "order", order), rowBounds);
	    int total = sqlSession.selectOne("countSCR", 
	    		Map.of("categoryID", categoryID, "search", search, "order", order));

		return new PageImpl<>(searchCResults, pageable, total);
	}
}