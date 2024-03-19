package com.example.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DAO.UserDAO;
import com.example.demo.DTO.UserDTO;

import jakarta.inject.Inject;

@Service
public class UserjoinInsertImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;

	@Override
	public void InsertUser(UserDTO userDTO) {
		userDAO.InsertUser(userDTO);
	}

}
