package com.tagme.tagme_bank_back.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Order(1)
public class RoutesWithoutTokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String path = httpServletRequest.getRequestURI();

        List<String> allowedPaths = List.of(
                "/auth/login",
                "/payments/credit-card"
        );

        if (allowedPaths.contains(path)) {
            request.getRequestDispatcher(path).forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}