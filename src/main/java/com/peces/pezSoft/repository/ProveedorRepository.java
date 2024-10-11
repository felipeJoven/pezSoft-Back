package com.peces.pezSoft.repository;

import com.peces.pezSoft.model.Proveedor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends BaseRespository<Proveedor, Integer> {

    // Verifica si existe un proveedor en la BD
    Boolean existsByNumeroIdentificacion(Long numero);

    @Query("SELECT p FROM Proveedor p WHERE " +
            "LOWER(p.razonSocial) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
            "CAST(p.numeroIdentificacion AS STRING) LIKE CONCAT('%', :filtro, '%')")
    List<Proveedor> findByRazonSocialAndNumeroIdentificacion(@Param("filtro") String filtro);
}
