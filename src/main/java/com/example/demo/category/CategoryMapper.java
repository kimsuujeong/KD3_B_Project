package com.example.demo.category;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

@Mapper
public interface CategoryMapper {

	@Select("SELECT categoryName FROM category WHERE categoryID = #{categoryID}")	
	String findCategoryNameById(@Param("categoryID") Long categoryID);

}
