package com.peces.pezSoft.controller;

import com.peces.pezSoft.service.ProveedorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("proveedor")
@CrossOrigin(origins="*")
public class ProveedorController {

    private ProveedorService proveedorService;

}