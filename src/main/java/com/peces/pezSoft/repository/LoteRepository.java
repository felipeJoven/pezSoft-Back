package com.peces.pezSoft.repository;

import com.peces.pezSoft.model.Lote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoteRepository extends BaseRespository<Lote, Integer> {

    // Verificar si existe un lote con la misma unidad productiva
    Boolean existsByLote(String lote);

    // Encontrar una lista de lotes por nombre y fecha de siembra
    @Query("SELECT l FROM Lote l WHERE " +
            "LOWER(l.lote) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
            "CAST(l.fechaSiembra AS string) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Lote> findByLoteAndFechaSiembra(String filtro);
}
