package com.peces.pezSoft.controller;

import com.peces.pezSoft.service.SalidaAlimentosService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("salida-alimentos")
@CrossOrigin(origins="*")
public class SalidaAlimentosController {

    private SalidaAlimentosService salidaAlimentosService;

}