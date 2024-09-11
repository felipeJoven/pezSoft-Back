package com.fish.fishNet.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EntradaAlimentosDTO {

    private String numeroFactura;
    private LocalDate fechaVencimiento;
    private String registroIca;
    private Double numeroKilos;
    private int tipoAlimentoId;
    private int proveedorId;
}