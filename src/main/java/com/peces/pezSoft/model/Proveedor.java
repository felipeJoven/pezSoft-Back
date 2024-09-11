package com.peces.pezSoft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
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
public class Proveedor extends Base {

    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private Long telefono;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String direccion;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "numero_identificacion", nullable = false)
    private Long numeroIdentificacion;

    // Relaciones
    // Tipo de Proveedor
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private TipoProveedor tipoProveedor;

    // Tipo de Identificación
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private TipoIdentificacion tipoIdentificacion;

}
