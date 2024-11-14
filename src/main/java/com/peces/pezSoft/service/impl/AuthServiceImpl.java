package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.dtos.DefaultResponseDto;
import com.peces.pezSoft.utils.Message;
import com.peces.pezSoft.dtos.LoginDto;
import com.peces.pezSoft.dtos.UsuarioDto;
import com.peces.pezSoft.model.Rol;
import com.peces.pezSoft.model.Usuario;
import com.peces.pezSoft.repository.RolRepository;
import com.peces.pezSoft.repository.UsuarioRepository;
import com.peces.pezSoft.security.JwtGenerador;
import com.peces.pezSoft.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private JwtGenerador jwtGenerador;
    private RolRepository rolRepository;

    private boolean passwordsEqual(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    @Override
    public ResponseEntity<?> login(LoginDto dtoLogin) {
        try {
            boolean existeUsuario = usuarioRepository.count() > 0;
            if (!existeUsuario) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Message.MENSAJE_ERROR_SIN_USUARIOS);
            }
            Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(dtoLogin.getEmail());
            if (optionalUsuario.isPresent() && passwordEncoder.matches(dtoLogin.getPassword(), optionalUsuario.get().getPassword())) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(dtoLogin.getEmail(), dtoLogin.getPassword());
                var rol = optionalUsuario.get().getRol().getName();
                var usuario = optionalUsuario.get().getUsuario();
                DefaultResponseDto responseDto = new DefaultResponseDto(jwtGenerador.generarToken(authentication, rol, usuario));
                responseDto.setRol(String.valueOf(rol));
                responseDto.setUsuario(String.valueOf(usuario));
                return new ResponseEntity<>(responseDto, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_LOGIN);
            }
        } catch (Exception e) {
            throw new RuntimeException(Message.MENSAJE_ERROR_SOLICITUD + e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<?> registro(UsuarioDto usuarioDto) {
        try {
            boolean sinUsuario = usuarioRepository.count() == 0;
            if (sinUsuario) {
                // Si no existe un admin, permite crearlo
            if (!passwordsEqual(usuarioDto.getPassword(), usuarioDto.getConfirmPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_PASSWORD);
            }
            Usuario usuario = new Usuario();
            String telefono = String.valueOf(usuarioDto.getTelefono());
            if (telefono.length() == 10) {
                usuario.setTelefono(usuarioDto.getTelefono());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_TELEFONO);
            }
            String email = usuarioDto.getEmail();
            if (!email.isEmpty()) {
                usuario.setEmail(usuarioDto.getEmail());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_EMAIL_VACIO);
            }
            usuario.setUsuario(usuarioDto.getUsuario());
            usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
            usuario.setNombre(usuarioDto.getNombre());
            usuario.setApellido(usuarioDto.getApellido());
            usuario.setFechaCreacion(LocalDate.now());
                Optional<Rol> rolAdmin = rolRepository.findByName("Admin");
                if (rolAdmin.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Message.MENSAJE_ERROR_ROL_ADMIN);
                }
                usuario.setRol(rolAdmin.get());
                usuarioRepository.save(usuario);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(String.format(Message.MENSAJE_EXITOSO_REGISTRO, "Admin"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Ya existe un usuario, debe autenticarse!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }
}