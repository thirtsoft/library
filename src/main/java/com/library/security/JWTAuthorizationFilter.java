package com.library.security;



import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JWTAuthorizationFilter /* extends OncePerRequestFilter*/ {
    /*
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // autoriser au frontend d'accèder au backend via le navigateur
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers",
                "Origin, Accept, X-Requested-With, Content-Type,"
                        + "Access-Control-Request-Method,"
                        + "Access-Control-Request-Headers,"
                        + "Authorization");
        response.addHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin,"
                + "Access-Control-Allow-Credentials, Authorization");

        String jwt = request.getHeader(SecutiryParams.HEADER_NAME);
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            if (jwt == null || !jwt.startsWith(SecutiryParams.HEADER_PRIFIX)) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = Jwts.parser()
                    .setSigningKey(SecutiryParams.SECRET)
                    .parseClaimsJws(jwt.replace(SecutiryParams.HEADER_PRIFIX, ""))
                    .getBody();
            String username = claims.getSubject();
            ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(role-> {
                authorities.add(new SimpleGrantedAuthority(role.get("authority")));
            });
            UsernamePasswordAuthenticationToken authenticationUserToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationUserToken);
            filterChain.doFilter(request, response);
        }

    }
*/
}
