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
public class Mortalidad extends Base {

    @Column(name = "peces_muertos", nullable = false)
    private int pecesMuertos;

    @Column(name = "observacion", nullable = false)
    private String observacion;

    // Lote
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Lote lote;
}