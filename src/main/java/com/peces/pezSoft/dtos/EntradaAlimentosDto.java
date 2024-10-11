package com.peces.pezSoft.dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EntradaAlimentosDto {

    private String numeroFactura;
    private LocalDate fechaVencimiento;
    private String registroIca;
    private Double numeroKilos;
    private int tipoAlimentoId;
    private int proveedorId;
}