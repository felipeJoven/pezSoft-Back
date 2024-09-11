package com.peces.pezSoft.service;

import com.peces.pezSoft.model.UnidadProductiva;
import org.springframework.http.ResponseEntity;

public interface UnidadProductivaService {

    ResponseEntity<?> listarUnidadP();
    ResponseEntity<?> agregarUnidadP(UnidadProductiva unidadP);

}