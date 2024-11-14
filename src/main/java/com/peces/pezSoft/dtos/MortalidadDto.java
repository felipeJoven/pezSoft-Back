package com.peces.pezSoft.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MortalidadDto {
    private Integer id;
    private LocalDate fechaCreacion;
    private int pecesMuertos;
    private String observacion;
    private int loteId;
    private String loteLote;
}