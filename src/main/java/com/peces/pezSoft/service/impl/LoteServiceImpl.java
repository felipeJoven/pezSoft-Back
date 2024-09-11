package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.repository.LoteRepository;
import com.peces.pezSoft.service.LoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoteServiceImpl implements LoteService {

    private LoteRepository loteRepository;

}
