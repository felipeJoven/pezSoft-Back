package com.peces.pezSoft.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LoteDto {
    private Integer id;
    private String lote;
    private int numeroPeces;
    private int pecesIniciales;
    private LocalDate fechaSiembra;
    private Long diasCultivados;
    private int especieId;
    private int unidadProductivaId;
    private int proveedorId;
    private String especieEspecie;
    private String unidadProductivaUnidadP;
    private String proveedorRazonSocial;
}
