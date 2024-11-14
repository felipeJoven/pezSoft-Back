package com.peces.pezSoft.repository;

import com.peces.pezSoft.model.Especie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecieRepository extends BaseRespository<Especie, Integer>{

    // Verifica si una especie existe en la BD
    Boolean existsByEspecie(String especie);
    // Realiza busqueda por especie

    // Encontrar una lista de especies por nombre
    @Query("SELECT e FROM Especie e WHERE " +
            "LOWER(e.especie) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Especie> findByEspecie(@Param("filtro") String filtro);
}