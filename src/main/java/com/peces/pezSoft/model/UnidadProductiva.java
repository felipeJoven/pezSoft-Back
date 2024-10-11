package com.peces.pezSoft.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "unidad_productiva")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnidadProductiva extends Base {
    
    @Column(name = "unidad_productiva", nullable = false)
    private String unidadP;
    
    @Column(nullable = false)
    private String area;
    
    @Column(nullable = false)
    private String coordenadas;

    @Column(nullable = false)
    private String observacion;

    @Column(nullable = false)
    private double profundidad;

    @Column(nullable = false)
    private Integer estado;
}