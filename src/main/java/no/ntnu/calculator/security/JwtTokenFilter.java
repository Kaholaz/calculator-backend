package no.ntnu.calculator.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * Filter for checking if the user is authenticated
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final AuthenticationService authenticationService;

    JwtTokenFilter(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Filter for checking if the user is authenticated
     * @param request The request
     * @param response The response
     * @param filterChain The filter chain
     * @throws ServletException If the filter fails
     * @throws IOException If the filter fails
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("Authorization"))
                    .findFirst().ifPresent(cookie -> {
                        try {
                            SecurityContextHolder.getContext().setAuthentication(authenticationService.getAuthentication(cookie.getValue()));
                            response.addCookie(CookieFactory.getAuthorizationCookie(authenticationService.renewToken(cookie.getValue())));
                        } catch (Exception e) {
                            SecurityContextHolder.clearContext();
                        }
                    });
        } else {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
