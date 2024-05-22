package com.example.backend.auth;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Auth0UserInfoService {

    private final RestTemplate restTemplate;

    public Auth0UserInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Auth0UserInfo getUserInfo(String accessToken) {
        String userInfoEndpoint = "https://dev-k72vb4107en0ey6q.us.auth0.com/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Auth0UserInfo> response = restTemplate.exchange(userInfoEndpoint, HttpMethod.GET, entity, Auth0UserInfo.class);
        System.out.println(response.getBody());
        return response.getBody();
    }
}
