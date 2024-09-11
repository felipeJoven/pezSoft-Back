package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.repository.EntradaAlimentosRepository;
import com.peces.pezSoft.service.EntradaAlimentosService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EntradaAlimentosServiceImpl implements EntradaAlimentosService {

    private static final String MENSAJE_ERROR_ENTRADA = "La entrada de alimentos ya existe";

    private EntradaAlimentosRepository entradaAlimentosRepository;


}