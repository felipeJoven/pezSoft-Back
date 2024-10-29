package com.peces.pezSoft.controller;

import com.peces.pezSoft.dtos.UsuarioDto;
import com.peces.pezSoft.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
@CrossOrigin("*")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService usuarioService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> obtenerUsuarios(@RequestParam(required = false) String filtro) {
        return usuarioService.listarUsuarios(filtro);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> obtenerUsuarioId(@PathVariable Integer id) {
        return usuarioService.listarUsuarioPorId(id);
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> obtenerPerfil() {
        return usuarioService.listarPerfil();
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> agregarUsuario(@RequestBody UsuarioDto usuarioDto) {
        return usuarioService.agregarUsuario(usuarioDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> editarUsuario(@PathVariable Integer id, @RequestBody UsuarioDto usuarioDto) {
        return usuarioService.actualizarUsuario(id, usuarioDto);
    }

    @PutMapping("/perfil")
    public ResponseEntity<?> actualizarPerfil(@RequestBody UsuarioDto usuarioDto) {
        return usuarioService.actualizarPerfil(usuarioDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(("hasAuthority('Admin')"))
    public ResponseEntity<?> borrarUsuario(@PathVariable Integer id) {
        return usuarioService.eliminarUsuario(id);
    }
}