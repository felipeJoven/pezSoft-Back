package com.peces.pezSoft.controller;

import com.peces.pezSoft.dtos.LoginDto;
import com.peces.pezSoft.dtos.RegistroDto;
import com.peces.pezSoft.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("auth")
@CrossOrigin("*")
public class RestControllerAuth {

    private AuthService authService;

    @GetMapping("")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getAllUsers() {
        return authService.listarUsuarios();
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        return authService.listarRoles();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> registerUser(@RequestBody RegistroDto registroDto) {
        return authService.registro(registroDto);
    }
}
