package com.example.demo.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardMapper {
	
	List<Board> findAll();

    List<Board> findByCategoryId(@Param("categoryID") Integer categoryID);

    Board findById(@Param("postID") Integer postID);

    void save(Board board);

 	Board selectBoardDetail(Integer postID);

 	void insert(Board board);

 	int updateBoard(Board params);

 	int selectBoardTotalCount();

 	List<Board> selectBoardList();

 	void visitCnt(@Param("postID") Integer postID);

    
}