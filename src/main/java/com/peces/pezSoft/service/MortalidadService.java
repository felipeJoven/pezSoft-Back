package com.peces.pezSoft.service;

import com.peces.pezSoft.dtos.MortalidadDto;
import org.springframework.http.ResponseEntity;

public interface MortalidadService {
    ResponseEntity<?> verMortalidades(String filtro);
    ResponseEntity<?> verMortalidadPorId(Integer id);
    ResponseEntity<?> agregarMortalidad(MortalidadDto mortalidadDto);
    ResponseEntity<?> actualizarMortalidad(Integer id, MortalidadDto mortalidadDto);
    ResponseEntity<?> eliminarMortalidad(Integer id);
}