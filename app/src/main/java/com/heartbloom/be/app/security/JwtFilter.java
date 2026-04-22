package com.heartbloom.be.app.security;

import com.heartbloom.be.app.application.user.implementation.UserReader;
import com.heartbloom.be.core.model.domain.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER = "Bearer ";

    private final JwtValidator jwtValidator;
    private final UserReader userReader;

    public JwtFilter(JwtValidator jwtValidator, UserReader userReader) {
        this.jwtValidator = jwtValidator;
        this.userReader = userReader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            filterChain.doFilter(request,response);
            return;
        }

        String bearer = request.getHeader(AUTHORIZATION_HEADER);
        String token = (StringUtils.hasText(bearer) && bearer.startsWith(BEARER)) ? bearer.substring(7) : null;
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtValidator.isValid(token)) { // 토큰이 유효하다면
                Long userId = jwtValidator.subject(token).get();
                User user = userReader.read(userId);

                CustomUserPrincipal principal = new CustomUserPrincipal(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.isDeleted(),
                        List.of()
                );

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}