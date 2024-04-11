package com.example.demo.auth;

import com.example.demo.file.UploadFile;
import com.example.demo.user.User;

import lombok.Data;

@Data
public class AuthorizedUser {

	private Integer authorizeID;
	private String user_userID;
	private Integer fileID;
	
	private UploadFile file;
	private Authorize authorize;
	private User user;
}
