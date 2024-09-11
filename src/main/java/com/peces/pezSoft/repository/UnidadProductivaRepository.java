package com.peces.pezSoft.repository;

import com.peces.pezSoft.model.UnidadProductiva;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadProductivaRepository extends BaseRespository<UnidadProductiva, Integer>{

    @Query("SELECT COUNT(u) > 0 FROM UnidadProductiva u WHERE u.unidadP = :unidadP")
    boolean existsByUnidadP(@Param("unidadP") String unidadP);

    @Query("SELECT COUNT(u) > 0 FROM UnidadProductiva u WHERE u.coordenadas = :coordenadas")
    boolean existsByCoordenadas(@Param("coordenadas") String coordenadas);
}
