package com.heartbloom.be.app.security.access;

import com.heartbloom.be.app.security.CustomUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class RequestUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestUser.class)
                && AccessUser.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return new AnonymousUser();
        }

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext()
                    .getAuthentication();
            CustomUserPrincipal principal = (CustomUserPrincipal) token.getPrincipal();
            return createAuthenticateUser(principal);

        }
        return new AnonymousUser();
    }

    private AuthenticateUser createAuthenticateUser(CustomUserPrincipal principal) {
        return new AuthenticateUser(
                principal.getId(),
                principal.getUsername(),
                principal.getEmail(),
                principal.isEnabled()
        );
    }

}