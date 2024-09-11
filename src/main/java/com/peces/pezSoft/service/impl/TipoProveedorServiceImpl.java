package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.repository.TipoProveedorRepository;
import com.peces.pezSoft.service.TipoProveedorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TipoProveedorServiceImpl implements TipoProveedorService {

    private TipoProveedorRepository tipoProveedorRepository;

}
