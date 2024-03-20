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

// <<<<<<< main 파일  -- 파일 충돌
// 	Board selectBoardDetail(Integer postID);

// 	void insert(Board board);

// 	int updateBoard(Board params);

// 	int selectBoardTotalCount();

// 	List<Board> selectBoardList();
// =======
// 	void visitCnt(@Param("postID") Integer postID);
// >>>>>>> develop
    
}