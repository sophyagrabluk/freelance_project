package com.tms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailService userDetailService;

    @Autowired
    public SecurityConfig(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.POST, "/user/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/user/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/service/**").permitAll()
                .antMatchers(HttpMethod.POST, "/service/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/service/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/service/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/feedback/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/feedback/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/order/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/order/**").hasAnyRole("USER")
                .antMatchers(HttpMethod.PUT, "/order/**").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .userDetailsService(userDetailService)
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
