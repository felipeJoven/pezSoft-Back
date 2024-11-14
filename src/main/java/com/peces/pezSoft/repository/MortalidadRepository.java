package com.peces.pezSoft.repository;

import com.peces.pezSoft.model.Mortalidad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MortalidadRepository extends BaseRespository<Mortalidad, Integer> {

    // Encontrar una lista de mortalidades por lotes y fecha de muerte
    @Query("SELECT m FROM Mortalidad m WHERE " +
            "LOWER(m.lote.lote) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
            "CAST(m.fechaCreacion AS string) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Mortalidad> findByLoteAndFecha(String filtro);
}