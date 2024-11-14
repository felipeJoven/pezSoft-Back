package com.peces.pezSoft.service;

import com.peces.pezSoft.dtos.PescaDto;
import org.springframework.http.ResponseEntity;

public interface PescaService {
    ResponseEntity<?> verPescas(String filtro);
    ResponseEntity<?> verPescaPorId(Integer id);
    ResponseEntity<?> agregarPesca(PescaDto PescaDto);
    ResponseEntity<?> actualizarPesca(Integer id, PescaDto PescaDto);
    ResponseEntity<?> eliminarPesca(Integer id);
}