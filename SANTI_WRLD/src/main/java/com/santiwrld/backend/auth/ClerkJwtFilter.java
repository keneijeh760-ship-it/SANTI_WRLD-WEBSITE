package com.santiwrld.backend.auth;

import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClerkJwtFilter extends OncePerRequestFilter {

    private ClerkJwtUtil clerkJwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader  = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try{
            JWTClaimsSet claims = clerkJwtUtil.validateToken(token);

            String userId = claims.getSubject();
            String role = (String) claims.getClaim("org_role");
            String authority = (role != null && !role.isBlank())
                    ? role
                    : "ROLE_USER";

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                List.of(new SimpleGrantedAuthority(authority))
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (RuntimeException e) {
            // Token was invalid, expired, or tampered with.
            // Clear any partial authentication state to be safe.
            SecurityContextHolder.clearContext();

            // Return 401 immediately — don't continue the filter chain
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired token: " + e.getMessage());
            return;


        }

        filterChain.doFilter(request, response);
    }
}
