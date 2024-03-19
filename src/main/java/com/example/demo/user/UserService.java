package com.example.demo.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DAO.UserDAO;
import com.example.demo.DTO.UserDTO;

public interface UserService {
	
	// Join_Form PAGE
	public void InsertUser (UserDTO userDTO); // Join(insert)
//	public void FindID (String usermail); // FindID (for e-mail)
//	public void FindPW (String usermail, String userID);// FindPW (for e-mail + ID )
//	public void Login (String userID, String userPW); // Login (ID,PW) + kakaoAPI, GoogleAPI...
//	
//	// MY PAGE_Form
//	public void UpdatePW(String UserPWcurrent, String UserPWnext);// UpdatePW (UserPWcurrent -> UserPWnext)
//	// deleteUser
//	public void DeleteUser(String UserPW);
	
	
}
