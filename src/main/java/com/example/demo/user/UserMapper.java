package com.example.demo.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

@Mapper
public interface UserMapper {

    String findUserNameById(@Param("userId") String userID);

	User findById(@Param("userID") User userID);
	
}
