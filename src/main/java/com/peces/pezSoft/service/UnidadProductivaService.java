package com.peces.pezSoft.service;

import com.peces.pezSoft.model.UnidadProductiva;
import org.springframework.http.ResponseEntity;

public interface UnidadProductivaService {

    ResponseEntity<?> listarUnidadP(String filtro);
    ResponseEntity<?> listarUnidadPPorId(Integer id);
    ResponseEntity<?> agregarUnidadP(UnidadProductiva unidadP);
    ResponseEntity<?> actualizarUnidadP(Integer id, UnidadProductiva unidadP);
    ResponseEntity<?> eliminarUnidadP(Integer id);
}