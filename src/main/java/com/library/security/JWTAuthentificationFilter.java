package com.library.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entities.Utilisateur;



import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthentificationFilter /*extends UsernamePasswordAuthenticationFilter*/ {
/*
    private AuthenticationManager authenticationManager;

    public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Utilisateur userinfo = null;
        try {
            userinfo = new ObjectMapper().readValue(request.getInputStream(), Utilisateur.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("*****************");
        System.out.println("Username : " +userinfo.getUsername());
        System.out.println("Username : " +userinfo.getPassword());
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userinfo.getUsername(), userinfo.getPassword()
        ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User userSpring = (User) authResult.getPrincipal();
        String jwt = Jwts.builder()
                .setSubject(userSpring.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+SecutiryParams.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SecutiryParams.SECRET)
                .claim("roles", userSpring.getAuthorities())
                .compact();
        response.addHeader(SecutiryParams.HEADER_NAME, SecutiryParams.HEADER_PRIFIX+jwt);


    }
    */
}
