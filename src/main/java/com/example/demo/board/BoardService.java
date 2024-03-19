package com.example.demo.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.category.Category;
import com.example.demo.category.CategoryService;

@Service
public class BoardService {

	@Autowired
    private BoardMapper boardMapper;
	
	@Autowired
    private CategoryService categoryService;
	
	public List<Board> getAllPosts() {
		return boardMapper.findAll();
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
}
