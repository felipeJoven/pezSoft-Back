package com.peces.pezSoft.repository;

import com.peces.pezSoft.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRespository<Usuario, Integer> {

    // Metodo para poder buscar un usuario mediante su nombre
    Optional<Usuario> findByEmail(String email);
    // Metodo para verificar si un usuario existe en la BD
    Boolean existsByEmail(String email);

}
