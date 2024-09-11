package com.peces.pezSoft.service;

import com.peces.pezSoft.dtos.LoginDto;
import com.peces.pezSoft.dtos.RegistroDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> listarUsuarios();
    ResponseEntity<?> listarRoles();
    ResponseEntity<?> login(LoginDto dtoLogin);
    ResponseEntity<?> registro(RegistroDto dtoRegistro);

}
