package com.peces.pezSoft.controller;

import com.peces.pezSoft.model.UnidadProductiva;
import com.peces.pezSoft.service.UnidadProductivaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("unidad-productiva")
@CrossOrigin(origins="*")
public class UnidadProductivaController {

    private UnidadProductivaService unidadProductivaService;

    @GetMapping("")
    public ResponseEntity<?> getAllUnits() {
        return unidadProductivaService.listarUnidadP();
    }

    @PostMapping("")
    public ResponseEntity<?> addUnit(@RequestBody UnidadProductiva unidadP) {
        return unidadProductivaService.agregarUnidadP(unidadP);
    }

}