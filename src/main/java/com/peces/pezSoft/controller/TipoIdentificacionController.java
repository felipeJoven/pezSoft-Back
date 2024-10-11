package com.peces.pezSoft.controller;

import com.peces.pezSoft.service.TipoIdentificacionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("tipo-identificacion")
@CrossOrigin(origins="*")
public class TipoIdentificacionController {

    private TipoIdentificacionService tipoIdentificacionService;

    @GetMapping("")
    public ResponseEntity<?> obtenerTipoIdentificaciones() {
        return tipoIdentificacionService.verTipoIdentificaciones();
    }
}
