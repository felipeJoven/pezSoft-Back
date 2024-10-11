package com.peces.pezSoft.service;

import com.peces.pezSoft.model.TipoIdentificacion;
import org.springframework.http.ResponseEntity;

public interface TipoIdentificacionService {
    ResponseEntity<?> verTipoIdentificaciones();
}
