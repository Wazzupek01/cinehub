package com.pedrycz.cinehub.security;

public class SecurityConstants {
    public static final String[] WHITELIST = {
            "/documentation/**",
            "/auth/**",
            "/user/**",
            "/poster/**",
            "/movies/all/**",
            "/movies/id/**",
            "/movies/title/**",
            "/movies/director/**",
            "/movies/actor/**",
            "/movies/genre/**",
            "/movies/runtime/**",
            "/swagger-ui.html", "/docs", "/swagger-resources/**",
            "/swagger-resources", "/v3/api-docs/**", "/swagger-ui/**", "/webjars/swagger-ui/**", "/proxy/**"
    };
    public static final String BEARER = "Bearer ";
}
