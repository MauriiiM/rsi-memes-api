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

import static com.rsi.memegenerator.constant.Routes.*;


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
     * Configuration for what HTTP Requests at certain routes are skipped by Spring Security,  from "/sign-up".
     *
     * @param http not too sure
     * @throws Exception also not too sure
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
        .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET,"/")
            .permitAll()
        .and()
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers(STORAGE + IMAGES + "/**")
            .permitAll()
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