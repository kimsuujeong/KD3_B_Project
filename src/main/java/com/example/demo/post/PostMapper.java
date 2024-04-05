package com.example.demo.post;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.board.Board;

@Repository
public class PostMapper {

	@Autowired
	SqlSession sqlSession;

	public List<Board> findAll() {
		return sqlSession.selectList("findAll");
	}

	public void save(Board board) {
		sqlSession.insert("save", board);
	}

	public Board selectBoardDetail(Integer postID) {
		return sqlSession.selectOne("selectBoardDetail", postID);
	}

	public void insert(Board board) {
		sqlSession.insert("insert_post", board);
	}

	public void update(Board board) {
		sqlSession.update("update_post", board);
	}

	public int selectBoardTotalCount() {
		return sqlSession.selectOne("selectBoardTotalCount");
	}

	public List<Board> selectBoardList() {
		return sqlSession.selectList("selectBoardList");
	}
	
	public void visitCnt(@Param("postID") Integer postID) {
		sqlSession.update("visitCnt",postID);
	}

	public void delete(@Param("postID") Integer postID) {
		// TODO Auto-generated method stub
		sqlSession.delete("delete_post", postID);
	}
}
