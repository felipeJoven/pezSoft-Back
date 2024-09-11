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

import java.time.LocalDate;

@Entity
@Table(name = "entrada_alimentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntradaAlimentos extends Base {
   
    @Column(name = "numero_factura", nullable = false)
    private String numeroFactura;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;
    
    @Column(name = "registro_ica", nullable = false)
    private String registroIca;
    
    @Column(name = "numero_kilos", nullable = false)
    private double numeroKilos;

    @Column(name = "kilos_inicial")
    private double kilosInicial;
    
    // TipoAlimento
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private TipoAlimento tipoAlimento;

    // Proveedor
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Proveedor proveedor;

}
