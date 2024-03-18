package com.example.demo.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.DTO.UserDTO;

@Mapper
public interface UserDAO {
	
	List<UserDTO> getAll();
	
}
