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
    public ResponseEntity<?> obtenerUnidades(@RequestParam(required = false) String filtro) {
        return unidadProductivaService.listarUnidadP(filtro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verUnidadId(@PathVariable Integer id) {
        return unidadProductivaService.listarUnidadPPorId(id);
    }

    @PostMapping("")
    public ResponseEntity<?> crearUnidad(@RequestBody UnidadProductiva unidadP) {
        return unidadProductivaService.agregarUnidadP(unidadP);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarUnidad(@PathVariable Integer id, @RequestBody UnidadProductiva unidadP) {
        return unidadProductivaService.actualizarUnidadP(id, unidadP);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarUnidad(@PathVariable Integer id) {
        return unidadProductivaService.eliminarUnidadP(id);
    }
}