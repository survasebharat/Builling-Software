package com.nt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nt.io.UserRequest;
import com.nt.io.UserResponse;
import com.nt.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {

	private final UserService userService;
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponse registerUser(@RequestBody UserRequest request)
	{
		try {
			 return userService.createUser(request);
			 
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable To Create User");
		}
		
	}
	
	@GetMapping("/users")
	public List<UserResponse> readUser()
	{
		return userService.readUsers();
	}
	
	@DeleteMapping("/users/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable String id)
	{
		try {
				userService.deleteUser(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found");
		}
	}
	
	
	
}
