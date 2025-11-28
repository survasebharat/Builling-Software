package com.nt.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nt.service.impl.AppUserDetailsService;
import com.nt.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter
{

	private final AppUserDetailsService userDetailsService;
	private final JwtUtil jwtUtil;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authorizationHeader = request.getHeader("authorization");
		
		String email = null;
		String jwt = null;
		if(authorizationHeader != null &&authorizationHeader.startsWith("Bearer"))
		{
			jwt = authorizationHeader.substring(7);
			email = jwtUtil.extractUserName(jwt);
			
		}
		
		if(email!=null &&SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails =userDetailsService.loadUserByUsername(email);
			
			if(jwtUtil.validateToken(jwt, userDetails))
			{
				UsernamePasswordAuthenticationToken authorizationToken = new UsernamePasswordAuthenticationToken( userDetails,null,userDetails.getAuthorities());
				authorizationToken.setDetails(new WebAuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authorizationToken);
				
			
			}
		}
		filterChain.doFilter(request,response);
	}

}
