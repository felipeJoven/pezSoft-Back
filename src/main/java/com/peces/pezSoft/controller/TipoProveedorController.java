package com.peces.pezSoft.controller;

import com.peces.pezSoft.service.TipoProveedorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("tipo-proveedor")
@CrossOrigin(origins="*")
public class TipoProveedorController {

    private TipoProveedorService tipoProveedorService;

    @GetMapping("")
    public ResponseEntity<?> obtenerTipoProveedores() {
        return tipoProveedorService.verTipoProveedores();
    }
}