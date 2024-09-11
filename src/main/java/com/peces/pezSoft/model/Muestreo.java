package com.peces.pezSoft.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Muestreo extends Base {

    @Column(name="peso_inicial", nullable = false)
    private double pesoInicial;

    @Column(name="peso_actual", nullable = false)
    private double pesoActual;

    // Lote
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Lote lote;

}
