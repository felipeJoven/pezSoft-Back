package com.peces.pezSoft.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LoteDto {

    private String lote;
    private int numeroAnimales;
    private LocalDate fechaSiembra;
    private Long diasDeSiembra;
    private int especieId;
    private int unidadProductivaId;
    private int proveedorId;
}
