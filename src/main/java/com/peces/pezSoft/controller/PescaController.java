package com.peces.pezSoft.controller;

import com.peces.pezSoft.service.PescaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("pesca")
@CrossOrigin(origins = "*")
public class PescaController {

    private PescaService pescaService;

}