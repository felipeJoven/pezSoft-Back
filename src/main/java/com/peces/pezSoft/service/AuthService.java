package com.peces.pezSoft.service;

import com.peces.pezSoft.dtos.LoginDto;
import com.peces.pezSoft.dtos.UsuarioDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> login(LoginDto dtoLogin);
    ResponseEntity<?> registro(UsuarioDto dtoRegistro);
}
