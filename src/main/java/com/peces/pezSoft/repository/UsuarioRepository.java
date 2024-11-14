package com.peces.pezSoft.repository;

import com.peces.pezSoft.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRespository<Usuario, Integer> {

    // Buscar un usuario mediante su nombre
    Optional<Usuario> findByEmail(String email);

    // Buscar un usuario mediante su username
    Optional<Usuario> findByUsuario(String usuario);

    // Verificar si un usuario existe en la BD
    Boolean existsByEmail(String email);

    // Encontrar usuario por username y rol
    List<Usuario> findByRolName(String filtro);
}