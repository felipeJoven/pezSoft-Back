package com.peces.pezSoft.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pesca extends Base {
    
    @Column(name="animales_pescados", nullable = false)
    private int animalesPescados;
    
    @Column(name="peso_promedio", nullable = false)
    private double pesoPromedio;

    // Lote
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Lote lote;
    
}
