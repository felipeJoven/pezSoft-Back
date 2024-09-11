package com.peces.pezSoft.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "salida_alimentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalidaAlimentos extends Base {
    
    @Column(name = "numero_factura", nullable = false)
    private String numeroFactura;
    
    @Column(name = "numero_kilos", nullable = false)
    private Double numeroKilos;

    // Lote
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Lote lote;

    // TipoAlimento
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private TipoAlimento tipoAlimento;

}
