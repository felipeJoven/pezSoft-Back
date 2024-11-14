package com.peces.pezSoft.controller;

import com.peces.pezSoft.dtos.LoteDto;
import com.peces.pezSoft.service.LoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("lote")
@CrossOrigin(origins="*")
public class LoteController {

    private LoteService loteService;

    @GetMapping("")
    public ResponseEntity<?> obtenerLotes(@RequestParam(required = false) String filtro) {
        return loteService.verLotes(filtro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerLoteId(@PathVariable Integer id) {
        return loteService.verLotePorId(id);
    }

    @PostMapping("")
    public ResponseEntity<?> crearLote(@RequestBody LoteDto loteDto) {
        return loteService.agregarLote(loteDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLote(@PathVariable Integer id, @RequestBody LoteDto loteDto) {
        return loteService.actualizarLote(id, loteDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarLote(@PathVariable Integer id) {
        return loteService.eliminarLote(id);
    }
}