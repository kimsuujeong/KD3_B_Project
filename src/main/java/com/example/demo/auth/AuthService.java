package com.example.demo.auth;

public interface AuthService {

	void authRequest(AuthRequest request, String name, String link, int i);

	AuthRequest getAuthRequestUserIDType(String userID, int i);



}
