package com.example.demo.auth;

import java.util.List;

public interface AuthService {

	void authRequest(AuthRequest request, String name, String link, int i);

	AuthRequest getAuthRequestByUserIDAndType(String userID, int i);



}
