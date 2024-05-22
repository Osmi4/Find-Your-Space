package com.example.backend.config;//package com.example.backend.config;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.backend.auth.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.example.backend.auth.Auth0UserInfo;
import com.example.backend.auth.Auth0UserInfoService;
import com.example.backend.auth.JwtUtil;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.ArrayList;
import java.util.Arrays;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;
    private final Auth0UserInfoService auth0UserInfoService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, Auth0UserInfoService auth0UserInfoService) {
        this.jwtUtil = jwtUtil;
        this.auth0UserInfoService = auth0UserInfoService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        // Your JWT authentication logic here
        System.out.println(request.getHeader("Authorization"));

        Auth0UserInfo userInfo = auth0UserInfoService.getUserInfo(request.getHeader("Authorization").substring(7));
        UserDetails userDetails = new CustomUserDetails(
                userInfo.getEmail(),
                "null",
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        System.out.println("Authenticated user sub: " + userInfo.getSub());
        System.out.println("User email: " + userInfo.getEmail());
        System.out.println("User name: " + userInfo.getName());
        System.out.println("User picture: " + userInfo.getPicture());

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null
                );
                System.out.println(authToken);
                SecurityContextHolder.getContext().setAuthentication(authToken);

        request.setAttribute("userEmail", userInfo.getEmail());

        filterChain.doFilter(request, response);
    }
}