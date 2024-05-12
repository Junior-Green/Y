package com.y.Y.filters;

import com.y.Y.error.ApiError;
import com.y.Y.error.custom_exceptions.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.y.Y.utils.UtilityService.convertObjectToJson;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(ExceptionHandlerFilter.class);

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (CustomException ce){
            ApiError apiError = new ApiError(ce.getStatus(), ce);
            response.setStatus(apiError.getStatus().value());
            response.getWriter().write(convertObjectToJson(apiError));
        }
        catch (SessionAuthenticationException se){
            ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, se);
            response.setStatus(apiError.getStatus().value());
            response.getWriter().write(convertObjectToJson(apiError));
        }
        catch (RuntimeException e) {
            ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e);
            System.out.println(e.getClass());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(convertObjectToJson(apiError));
        }

    }
}
