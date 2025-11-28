package com.nt.service;

import java.util.List;

import com.nt.io.UserRequest;
import com.nt.io.UserResponse;

public interface UserService {

	UserResponse createUser(UserRequest request);
	
	String getUserRole(String email);

	List<UserResponse> readUsers();
	
	void deleteUser(String id);
	
}
