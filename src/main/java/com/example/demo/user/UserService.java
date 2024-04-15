package com.example.demo.user;

public interface UserService {
	
	// Join_Form PAGE
	public User FindID(String email); // FindID (for e-mail)
	public User FindPW (String usermail, String userID);// FindPW (for e-mail + ID )
	public User Login (String userID); // Login (ID,PW) + kakaoAPI, GoogleAPI...
	public int Update(String pwNew, String pwEmail);
	

}
