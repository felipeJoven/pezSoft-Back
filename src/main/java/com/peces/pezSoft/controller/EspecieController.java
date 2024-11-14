package com.peces.pezSoft.controller;

import com.peces.pezSoft.model.Especie;
import com.peces.pezSoft.service.EspecieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("especie")
@CrossOrigin(origins = "*")
public class EspecieController {

    private EspecieService especieService;

    @GetMapping("")
    public ResponseEntity<?> obtenerEspecies(@RequestParam(required = false) String filtro) {
        return especieService.listarEspecies(filtro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEspecieId(@PathVariable Integer id) {
        return especieService.listarEspeciePorId(id);
    }

    @PostMapping("")
    public ResponseEntity<?> crearEspecie(@RequestBody Especie especie) {
        return especieService.agregarEspecie(especie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarEspecie(@PathVariable Integer id, @RequestBody Especie especie) {
        return especieService.actualizarEspecie(id, especie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarEspecie(@PathVariable Integer id) {
        return especieService.eliminarEspecie(id);
    }
}