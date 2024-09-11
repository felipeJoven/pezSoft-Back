package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.repository.TipoIdentificacionRepository;
import com.peces.pezSoft.service.TipoIdentificacionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TipoIdentificacionServiceImpl implements TipoIdentificacionService {

    private TipoIdentificacionRepository tipoIdentificacionRepository;

}