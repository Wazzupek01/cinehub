package com.pedrycz.cinehub.security;

public class SecurityConstants {
    public static final String[] WHITELIST = {
            "/documentation/**",
            "/auth/**",
            "/user/nickname/**",
            "/poster/**",
            "/movies/all/**",
            "/movies/id/**",
            "/movies/title/**",
            "/movies/director/**",
            "/movies/actor/**",
            "/movies/genre/**",
            "/movies/runtime/**",
            "/review/id/**",
            "/review/movie/**",
            "/review/user/**",
            "/swagger-ui.html", "/docs", "/swagger-resources/**",
            "/swagger-resources", "/v3/api-docs/**", "/swagger-ui/**", "/webjars/swagger-ui/**", "/proxy/**"
    };
}
