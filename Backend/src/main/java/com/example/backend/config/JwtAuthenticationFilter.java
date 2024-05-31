package com.example.backend.config;//package com.example.backend.config;
import com.example.backend.auth.AuthenticationService;
import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.service.UserService;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private final JwtUtil jwtUtil;
    private final Auth0UserInfoService auth0UserInfoService;

    private final UserService userService;
    public JwtAuthenticationFilter(String defaultFilterProcessesUrl, JwtUtil jwtUtil, Auth0UserInfoService auth0UserInfoService,  UserService userService) {
        super(defaultFilterProcessesUrl);
        this.jwtUtil = jwtUtil;
        this.auth0UserInfoService = auth0UserInfoService;
        this.userService = userService;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader("Authorization");
        try{
            Auth0UserInfo userInfo = auth0UserInfoService.getUserInfo(token.substring(7));

            try{
                UserResponse user = userService.getUserByUserEmail(userInfo.getEmail());
                System.out.println(user);
            } catch (Exception e) {
                String[] firstNameLastName = userInfo.getName().split(" ");

                RegisterDto registerDto = new RegisterDto(userInfo.getEmail(), "12345678","contactinfo",firstNameLastName[0],firstNameLastName[1]);
                userService.registerWithoutDuplicateCheck(registerDto);
            }

            if (token != null && token.startsWith("Bearer ")) {
                UserDetails userDetails = User.builder()
                        .username(userInfo.getEmail())
                        .password("12345678")
                        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                        .build();
                if (userDetails != null) {
                    return new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                }
            }
        }catch (Exception e){
            System.out.println("Failed to authenticate user: " + e.getMessage());
            throw new AuthenticationServiceException("Failed to authenticate user: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException, java.io.IOException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException, java.io.IOException {
//        // Your JWT authentication logic here
//        System.out.println(request.getHeader("Authorization"));
//        Auth0UserInfo userInfo = auth0UserInfoService.getUserInfo(request.getHeader("Authorization").substring(7));
//        System.out.println(userInfo);
////        UserDetails userDetails = new CustomUserDetails(
////                userInfo.getEmail(),
////                "null",
////                true,
////                true,
////                true,
////                true,
////                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
////        );
////        System.out.println("Authenticated user sub: " + userInfo.getSub());
////        System.out.println("User email: " + userInfo.getEmail());
////        System.out.println("User name: " + userInfo.getName());
////        System.out.println("User picture: " + userInfo.getPicture());
//
////        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
////                userDetails, null
////                );
////                System.out.println(authToken);
////                SecurityContextHolder.getContext().setAuthentication(authToken);
////
////        //UserResponse user = userService.getUserByUserEmail(userInfo.getEmail());
////
////        request.setAttribute("userEmail", userInfo.getEmail());
//
//        UserDetails userDetails = User.builder()
//                .username("customUser")
//                .password("password")
//                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
//                .build();
//
//        // Create authentication token
//        Authentication authentication = new UsernamePasswordAuthenticationToken(
//                userDetails,
//                null,
//                userDetails.getAuthorities()
//        );
//
//        // Set authentication in the security context
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Now, you can get the username from the security context
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        System.out.println("Authenticated username: " + username);
//
//        filterChain.doFilter(request, response);
//    }
}