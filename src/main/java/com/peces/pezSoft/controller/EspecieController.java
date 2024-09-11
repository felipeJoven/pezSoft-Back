package com.peces.pezSoft.controller;

import com.peces.pezSoft.model.Especie;
import com.peces.pezSoft.service.EspecieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("especie")
@CrossOrigin(origins="*")
public class EspecieController {

    private EspecieService especiesService;

    @GetMapping("")
    public ResponseEntity<?> getAllSpecies() {
        return especiesService.listarEspecies();
    }

    @PostMapping("")
    public ResponseEntity<?> addSpecie(@RequestBody Especie especie) {
        return especiesService.guardarEspecie(especie);
    }
}
