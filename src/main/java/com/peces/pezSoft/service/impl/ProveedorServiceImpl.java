package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.service.ProveedorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private static final String MENSAJE_ERROR_PROVEEDOR = "El proveedor ya existe.";
    private static final String MENSAJE_ERROR_ID = "No se encontró el proveedor con ese ID: .";

}
