package com.peces.pezSoft.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PescaDto {
    private Integer id;
    private LocalDate fechaCreacion;
    private int pecesPescados;
    private double pesoPromedio;
    private int loteId;
    private String loteLote;
}