package com.fabriciosaand.testeauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
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
}
