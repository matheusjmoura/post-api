package com.matheusjmoura.postapi.commons.util;

import com.matheusjmoura.postapi.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        final String authorization = request.getHeader("Authorization");
        if (!StringUtils.startsWith(authorization, BEARER) && StringUtils.isNotBlank(authorization))
            validateToken(BEARER.concat(authorization), request);
        else if (StringUtils.startsWith(authorization, BEARER) && StringUtils.isNotBlank(authorization))
            validateToken(authorization, request);
        chain.doFilter(request, response);
    }

    private void validateToken(String authorization, HttpServletRequest request) {
        String jwtToken = authorization.substring(7);
        try {
            String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            if (StringUtils.isNotEmpty(username) && null == SecurityContextHolder.getContext().getAuthentication()) {
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(jwtToken, userDetails))) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (IllegalArgumentException e) {
            log.error("[JwtRequestFilter.validateToken] Unable to fetch JWT Token");
        } catch (ExpiredJwtException e) {
            log.error("[JwtRequestFilter.validateToken] JWT Token is expired");
        } catch (Exception e) {
            log.error("[JwtRequestFilter.validateToken] Something went wrong - Error: {}", e.getMessage());
        }
    }
}
