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

import com.example.demo.post.ImageFile;

@Repository
public class BoardMapper {

	@Autowired
	SqlSession sqlSession;

	// 모든 게시물 가져오기
	public List<Board> findAll() {
		return sqlSession.selectList("findAll");
	}

	public List<Board> findAllPage(RowBounds rowBounds) {
		return sqlSession.selectList("findAllPage", rowBounds);
	}

	// 카테고리 아이디별 게시물 가져오기
	public List<Board> findByCategoryId(Integer categoryId, RowBounds rowBounds) {
		return sqlSession.selectList("findByCategoryId", categoryId, rowBounds);
	}

	// 게시물 아이디로 게시물 정보 가져오기
	public Board getPostById(Integer postId) {
		return sqlSession.selectOne("getPostById", postId);
	}

//	Board findById(@Param("postID") Integer postID) {
//		return sqlSession.selectOne("findByPostId",postID);
//	}

	// 조회수
	public void visitCnt(@Param("postID") Integer postID) {
		sqlSession.update("visitCnt", postID);
	}
	
	// 카테고리 별 게시물 수
	public int countAll(Integer categoryId) {
		return sqlSession.selectOne("countAll", categoryId);
	}

	// 목록 가져오기
// 	List<Board> getBoardList(Search search);
	public Page<Board> getList(Pageable page, Integer categoryID) {
		int total = countAll(categoryID);
		int offset = page.getPageNumber() * page.getPageSize();

		RowBounds rowBounds = new RowBounds(offset, page.getPageSize());
		List<Board> content = sqlSession.selectList("findAllPage", null, rowBounds);

		return new PageImpl<>(content, page, total);
	}

	// 검색 목록 가져오기
	public Page<Board> searchCtg(Integer categoryID, Search search, String order, Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		RowBounds rowBounds = new RowBounds(offset, pageable.getPageSize());
		List<Board> searchCResults = sqlSession.selectList("searchCtg",
				Map.of("categoryID", categoryID, "search", search, "order", order), rowBounds);
	    int total = sqlSession.selectOne("countSCR", 
	    		Map.of("categoryID", categoryID, "search", search, "order", order));

		return new PageImpl<>(searchCResults, pageable, total);
	}
	
	public Page<Board> search(Search search, Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		RowBounds rowBounds = new RowBounds(offset, pageable.getPageSize());
		List<Board> searchResults = sqlSession.selectList("search", search, rowBounds);
		int total = sqlSession.selectOne("count", search);

		return new PageImpl<>(searchResults, pageable, total);
	}
	
	// 이미지 정보 가져오기
	public ImageFile getImageFile(Integer fileID) {
		return sqlSession.selectOne("getImageFile", fileID);
	}

	// 관심아이디로 게시물 정보 가져오기
	public Board getPostByLikeID(Integer postID) {
		return sqlSession.selectOne("getPostByLikeID", postID);
	}
	// 비용 정보
	public String getCostName(Integer costID) {
		return sqlSession.selectOne("getCostName", costID);
	}
}