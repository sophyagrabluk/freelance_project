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
                .antMatchers(HttpMethod.GET, "/user").permitAll()
                .antMatchers(HttpMethod.GET, "/user/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/user/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.POST, "/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/user").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/user/removeFav").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/user/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/service/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/service/**").permitAll()
                .antMatchers(HttpMethod.POST, "/service").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/service").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/service").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/service/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/feedback/{toWhichServiceId}").permitAll()
                .antMatchers(HttpMethod.GET, "/feedback/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/feedback").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/feedback").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/feedback").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/feedback/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/order/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/order/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/order/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/swagger/**").hasRole("ADMIN")
                .antMatchers("/actuator/**").hasRole("ADMIN")
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