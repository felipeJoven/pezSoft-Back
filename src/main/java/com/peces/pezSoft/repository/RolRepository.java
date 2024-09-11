package com.peces.pezSoft.repository;

import com.peces.pezSoft.model.Rol;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends BaseRespository<Rol, Integer>{

    //Metodo para poder buscar un rol mediante su nombre
    Optional<Rol> findByName(String name);

}
