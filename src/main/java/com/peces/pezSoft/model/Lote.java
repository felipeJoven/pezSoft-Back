package com.peces.pezSoft.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lote extends Base {
    
    @Column(nullable = false)
    private String lote;

    @Column(name = "numero_animales", nullable = false)
    private int  numeroAnimales;

    @Column(name = "fecha_siembra", nullable = false)
    private LocalDate fechaSiembra;

    @Column(name = "animales_inicial")
    private Integer animalesInicial;

    @Transient
    private Long diasDeSiembra;

    // Proveedor
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Proveedor proveedor;

    // Especies
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Especie especies;

    // Unidad productiva
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private UnidadProductiva unidadProductiva;

}
