package com.peces.pezSoft.dtos;

import lombok.Data;

@Data
public class SalidaAlimentosDto {

    private String numeroFactura;
    private Double numeroKilos;
    private int loteId;
    private int tipoAlimentoId;

}
