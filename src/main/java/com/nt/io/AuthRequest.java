package com.nt.io;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthRequest {

	public String email;
	public String token;
	public String role;
	public String password;
}
