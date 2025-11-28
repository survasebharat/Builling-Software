package com.nt.service.impl;

import java.util.Collections;

import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nt.entity.UserEntity;
import com.nt.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService
{
	public final UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserEntity existingUser = userRepository.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("email Not Fonud for the Email "+email));
		return new User(existingUser.getEmail(),existingUser.getPassword(),Collections.singleton(new SimpleGrantedAuthority(existingUser.getRole())));
	}

} 
