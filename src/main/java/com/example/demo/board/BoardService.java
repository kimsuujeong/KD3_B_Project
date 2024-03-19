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

	public List<Board> getPostsByCategoryId(Integer categoryId) {
       
		System.out.println("서비스 카테고리"+boardMapper.findByCategoryId(categoryId));
		return boardMapper.findByCategoryId(categoryId);
    }

    public Board getPostById(Integer postID) {
    	Board post = boardMapper.findById(postID);
    	if (post != null) {
            Category category = post.getCategory_categoryID();
            if (category != null) {
                post.setCategoryName(category.getCategoryName());
                System.out.println("서비스 포스트" + post.getCategoryName());
            }
        }
    	return post;
    }
}
