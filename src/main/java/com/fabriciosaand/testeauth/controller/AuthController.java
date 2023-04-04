package com.fabriciosaand.testeauth.controller;

import com.fabriciosaand.testeauth.model.Usuario;
import com.fabriciosaand.testeauth.security.AuthToken;
import com.fabriciosaand.testeauth.util.TokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/free")
    public String free(){
        return "Free - Acesso liberado para todos usuarios";
    }

    @GetMapping("/private")
    public String privado(){
        return "Privado - Acesso liberado para usuarios autenticados";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestBody Usuario usuario){
        if(usuario.getLogin().equals("fabricio") && usuario.getSenha().equals("12345")){
            return ResponseEntity.ok(TokenUtil.encodeToken(usuario));
        }
        return ResponseEntity.status(403).build();
    }


}
