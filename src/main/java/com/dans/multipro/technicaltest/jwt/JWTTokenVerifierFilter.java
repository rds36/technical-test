package com.dans.multipro.technicaltest.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dans.multipro.technicaltest.configuration.ConfigProperties;
import com.dans.multipro.technicaltest.security.SecurityConstants;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@AllArgsConstructor
public class JWTTokenVerifierFilter extends OncePerRequestFilter {

    ConfigProperties configProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader(SecurityConstants.AUTHORIZATION); // Bearer token...

        if (bearerToken == null || !bearerToken.startsWith(SecurityConstants.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = bearerToken.replace(SecurityConstants.BEARER, "");
        String secretKey = configProperties.getConfigValue("app.jwt.secretKey");


        String username = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getSubject();

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, Arrays.asList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

//        }

    }
}
