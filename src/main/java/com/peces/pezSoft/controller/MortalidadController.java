package com.peces.pezSoft.controller;

import com.peces.pezSoft.dtos.MortalidadDto;
import com.peces.pezSoft.service.MortalidadService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("mortalidad")
@CrossOrigin(origins="*")
public class MortalidadController {

    private MortalidadService mortalidadService;

    @GetMapping("")
    public ResponseEntity<?> obtenerMortalidades(@RequestParam(required = false) String filtro) {
        return mortalidadService.verMortalidades(filtro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMortalidadId(@PathVariable Integer id) {
        return mortalidadService.verMortalidadPorId(id);
    }

    @PostMapping("")
    public ResponseEntity<?> crearMortalidad(@RequestBody MortalidadDto mortalidadDto) {
        return mortalidadService.agregarMortalidad(mortalidadDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarMortalidad(@PathVariable Integer id, @RequestBody MortalidadDto mortalidadDto) {
        return mortalidadService.actualizarMortalidad(id, mortalidadDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarMortalidad(@PathVariable Integer id) {
        return mortalidadService.eliminarMortalidad(id);
    }
}