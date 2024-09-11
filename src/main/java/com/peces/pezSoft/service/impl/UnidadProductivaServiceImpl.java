package com.peces.pezSoft.service.impl;

import com.fish.fishNet.utils.Message;
import com.peces.pezSoft.model.UnidadProductiva;
import com.peces.pezSoft.repository.UnidadProductivaRepository;
import com.peces.pezSoft.service.UnidadProductivaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UnidadProductivaServiceImpl implements UnidadProductivaService {

    private UnidadProductivaRepository unidadProductivaRepository;

    @Override
    public ResponseEntity<?> listarUnidadP() {
        try {
            List<UnidadProductiva> unidades = unidadProductivaRepository.findAll();
            return ResponseEntity.ok(unidades);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> agregarUnidadP(UnidadProductiva unidadP) {
        try {
            String unidad = unidadP.getUnidadP();
            String coordenadas = unidadP.getCoordenadas();
            boolean unidadExists = unidadProductivaRepository.existsByUnidadP(unidad);
            boolean coordenadasExists = unidadProductivaRepository.existsByCoordenadas(coordenadas);
            if (unidadExists && coordenadasExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("La unidad productiva y las coordenadas no estan disponibles!");
            } else if (unidadExists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La unidad productiva no está disponible!");
            } else if (coordenadasExists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Las coordenadas no están disponibles!");
            } else {
                unidadP.setFechaCreacion(LocalDate.now());
                unidadProductivaRepository.save(unidadP);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Message.MENSAJE_EXITOSO_GUARDADO + "una unidad productiva");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

}