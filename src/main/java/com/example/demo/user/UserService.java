package com.example.demo.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DAO.UserDAO;
import com.example.demo.DTO.UserDTO;

@Service
public class UserService {
	
	// 서비스단 따로 인터페이스로 만들어서 상속받자
	
	@Autowired
	UserDAO userDAO;
	
	public List<UserDTO> getAll() {
		
        return userDAO.getAll();
        
    }

}
