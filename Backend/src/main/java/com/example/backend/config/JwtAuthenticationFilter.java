package com.example.backend.config;//package com.example.backend.config;
import com.example.backend.auth.AuthenticationService;
import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.service.UserService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.json.JSONException;
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
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import org.json.JSONObject;

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
        String token = request.getHeader("Authorization").substring(7);
        try{
                if (StringUtils.hasText(token)) {
                    JSONObject claims =jwtDecoder(token);

                    String emailClaim = claims.getString("email");
                    String givenName = claims.getString("given_name");
                    String familyName = claims.getString("family_name");
                    String picture = claims.getString("picture");
            try{
                UserResponse user = userService.getUserByUserEmail(emailClaim);
                System.out.println(user);
            } catch (Exception e) {

                RegisterDto registerDto = new RegisterDto(emailClaim, "","",givenName,familyName,picture);
                userService.registerWithoutDuplicateCheck(registerDto);
            }
                UserDetails userDetails = User.builder()
                        .username(emailClaim)
                        .password("")
                        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                        .build();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, Collections.emptyList());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return authentication;
                }

        }catch (Exception e){
            System.out.println("Failed to authenticate user: " + e.getMessage());
            throw new AuthenticationServiceException("Failed to authenticate user: " + e.getMessage(), e);
        }

        return null;
    }

    public JSONObject jwtDecoder(String jwtToken) throws JSONException {
        String[] chunks = jwtToken.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        JSONObject jsonObject = new JSONObject(payload);

        return jsonObject;
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


