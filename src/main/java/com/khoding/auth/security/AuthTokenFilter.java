package com.khoding.auth.security;

import com.khoding.auth.service.auth.UserDetailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailServiceImpl userDetailService;
    private final static String LOGGER_PREFIX = "[Auth Token Filter]";
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailServiceImpl userDetailService) {
        this.jwtUtils = jwtUtils;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                LOGGER.info("{} Do Filter Internal", LOGGER_PREFIX);
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                LOGGER.info("{} Username from JWT Token {}", LOGGER_PREFIX, username);
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                LOGGER.info("{} LoadUSerByUsername from JWT Token {}", LOGGER_PREFIX, username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                LOGGER.info("{} UsernamePasswordAuthenticationToken {}", LOGGER_PREFIX, usernamePasswordAuthenticationToken);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (Exception exception){
            LOGGER.error("Error Cannot set UserAuthentication {}", exception.getMessage());
        }
        filterChain.doFilter(request, response);
    }
    
    private String parseJwt(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader)  && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7, authHeader.length());
        }
        return null;
    }
}
