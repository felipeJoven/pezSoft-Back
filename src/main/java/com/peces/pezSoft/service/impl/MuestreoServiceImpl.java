package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.repository.MuestreoRepository;
import com.peces.pezSoft.service.MuestreoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MuestreoServiceImpl implements MuestreoService {

    private MuestreoRepository muestreoRepository;

}
