package com.example.demo.Adminpage;

import java.util.List;

import com.example.demo.auth.AuthRequest;

public interface AdminService {

	boolean isUserAdmin(String userID);

	List<AuthRequest> getAllAuthRequests();

	void approveRequest(Integer requestID);

	void rejectRequest(Integer requestID);

	List<AuthRequest> getAuthRequestsStatus();

}
