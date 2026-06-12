package com.spring.placement_management_system.security;

import com.spring.placement_management_system.dto.CurrentUser;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {

		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		final String token = authHeader.substring(7);

		if (!jwtService.validateToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		Claims claims = jwtService.getClaimsFromToken(token);

		Long userId = claims.get("userId", Long.class);
		String email = claims.getSubject();
		String role = claims.get("role", String.class);

		CurrentUser currentUser = new CurrentUser(
				userId,
				email,
				role
		);

		SimpleGrantedAuthority authority =
				new SimpleGrantedAuthority("ROLE_" + (role != null ? role : "USER"));

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(
						currentUser,
						null,
						Collections.singletonList(authority)
				);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}
}

