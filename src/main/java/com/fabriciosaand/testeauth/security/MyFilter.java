package com.fabriciosaand.testeauth.security;

import com.fabriciosaand.testeauth.dto.ErroDTO;
import com.fabriciosaand.testeauth.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        if(request.getHeader("Authorization") != null){
            Authentication auth = TokenUtil.decodeToken(request);
            if(auth != null){
                SecurityContextHolder.getContext().setAuthentication(auth);
            }else {
                System.out.println("Erro no token");
                ErroDTO erroDTO = new ErroDTO(401, "Usuário não autorizado para esse sistema");
                response.setStatus(erroDTO.getStatus());
                response.setContentType("application/json");
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().print(mapper.writeValueAsString(erroDTO));
                response.getWriter().flush();
                return;
            }

        }
        filterChain.doFilter(request, response);
    }
}
