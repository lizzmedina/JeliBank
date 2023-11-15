package com.example.jeliBankBackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// class to validate the info of the token and if is sussced it´ll set the authentication of the user who did the request
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtGenerator jwtGenerator;

    private String getTokenFromRequest(HttpServletRequest request){
        System.out.println(request);
        String bearerToken = request.getHeader("Authorization");
        System.out.println(request.getHeader("Authorization"));
        System.out.println(bearerToken);

        if (StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer ")){
            System.out.println("inside if" + bearerToken.substring(7, bearerToken.length()));

            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (StringUtils.hasText(token) && jwtGenerator.validateToken(token)){
            String userName = jwtGenerator.getUserNameFromJwt(token);
            System.out.println("if 50 "+ userName);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
            System.out.println("if  "+ userDetails);
            List<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            System.out.println("if  "+ userRoles);

            if (userRoles.contains("USER") || userRoles.contains("ADMIN")){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
