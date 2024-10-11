package com.peces.pezSoft.controller;

import com.peces.pezSoft.dtos.ProveedorDto;
import com.peces.pezSoft.service.ProveedorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("proveedor")
@CrossOrigin(origins="*")
public class ProveedorController {

    private ProveedorService proveedorService;

    @GetMapping("")
    public ResponseEntity<?> obtenerProveedores(@RequestParam(required = false) String filtro) {
        return proveedorService.verProveedores(filtro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProveedorPorId(@PathVariable Integer id) {
        return proveedorService.verProveedorPorId(id);
    }

    @PostMapping("")
    public ResponseEntity<?> crearProveedor(@RequestBody ProveedorDto proveedorDto) {
        return proveedorService.agregarProveedor(proveedorDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarProveedor(@PathVariable Integer id, @RequestBody ProveedorDto proveedorDto) {
        return proveedorService.actualizarProveedor(id, proveedorDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarProveedor(@PathVariable Integer id) {
        return proveedorService.eliminarProveedor(id);
    }
}