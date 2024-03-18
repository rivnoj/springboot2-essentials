package com.rivnoj.springboot2.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rivnoj.springboot2.service.DevDojoUserDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final DevDojoUserDetailsService devDojoUserDetailsService;
  /*
   * BasicAuthenticationFilter: base64 encode/decode user:password
   * UserPasswordAuthenticationFilter: user and password are in request
   * DefaultLoginPageGeneratingFilter
   * DefaultLogoutPageGeneratingFilter
   * FilterSecurityInterceptor: filter that will check if you are authorized
   * Authetication versus Authorization
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
          .disable()
          //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) //habilitar em produção
          //.and()
          .authorizeRequests()
          .anyRequest()
          .authenticated()
          .and()
          .formLogin()
          .and()
          .httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    log.info("Password enconded {}", passwordEncoder.encode("academy"));
    auth.inMemoryAuthentication()
          .withUser("william2")
          .password(passwordEncoder.encode("academy"))
          .roles("USER", "ADMIN")
          .and()
          .withUser("devdojo2")
          .password(passwordEncoder.encode("academy"))
          .roles("USER");

    auth.userDetailsService(devDojoUserDetailsService)
        .passwordEncoder(passwordEncoder);
  }
 
}
