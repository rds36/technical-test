package com.dans.multipro.technicaltest.security;

import com.dans.multipro.technicaltest.configuration.ConfigProperties;
import com.dans.multipro.technicaltest.configuration.ObjectMapperConfiguration;
import com.dans.multipro.technicaltest.jwt.JWTTokenVerifierFilter;
import com.dans.multipro.technicaltest.security.filter.AuthenticationFilter;
import com.dans.multipro.technicaltest.security.filter.ExceptionHandlerFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class AppSecurityConfig {

    AuthenticationManager authenticationManager;
    ConfigProperties configProperties;
    ObjectMapperConfiguration mapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, configProperties, mapper);
        authenticationFilter.setFilterProcessesUrl("/authenticate");

        httpSecurity
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(mapper.getObjectMapper()), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTTokenVerifierFilter(configProperties), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return httpSecurity.build();
    }
}