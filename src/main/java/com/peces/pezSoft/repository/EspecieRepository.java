package com.peces.pezSoft.repository;

import com.peces.pezSoft.model.Especie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecieRepository extends BaseRespository<Especie, Integer>{

    @Query("SELECT COUNT(e) > 0 FROM Especie e WHERE e.especie = :especie")
    boolean existByEspecie(@Param("especie") String especie);
}
