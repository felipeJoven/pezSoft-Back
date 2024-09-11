package com.peces.pezSoft.controller;

import com.peces.pezSoft.service.MuestreoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("muestreo")
@CrossOrigin(origins="*")
public class MuestreoController {

    private MuestreoService muestreoService;

}

