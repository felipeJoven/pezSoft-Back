package com.peces.pezSoft.service;

import com.peces.pezSoft.dtos.UsuarioDto;
import org.springframework.http.ResponseEntity;

public interface UsuarioService {
    ResponseEntity<?> listarUsuarios(String filtro);
    ResponseEntity<?> listarUsuarioPorId(Integer id);
    ResponseEntity<?> listarPerfil();
    ResponseEntity<?> agregarUsuario(UsuarioDto usuarioDto);
    ResponseEntity<?> actualizarUsuario(Integer id, UsuarioDto usuarioDto);
    ResponseEntity<?> actualizarPerfil(UsuarioDto usuarioDto);
    ResponseEntity<?> eliminarUsuario(Integer id);
}