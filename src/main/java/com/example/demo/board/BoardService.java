package com.example.demo.board;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.category.Category;
import com.example.demo.category.CategoryService;

@Service
public class BoardService {

	@Autowired
    private BoardMapper boardMapper;
	
//	@Autowired
//    private CategoryService categoryService;
	
	public List<Board> getAllPosts() {
		return boardMapper.findAll();
	}

	// board register
//	public boolean registerBoard(Board params) {
//		int queryResult = 0;
//
//		if (params.getPostID() == null) {
//			queryResult = boardMapper.insertBoard(params);
//		} else {
//			queryResult = boardMapper.updateBoard(params);
//		}
//
//		return (queryResult == 1) ? true : false;
//	}
	
	public void saveBoard(Board board) {
		// TODO Auto-generated method stub
		boardMapper.insert(board);
	}
	
	// board detail
	public Board getBoardDetail(Integer postID) {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardDetail(postID);
	}
	
	public List<Board> getBoardList() {
		List<Board> boardList = Collections.emptyList();

		int boardTotalCount = boardMapper.selectBoardTotalCount();

		if (boardTotalCount > 0) {
			boardList = boardMapper.selectBoardList();
		}

		return boardList;
	}
	
//	get post's categoryID from category
	public List<Board> getPostsByCategoryId(Integer categoryId) {
       
//		System.out.println("서비스 카테고리"+boardMapper.findByCategoryId(categoryId));
//		boardmapper.xml findByCategoryId
		return boardMapper.findByCategoryId(categoryId);
    }

//	get postID 
//	
    public Board getPostById(Integer postID) {
//    	boardmapper.xml findById
    	Board post = boardMapper.findById(postID);
//    	post is not null start
    	if (post != null) {
            Category category = post.getCategory_categoryID();
            if (category != null) {
                post.setCategoryName(category.getCategoryName());
//                System.out.println("서비스 포스트" + post.getCategoryName());
            }
        }
    	return post;
    }

// <<<<<<< main
	
// 파일 충돌 
	
// =======
// 	public void visitCnt(Integer postID) {
// 		boardMapper.visitCnt(postID);
// 	}
// >>>>>>> develop
}
