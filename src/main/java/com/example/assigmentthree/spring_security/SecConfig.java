package com.example.assigmentthree.spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.assigmentthree.service.impl.UserManagerService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // This class is using security configuration
@EnableWebSecurity
public class SecConfig {
    private final UnAuthEntryPoint unAuthEntryPoint;
    private final UserManagerService userManagerService;
    private final CookieFilter cookieFilter;

    @Autowired
    public SecConfig(UnAuthEntryPoint unAuthEntryPoint, UserManagerService userManagerService, CookieFilter cookieFilter) {
        this.unAuthEntryPoint = unAuthEntryPoint;
        this.userManagerService = userManagerService;
        this.cookieFilter = cookieFilter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(unAuthEntryPoint)) // not authorized -> login page
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers( "/dashboard").authenticated()
                        .requestMatchers( "/upload").authenticated()
                        .requestMatchers( "/uploadTree").authenticated()
                        .requestMatchers( "/allTrees").authenticated()
                        .requestMatchers( "/history").authenticated()
                        .requestMatchers( "/ai").authenticated()
                        .requestMatchers( "/analysis").authenticated()
                        .requestMatchers( "/**").permitAll())
                .httpBasic(withDefaults()); // checks if the user is authenticated

        // checks the username and password
        http.addFilterBefore(cookieFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // checks if the user is in the DB (middleware)
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // hashing the passwords before entering the DB
    @Bean
    public PasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}
