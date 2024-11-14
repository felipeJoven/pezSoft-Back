package com.peces.pezSoft.repository;

import com.peces.pezSoft.model.Pesca;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PescaRepository extends BaseRespository<Pesca, Integer>{

    // Encontrar una lista de pescas por lotes y fecha de pesca
    @Query("SELECT p FROM Pesca p WHERE " +
            "LOWER(p.lote.lote) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
            "CAST(p.fechaCreacion AS string) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Pesca> findByLoteAndFecha(String filtro);
}