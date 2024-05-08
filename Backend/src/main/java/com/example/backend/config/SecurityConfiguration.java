package com.example.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.sql.DataSource;

import static com.example.backend.enums.Role.ADMIN;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/health/**").permitAll()
//                        .requestMatchers("/index.html").permitAll()
//                        .requestMatchers("/login/oauth2/code/google").permitAll()
//                        // more matchers
//                        .anyRequest().authenticated())
//                .oauth2Login()
//                .and()
//                .logout(logout -> logout
//                        .logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository))
//                )
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build();
//    }
//
//    private LogoutSuccessHandler oidcLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository) {
//        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
//        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
//        return oidcLogoutSuccessHandler;
//    }
//}
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/health/**").permitAll()
                        .requestMatchers("/index.html").permitAll()
                        .requestMatchers("/api/payment/charge").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "favicon.ico").permitAll()
                        .requestMatchers("/api/auth/change-password").authenticated()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/permission/**").hasAnyRole(ADMIN.name())
                        .requestMatchers("/api/admin/**").hasAnyRole(ADMIN.name())
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
