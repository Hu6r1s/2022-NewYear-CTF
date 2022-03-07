package com.securityfirst.winterctf.config;

import com.securityfirst.winterctf.security.JwtAuthProvider;
import com.securityfirst.winterctf.security.JwtAuthenticationEntryPoint;
import com.securityfirst.winterctf.security.JwtAuthenticationFilter;
import com.securityfirst.winterctf.service.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
Spring Security 관련 설정을 하는 Config Class
*/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthProvider jwtAuthProvider;

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  private LogService logService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .cors()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(new JwtAuthenticationFilter(jwtAuthProvider, logService),
            UsernamePasswordAuthenticationFilter.class) // Jwt 인증 로직 필터 추가
        .authorizeRequests()
        .antMatchers("/api/manage/**").hasAnyAuthority("ROLE_ADMIN")
        .antMatchers("/api/login","/api/register/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin().disable();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
