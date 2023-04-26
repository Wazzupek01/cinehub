package com.pedrycz.cinehub.security;

public class SecurityConstants {
    public static final String[] WHITELIST = {
            "/auth/**",
            "/movies",
            "/movies/id",
            "/movies/title/**",
            "/director/**",
            "/actor/**",
            "/genre/**",
            "/runtime/**"
    };
    public static final String BEARER = "Bearer ";
}
