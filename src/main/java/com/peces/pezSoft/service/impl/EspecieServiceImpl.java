package com.peces.pezSoft.service.impl;

import com.fish.fishNet.utils.Message;
import com.peces.pezSoft.model.Especie;
import com.peces.pezSoft.repository.EspecieRepository;
import com.peces.pezSoft.service.EspecieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EspecieServiceImpl implements EspecieService {

    private EspecieRepository especieRepository;

    @Override
    public ResponseEntity<?> listarEspecies() {
        try {
            List<Especie> especies = especieRepository.findAll();
            return ResponseEntity.ok(especies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> guardarEspecie(Especie especie) {
        try {
            boolean existeEspecie = especieRepository.existByEspecie(especie.getEspecie());
            if (existeEspecie) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("La especie ya exise en la base de datos!");
            } else {
                especie.setFechaCreacion(LocalDate.now());
                especieRepository.save(especie);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Message.MENSAJE_EXITOSO_GUARDADO + "una especie");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }
}
