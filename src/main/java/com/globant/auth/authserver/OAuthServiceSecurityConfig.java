package com.globant.auth.authserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.auth.authserver.authentication.AppAuthenticationEntryPoint;
import com.globant.auth.authserver.authentication.provider.JwtAuthenticationProvider;
import com.globant.auth.authserver.authentication.filter.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class OAuthServiceSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String AUTH_TOKEN_REQUEST = "/authservice/oauth/token/get";
    private static final String REFRESHTOKEN_REQUEST = "/oauth/token/refresh";
    private static final String USER_REGISTRATION_REQUEST = "/user/**";

    @Autowired
    private AppAuthenticationEntryPoint entryPoint;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private JwtAuthenticationProvider authenticationProvider;
    @Autowired
    private ObjectMapper objectMapper;

    private JwtUsernameAndPasswordAuthenticationFilter buildJwtLoginProcessingFilter() {
        JwtUsernameAndPasswordAuthenticationFilter filter = new JwtUsernameAndPasswordAuthenticationFilter(AUTH_TOKEN_REQUEST, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests() //
                .regexMatchers(HttpMethod.POST, AUTH_TOKEN_REQUEST).permitAll()
                .antMatchers(USER_REGISTRATION_REQUEST).permitAll()
                .regexMatchers("/api/sayHello").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated() //
                .and()
                .addFilterBefore(buildJwtLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}

