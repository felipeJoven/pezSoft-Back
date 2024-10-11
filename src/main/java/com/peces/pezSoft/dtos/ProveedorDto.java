package com.peces.pezSoft.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProveedorDto {

    private Integer id;
    private LocalDate fechaCreacion;
    private String nombre;
    private String apellido;
    private Long telefono;
    private String email;
    private String direccion;
    private String razonSocial;
    private Long numeroIdentificacion;
    private int tipoIdentificacionId;
    private int tipoProveedorId;
}
