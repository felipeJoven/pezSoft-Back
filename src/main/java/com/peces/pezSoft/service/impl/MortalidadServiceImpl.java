package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.repository.MortalidadRepository;
import com.peces.pezSoft.service.MortalidadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MortalidadServiceImpl implements MortalidadService {

    private MortalidadRepository mortalidadRepository;

}
