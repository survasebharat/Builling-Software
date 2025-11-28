package com.nt.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nt.io.AuthRequest;
import com.nt.io.AuthResponse;
import com.nt.service.UserService;
import com.nt.service.impl.AppUserDetailsService;
import com.nt.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

	
	public final PasswordEncoder passwordEncoder;
	public final AuthenticationManager authenticationManager;
	public final AppUserDetailsService appUserDetailsService;
	
	public final UserService userService;
	
	public final JwtUtil jwtUtils;
	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest request) throws Exception {

	    // 1. Authenticate email + password
	    authenticate(request.getEmail(), request.getPassword());

	    // 2. Load user details (Correct type: UserDetails)
	    final UserDetails userDetails = appUserDetailsService.loadUserByUsername(request.getEmail());

	    // 3. Generate JWT
	    final String jwtToken = jwtUtils.generateToken(userDetails);

	    // 4. Get user role
	    String role = userService.getUserRole(request.getEmail());

	    // 5. Return response
	    return new AuthResponse(request.getEmail(), jwtToken, role);
	}

	
	private void authenticate(String email, String password) throws Exception {
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) 
		{
			throw new Exception("User Disabled");
		}
		catch (BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email Or PAssword Is Incurrect");
		}
		
	}

	@PostMapping("/encode")
	public String encodePassword(@RequestBody Map<String,String>request)
	{
		return passwordEncoder.encode(request.get("password"));
	}
	
}
