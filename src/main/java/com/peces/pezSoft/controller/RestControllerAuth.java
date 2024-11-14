package com.peces.pezSoft.controller;

import com.peces.pezSoft.dtos.LoginDto;
import com.peces.pezSoft.dtos.UsuarioDto;
import com.peces.pezSoft.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("auth")
@CrossOrigin("*")
public class RestControllerAuth {

    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioDto usuarioDto) {
        return authService.registro(usuarioDto);
    }
}