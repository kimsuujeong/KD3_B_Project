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

	// 모든 게시물
	public List<Board> findAll() {
		return sqlSession.selectList("findAll");
	}
	// 게시물 저장
	public void save(Board board) {
		sqlSession.insert("save", board);
	}
	// postID로 게시물 정보가져오기
	public Board selectBoardDetail(Integer postID) {
		return sqlSession.selectOne("selectBoardDetail", postID);
	}
	// 게시물 저장
	public void insert(Board board) {
		sqlSession.insert("insert_post", board);
	}
	// 게시물 수정
	public void update(Board board) {
		sqlSession.update("update_post", board);
	}
	// 전체 게시물 수
	public int selectBoardTotalCount() {
		return sqlSession.selectOne("selectBoardTotalCount");
	}
	// 게시물 리스트
	public List<Board> selectBoardList() {
		return sqlSession.selectList("selectBoardList");
	}
	// 조회수
	public void visitCnt(@Param("postID") Integer postID) {
		sqlSession.update("visitCnt",postID);
	}
	// 게시물 삭제
	public void delete(@Param("postID") Integer postID) {
		// TODO Auto-generated method stub
		sqlSession.delete("delete_post", postID);
	}
}
