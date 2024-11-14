package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.model.TipoIdentificacion;
import com.peces.pezSoft.repository.TipoIdentificacionRepository;
import com.peces.pezSoft.service.TipoIdentificacionService;
import com.peces.pezSoft.utils.Message;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TipoIdentificacionServiceImpl implements TipoIdentificacionService {

    private TipoIdentificacionRepository tipoIdentificacionRepository;

    @Override
    public ResponseEntity<?> verTipoIdentificaciones() {
        try {
            List<TipoIdentificacion> tipoIdentificaciones = tipoIdentificacionRepository.findAll();
            if (!tipoIdentificaciones.isEmpty()) {
                return ResponseEntity.ok(tipoIdentificaciones);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Message.MENSAJE_ERROR_VER + "tipo de identificaciones!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }
}