package com.peces.pezSoft.service;

import com.peces.pezSoft.model.Especie;
import org.springframework.http.ResponseEntity;

public interface EspecieService {

    ResponseEntity<?> listarEspecies();
    ResponseEntity<?> guardarEspecie(Especie especie);

}
