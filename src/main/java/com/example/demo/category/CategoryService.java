package com.example.demo.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;
	
	public String getCategoryNameById(Long categoryID) {
        return categoryMapper.findCategoryNameById(categoryID);
    }
}
