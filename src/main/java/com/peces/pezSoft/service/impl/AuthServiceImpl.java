package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.dtos.DefaultResponseDto;
import com.peces.pezSoft.utils.Message;
import com.peces.pezSoft.dtos.LoginDto;
import com.peces.pezSoft.dtos.RegistroDto;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

    private boolean esAdminAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("Admin"));
    }

    @Override
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> listarRoles() {
        try {
            List<Rol> roles = rolRepository.findAll();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
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
    public ResponseEntity<?> registro(RegistroDto registroDto) {
        try {
            boolean existeUsuario = usuarioRepository.count() > 0;
            if (usuarioRepository.existsByEmail(registroDto.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_EMAIL);
            }
            if (!passwordsEqual(registroDto.getPassword(), registroDto.getConfirmPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_PASSWORD);
            }
            Usuario usuario = new Usuario();
            String telefono = String.valueOf(registroDto.getTelefono());
            if (telefono.length() == 10) {
                usuario.setTelefono(registroDto.getTelefono());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_TELEFONO);
            }
            String email = registroDto.getEmail();
            if (!email.isEmpty()) {
                usuario.setEmail(registroDto.getEmail());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_EMAIL_VACIO);
            }
            usuario.setUsuario(registroDto.getUsername());
            usuario.setPassword(passwordEncoder.encode(registroDto.getPassword()));
            usuario.setNombre(registroDto.getNombre());
            usuario.setApellido(registroDto.getApellido());
            usuario.setFechaCreacion(LocalDate.now());
            if (!existeUsuario) {
                // Si no existe un admin, permite crearlo
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
                // Si ya existe un admin, verificar si es admin
                if (esAdminAutenticado()) {
                    Optional<Rol> optionalRol = rolRepository.findById(registroDto.getRolId());
                    if (optionalRol.isPresent()) {
                        usuario.setRol(optionalRol.get());
                        usuarioRepository.save(usuario);
                        Integer rolId = optionalRol.get().getId();
                        if (rolId.equals(1)) {
                            return ResponseEntity.status(HttpStatus.CREATED)
                                    .body(String.format(Message.MENSAJE_EXITOSO_REGISTRO, "Admin"));

                        } else if (rolId.equals(2)) {
                            return ResponseEntity.status(HttpStatus.CREATED)
                                    .body(String.format(Message.MENSAJE_EXITOSO_REGISTRO, "User"));

                        } else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body(Message.MENSAJE_ERROR_ROL);
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Message.MENSAJE_ERROR_SELECCIONAR_ROL);
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(Message.MENSAJE_ERROR_CREAR_USUARIO);
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }
}
