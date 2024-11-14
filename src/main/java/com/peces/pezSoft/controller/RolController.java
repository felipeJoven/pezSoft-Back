package com.peces.pezSoft.controller;

import com.peces.pezSoft.service.RolService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("rol")
@CrossOrigin("*")
public class RolController {

    private RolService rolService;

    @GetMapping("")
    public ResponseEntity<?> obtenerRoles() {
        return rolService.listarRoles();
    }
}