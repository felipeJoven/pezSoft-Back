package com.peces.pezSoft.service;

import com.peces.pezSoft.dtos.LoteDto;
import org.springframework.http.ResponseEntity;

public interface LoteService {
    ResponseEntity<?> verLotes(String filtro);
    ResponseEntity<?> verLotePorId(Integer id);
    ResponseEntity<?> agregarLote(LoteDto loteDto);
    ResponseEntity<?> actualizarLote(Integer id, LoteDto loteDto);
    ResponseEntity<?> eliminarLote(Integer id);
}