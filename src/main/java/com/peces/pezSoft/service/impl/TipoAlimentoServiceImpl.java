package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.repository.TipoAlimentoRepository;
import com.peces.pezSoft.service.TipoAlimentoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TipoAlimentoServiceImpl implements TipoAlimentoService {

    private TipoAlimentoRepository tipoAlimentoRepository;

}
