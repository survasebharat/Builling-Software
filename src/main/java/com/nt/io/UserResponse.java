package com.nt.io;

import java.security.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

	public String userId;
	public String name;
	public String email;

	public String role;
	public Timestamp createdAt;
	public Timestamp updatedAt;
}
