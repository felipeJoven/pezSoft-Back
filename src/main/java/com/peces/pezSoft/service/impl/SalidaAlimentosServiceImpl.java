package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.repository.SalidaAlimentosRepository;
import com.peces.pezSoft.service.SalidaAlimentosService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SalidaAlimentosServiceImpl implements SalidaAlimentosService {

    private static final String MENSAJE_ERROR_SALIDA = "No es posible realizar la salida, porque solo existen %s kilos disponibles";
    private static final String MENSAJE_ERROR_LOTE_TIPO_ALIMENTO = "El tipo alimento o el lote seleccionado no es valido.";
    private static final String MENSAJE_ERROR_NUMERO_FACTURA = "El número de factura %s no existe.";

    private SalidaAlimentosRepository salidaAlimentosRepository;
}