package com.example.baitap7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.baitap7.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider auth
                = new DaoAuthenticationProvider(userDetailsService);

        auth.setPasswordEncoder(passwordEncoder);

        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/enroll/**").hasAuthority("STUDENT")
                .requestMatchers("/home", "/courses", "/register", "/login", "/css/**")
                .permitAll()
                .anyRequest().authenticated()
                )
                .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
                )
                .logout(logout -> logout
                .logoutSuccessUrl("/home")
                );

        return http.build();
    }
}
