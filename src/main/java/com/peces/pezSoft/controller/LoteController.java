package com.peces.pezSoft.controller;

import com.peces.pezSoft.service.LoteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("lote")
@CrossOrigin(origins="*")
public class LoteController {

    private LoteService loteService;

}
