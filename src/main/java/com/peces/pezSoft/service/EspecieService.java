package com.peces.pezSoft.service;

import com.peces.pezSoft.model.Especie;
import org.springframework.http.ResponseEntity;

public interface EspecieService {
    ResponseEntity<?> listarEspecies(String filtro);
    ResponseEntity<?> listarEspeciePorId(Integer id);
    ResponseEntity<?> agregarEspecie(Especie especie);
    ResponseEntity<?> actualizarEspecie(Integer id, Especie especie);
    ResponseEntity<?> eliminarEspecie(Integer id);
}
