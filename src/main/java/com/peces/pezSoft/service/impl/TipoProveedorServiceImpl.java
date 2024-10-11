package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.model.TipoProveedor;
import com.peces.pezSoft.repository.TipoProveedorRepository;
import com.peces.pezSoft.service.TipoProveedorService;
import com.peces.pezSoft.utils.Message;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TipoProveedorServiceImpl implements TipoProveedorService {

    private TipoProveedorRepository tipoProveedorRepository;

    @Override
    public ResponseEntity<?> verTipoProveedores() {
        try {
            List<TipoProveedor> tipoProveedores = tipoProveedorRepository.findAll();
            if (!tipoProveedores.isEmpty()) {
                return ResponseEntity.ok(tipoProveedores);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Message.MENSAJE_ERROR_VER + "tipo de proveedores!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

}
