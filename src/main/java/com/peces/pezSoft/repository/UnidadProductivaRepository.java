package com.peces.pezSoft.repository;

import com.peces.pezSoft.model.UnidadProductiva;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadProductivaRepository extends BaseRespository<UnidadProductiva, Integer>{

    @Query("SELECT COUNT(u) > 0 FROM UnidadProductiva u WHERE u.unidadP = :unidadP")
    boolean existsByUnidadP(@Param("unidadP") String unidadP);

    @Query("SELECT COUNT(u) > 0 FROM UnidadProductiva u WHERE u.coordenadas = :coordenadas")
    boolean existsByCoordenadas(@Param("coordenadas") String coordenadas);

    @Query("SELECT u FROM UnidadProductiva u WHERE " +
            "LOWER(u.unidadP) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
            "LOWER(u.coordenadas) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<UnidadProductiva> findByUnidadPAndCoordenadas(@Param("filtro") String filtro);
}
