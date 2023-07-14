package com.dans.multipro.technicaltest.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dans.multipro.technicaltest.data.model.ErrorResponse;
import com.dans.multipro.technicaltest.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Exception Handler Global Filter.");

        try {
            filterChain.doFilter(request,response);
        } catch (EntityNotFoundException e){

            // Replace error message EntityNotFound
            String json = mapper.writeValueAsString(new ErrorResponse(Arrays.asList("Username or password is incorrect")));

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (JWTVerificationException e){

            String errorJson = mapper.writeValueAsString(new ErrorResponse(Arrays.asList(e.getMessage())));

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(errorJson);
            response.getWriter().flush();
        } catch (RuntimeException e){
            log.info("Runtime Exception catched.");
            log.info("Error: {}", e.getMessage());
            log.info("Exception: {}", e.toString());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("BAD REQUEST");
            response.getWriter().flush();
        }
    }
}
