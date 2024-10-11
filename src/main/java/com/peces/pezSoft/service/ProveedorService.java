package com.peces.pezSoft.service;

import com.peces.pezSoft.dtos.ProveedorDto;
import com.peces.pezSoft.model.Proveedor;
import org.springframework.http.ResponseEntity;

public interface ProveedorService {

    ResponseEntity<?> verProveedores(String filtro);
    ResponseEntity<?> verProveedorPorId(Integer id);
    ResponseEntity<?> agregarProveedor(ProveedorDto proveedor);
    ResponseEntity<?> actualizarProveedor(Integer id, ProveedorDto proveedor);
    ResponseEntity<?> eliminarProveedor(Integer id);
}