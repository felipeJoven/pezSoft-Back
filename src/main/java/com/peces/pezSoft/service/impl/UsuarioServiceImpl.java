package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.dtos.UsuarioDto;
import com.peces.pezSoft.model.Rol;
import com.peces.pezSoft.model.Usuario;
import com.peces.pezSoft.repository.RolRepository;
import com.peces.pezSoft.repository.UsuarioRepository;
import com.peces.pezSoft.service.UsuarioService;
import com.peces.pezSoft.utils.Message;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;
    private RolRepository rolRepository;
    private PasswordEncoder passwordEncoder;

    private boolean passwordsEqual(String password, String confirmPassword) {
        return !password.equals(confirmPassword);
    }

    @Override
    public ResponseEntity<?> listarUsuarios(String filtro) {
        try {
            List<Usuario> usuarios;
            if (filtro != null && !filtro.isEmpty()) {
                usuarios = usuarioRepository.findByRolName(filtro);
            } else {
                usuarios = usuarioRepository.findAll();
            }
            List<UsuarioDto> usuarioDtos = usuarios.stream()
                    .map(usuario -> {
                        UsuarioDto usuarioDto = new UsuarioDto();
                        usuarioDto.setId(usuario.getId());
                        usuarioDto.setUsuario(usuario.getUsuario());
                        usuarioDto.setNombre(usuario.getNombre());
                        usuarioDto.setApellido(usuario.getApellido());
                        usuarioDto.setTelefono(usuario.getTelefono());
                        usuarioDto.setEmail(usuario.getEmail());
                        usuarioDto.setRolName(usuario.getRol().getName());
                        return usuarioDto;
                    })
                    .collect(Collectors.toList());
            if (!usuarios.isEmpty()) {
                return ResponseEntity.ok(usuarioDtos);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Message.MENSAJE_ERROR_VER + "usuarios!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> listarUsuarioPorId(Integer id) {
        try {
            Optional<Usuario> usuario = usuarioRepository.findById(id);
            Optional<UsuarioDto> optionalUsuario = usuario.map(user -> {
                        UsuarioDto usuarioDto = new UsuarioDto();
                        usuarioDto.setId(user.getId());
                        usuarioDto.setUsuario(user.getUsuario());
                        usuarioDto.setNombre(user.getNombre());
                        usuarioDto.setApellido(user.getApellido());
                        usuarioDto.setTelefono(user.getTelefono());
                        usuarioDto.setEmail(user.getEmail());
                        usuarioDto.setRolId(user.getRol().getId());
                        usuarioDto.setRolName(user.getRol().getName());
                        return usuarioDto;
                    });
            if (optionalUsuario.isPresent()) {
                return ResponseEntity.ok(optionalUsuario);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_ID + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> listarPerfil() {
        try {
            // Obtener la autenticación actual
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
            }
            // Obtener el email del usuario autenticado
            String email = authentication.getName();
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("No se encontró usuario!"));
            // Mapear los datos del usuario a UsuarioDto
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setUsuario(usuario.getUsuario());
            usuarioDto.setNombre(usuario.getNombre());
            usuarioDto.setApellido(usuario.getApellido());
            usuarioDto.setTelefono(usuario.getTelefono());
            usuarioDto.setEmail(usuario.getEmail());
            usuarioDto.setRolName(usuario.getRol().getName());
            return ResponseEntity.ok(usuarioDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> agregarUsuario(UsuarioDto usuarioDto) {
        try {
            Usuario usuario = new Usuario();
            boolean existeEmail = usuarioRepository.existsByEmail(usuarioDto.getEmail());
            // Verifica si el email existe
            if (existeEmail) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_EMAIL);
            }
            // Valida que las contraseñas coinciden
            if (passwordsEqual(usuarioDto.getPassword(), usuarioDto.getConfirmPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_PASSWORD);
            }
            // Valida que el telefono tenga exactamente 10 digitos
            String telefono = String.valueOf(usuarioDto.getTelefono());
            if (telefono.length() == 10) {
                usuario.setTelefono(usuarioDto.getTelefono());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_TELEFONO);
            }
            // Valida que el email no sea vacío
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
            // Verificar que existan los roles en la bd
            Rol rol = rolRepository.findById(usuarioDto.getRolId())
                    .orElseThrow(() -> new EntityNotFoundException(Message.MENSAJE_ERROR_ROL));
            usuario.setRol(rol);
            usuario.setFechaCreacion(LocalDate.now());
            usuarioRepository.save(usuario);
            return switch (rol.getId()) {
                case 1 -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(String.format(Message.MENSAJE_EXITOSO_REGISTRO, "Admin"));
                case 2 -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(String.format(Message.MENSAJE_EXITOSO_REGISTRO, "User"));
                default -> ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Rol no encontrado!");
            };
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> actualizarUsuario(Integer id, UsuarioDto usuarioDto) {
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                boolean existeEmail = usuarioRepository.existsByEmail(usuarioDto.getEmail());
                // Verifica si el email existe y que sea el mismo que tiene el usuario
                if (!usuarioDto.getEmail().equals(usuario.getEmail()) && existeEmail) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Message.MENSAJE_ERROR_EMAIL);
                }
                // Valida que las contraseñas coinciden
                if (passwordsEqual(usuarioDto.getPassword(), usuarioDto.getConfirmPassword())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Message.MENSAJE_ERROR_PASSWORD);
                }
                // Valida que el telefono tenga exactamente 10 digitos y que sea el mismo que tiene el usuario
                String telefono = String.valueOf(usuarioDto.getTelefono());
                if (!usuarioDto.getTelefono().equals(usuario.getTelefono()) && telefono.length() != 10) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Message.MENSAJE_ERROR_TELEFONO);
                }
                // Valida que el email no sea vacío
                String email = usuarioDto.getEmail();
                if (email.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Message.MENSAJE_ERROR_EMAIL_VACIO);
                }
                usuario.setUsuario(usuarioDto.getUsuario());
                usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
                usuario.setNombre(usuarioDto.getNombre());
                usuario.setApellido(usuarioDto.getApellido());
                usuario.setEmail(usuarioDto.getEmail());
                usuario.setTelefono(usuarioDto.getTelefono());
                usuario.setFechaCreacion(LocalDate.now());
                // Verificar que existan los roles en la bd
                Rol rol = rolRepository.findById(usuarioDto.getRolId())
                        .orElseThrow(() -> new EntityNotFoundException(Message.MENSAJE_ERROR_ROL));
                usuario.setRol(rol);
                usuarioRepository.save(usuario);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(String.format(Message.MENSAJE_EXITOSO_ACTUALIZADO + "el Usuario"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Message.MENSAJE_ERROR_ID + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> actualizarPerfil(UsuarioDto usuarioDto) {
        try {
            // Obtener la autenticación actual
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
            }
            // Obtener el email del usuario autenticado
            String email = authentication.getName();
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("No se encontró usuario!"));
            boolean existeEmail = usuarioRepository.existsByEmail(usuarioDto.getEmail());
            // Verifica si el email existe y que sea el mismo que tiene el usuario
            if (!usuarioDto.getEmail().equals(usuario.getEmail()) && existeEmail) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_EMAIL);
            }
            // Valida que las contraseñas coinciden
            if (passwordsEqual(usuarioDto.getPassword(), usuarioDto.getConfirmPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_PASSWORD);
            }
            // Mapear los datos del usuario a UsuarioDto
            usuario.setUsuario(usuarioDto.getUsuario());
            usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
            usuario.setNombre(usuarioDto.getNombre());
            usuario.setApellido(usuarioDto.getApellido());
            usuario.setTelefono(usuarioDto.getTelefono());
            usuario.setEmail(usuarioDto.getEmail());
            usuarioRepository.save(usuario);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format(Message.MENSAJE_EXITOSO_ACTUALIZADO + "el Usuario"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> eliminarUsuario(Integer id) {
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                usuarioRepository.delete(usuario);
                return ResponseEntity.ok(Message.MENSAJE_EXITOSO_ELIMINADO + "este usuario");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Message.MENSAJE_ERROR_ID + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }
}