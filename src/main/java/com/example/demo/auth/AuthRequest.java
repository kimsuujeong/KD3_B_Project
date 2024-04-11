package com.example.demo.auth;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.user.User;

import lombok.Data;

@Data
public class AuthRequest {

	private Integer requestID;
	private String userID;
	private Integer fileID;
	private String name;
	private String link;
	private String status;
	private Integer authorizeID;
	
	private User user;
//	private UploadFile file;
	private MultipartFile file;
}
