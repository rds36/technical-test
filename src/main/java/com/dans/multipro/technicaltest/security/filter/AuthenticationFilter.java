package com.dans.multipro.technicaltest.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dans.multipro.technicaltest.configuration.ConfigProperties;
import com.dans.multipro.technicaltest.configuration.ObjectMapperConfiguration;
import com.dans.multipro.technicaltest.data.model.ErrorResponse;
import com.dans.multipro.technicaltest.data.model.LoginRequest;
import com.dans.multipro.technicaltest.data.model.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private ConfigProperties configProperties;
    private ObjectMapperConfiguration objectMapperConfiguration;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            log.info("Authentication Filter:");
            log.info("Username: {}", loginRequest.getUsername());
            log.info("Password: {}", loginRequest.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("Successfully authenticated user");
        String secretKey = configProperties.getConfigValue("app.jwt.secretKey");
        int tokenExpires = Integer.parseInt(configProperties.getConfigValue("app.jwt.tokenExpiration"));
        ObjectMapper mapper = objectMapperConfiguration.getObjectMapper();

        String token = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpires * 1000))
                .sign(Algorithm.HMAC256(secretKey));

        LoginResponse loginResponse = new LoginResponse(token, "Bearer", tokenExpires);

        String json = mapper.writeValueAsString(loginResponse);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("Unsuccessfully authenticated user");
        ObjectMapper mapper = objectMapperConfiguration.getObjectMapper();

        String json = mapper.writeValueAsString(new ErrorResponse(Arrays.asList(failed.getMessage())));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}
