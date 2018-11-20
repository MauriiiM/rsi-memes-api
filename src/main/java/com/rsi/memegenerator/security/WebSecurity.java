package com.rsi.memegenerator.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;

import static com.rsi.memegenerator.constant.URLConstants.*;


/**
 * This class overrides Spring Security's default username/password
 */
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Configuration for skipped by Spring Security, such as a POST from "/sign-up".
     *
     * @param http not too sure
     * @throws Exception also not too sure
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
        .and()
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers(STORAGE + IMAGES)
            .permitAll()
//        .and()
//            .csrf()
//            .disable()
//            .authorizeRequests()
//            .antMatchers(HttpMethod.POST, API + USER + "/*")
//            .permitAll()
            .anyRequest()
            .authenticated()
        ;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}