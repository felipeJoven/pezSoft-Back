package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.repository.PescaRepository;
import com.peces.pezSoft.service.PescaService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PescaServiceImpl implements PescaService {

    private PescaRepository pescaRepository;

    @Override
    public ResponseEntity<?> listar() {
        return null;
    }

}
