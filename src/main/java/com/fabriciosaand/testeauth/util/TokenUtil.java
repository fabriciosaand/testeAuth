package com.fabriciosaand.testeauth.util;

import com.fabriciosaand.testeauth.model.Usuario;
import com.fabriciosaand.testeauth.security.AuthToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

public class TokenUtil {

    private static final String EMISSOR = "Fabriciosaand";
    private static final String TOKEN_HEADER = "Bearer ";
    private static final String TOKEN_KEY = "01234567890123456789012345678901";
    private static final long UM_SEGUNDO = 1000;
    private static final long UM_MINUTO = 60 * UM_SEGUNDO;

    public static AuthToken encodeToken(Usuario u){
        Key secretKey = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());
        String tokenJWT = Jwts.builder()
                .setSubject(u.getLogin())
                .setIssuer(EMISSOR)
                .setExpiration(new Date(System.currentTimeMillis() + UM_MINUTO))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        AuthToken token = new AuthToken(TOKEN_HEADER + tokenJWT);
        return token;
    }

    public static Authentication decodeToken(HttpServletRequest request){
        try {
            // Pega o tokenJWT
            String jwtToken = request.getHeader("Authorization");

            // Remove o "Bearer "
            jwtToken = jwtToken.replace(TOKEN_HEADER, "");

            // Faz o parser do token
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(
                    TOKEN_KEY.getBytes()).build().parseClaimsJws(jwtToken);

            // Extai as informações
            String usuario = claimsJws.getBody().getSubject();
            String emissor = claimsJws.getBody().getIssuer();
            Date validade = claimsJws.getBody().getExpiration();

            if(usuario.length() > 0 && emissor.equals(EMISSOR) && validade.after(new Date(System.currentTimeMillis()))){
                return new UsernamePasswordAuthenticationToken("user",null, Collections.emptyList());
            }
        } catch (Exception ex){
            System.out.println("DEBUG - Erro ao decodificar token");
            System.out.println(ex.getMessage());
        }

        return null;
    }
}
