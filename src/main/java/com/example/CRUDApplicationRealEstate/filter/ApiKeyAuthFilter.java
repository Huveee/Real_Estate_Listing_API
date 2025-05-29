package com.example.CRUDApplicationRealEstate.filter;
import org.springframework.beans.factory.annotation.Value;
import com.example.CRUDApplicationRealEstate.config.RequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private String validApiKey;

    private final RequestContext requestContext;

    public ApiKeyAuthFilter(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Value("${api.key}")
    public void setValidApiKey(String validApiKey) {
        this.validApiKey = validApiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader("X-API-Key");
        if (!validApiKey.equals(apiKey)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid API Key");
            return;
        }

        requestContext.setApiKey(apiKey);
        try {
            filterChain.doFilter(request, response);
        } finally {
            requestContext.clear();
        }
    }
}
