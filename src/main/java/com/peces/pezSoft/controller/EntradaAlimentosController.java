package com.peces.pezSoft.controller;


import com.peces.pezSoft.service.EntradaAlimentosService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("entrada-alimentos")
@CrossOrigin(origins="*")
public class EntradaAlimentosController {

    private EntradaAlimentosService entradaAlimentosService;

}
