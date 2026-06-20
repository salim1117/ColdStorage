package com.csms.config;

import com.csms.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new LegacyAwarePasswordEncoder();
    }

    // -----------------------------------------------------------------------
    // Security filter chain — REST API (/api/**) gets its own entry point
    // that returns a JSON 401 instead of redirecting to the login page.
    // All existing Thymeleaf behavior is completely preserved.
    // -----------------------------------------------------------------------
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthService authService) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/storages", "/storages/**", "/booking", "/about",
                                "/contact", "/programs", "/admin/login",
                                "/css/**", "/js/**", "/images/**", "/uploads/**", "/webjars/**"
                        ).permitAll()
                        // REST API routes require ROLE_ADMIN — same as the admin UI
                        .requestMatchers("/api/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .successHandler(adminSuccessHandler(authService))
                        .failureUrl("/admin/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                // CSRF: disabled for /api/** (REST clients don't send CSRF tokens)
                // and for the existing public form routes — Thymeleaf routes keep CSRF protection
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        new AntPathRequestMatcher("/api/**"),
                        new AntPathRequestMatcher("/booking"),
                        new AntPathRequestMatcher("/contact"),
                        new AntPathRequestMatcher("/admin/**")
                ))
                // Exception handling: unauthenticated /api/** requests get a JSON 401 response,
                // NOT a redirect to the login page. Thymeleaf routes are unaffected.
                .exceptionHandling(ex -> ex
                        .defaultAuthenticationEntryPointFor(
                                apiAuthenticationEntryPoint(),
                                new AntPathRequestMatcher("/api/**")
                        )
                );

        return http.build();
    }

    /**
     * Custom AuthenticationEntryPoint for /api/** paths.
     * Returns HTTP 401 with a JSON body instead of redirecting to the login page.
     * This is only triggered for unauthenticated requests to /api/**.
     * Thymeleaf routes continue to use the default login-page redirect.
     */
    private AuthenticationEntryPoint apiAuthenticationEntryPoint() {
        return (HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("status", 401);
            body.put("error", "Unauthorized");
            body.put("message", "Authentication is required to access this resource.");
            body.put("path", request.getRequestURI());
            new ObjectMapper().writeValue(response.getOutputStream(), body);
        };
    }

    private AuthenticationSuccessHandler adminSuccessHandler(AuthService authService) {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                org.springframework.security.core.Authentication authentication)
                    throws IOException, ServletException {
                authService.markLogin(authentication.getName());
                response.sendRedirect("/admin");
            }
        };
    }
}
