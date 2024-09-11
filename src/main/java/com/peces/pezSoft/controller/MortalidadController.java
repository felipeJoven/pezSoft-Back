package com.peces.pezSoft.controller;

import com.peces.pezSoft.service.MortalidadService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("mortalidad")
@CrossOrigin(origins="*")
public class MortalidadController {

    private MortalidadService mortalidadService;

}
