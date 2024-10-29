package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.utils.Message;
import com.peces.pezSoft.model.UnidadProductiva;
import com.peces.pezSoft.repository.UnidadProductivaRepository;
import com.peces.pezSoft.service.UnidadProductivaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UnidadProductivaServiceImpl implements UnidadProductivaService {

    private UnidadProductivaRepository unidadProductivaRepository;

    @Override
    public ResponseEntity<?> listarUnidadP(String filtro) {
        try {
            List<UnidadProductiva> unidades;
            if (filtro != null && !filtro.isEmpty()) {
                unidades = unidadProductivaRepository.findByUnidadPAndCoordenadas(filtro);
            } else {
                unidades = unidadProductivaRepository.findAll();
            }
            if (!unidades.isEmpty()) {
                return ResponseEntity.ok(unidades);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Message.MENSAJE_ERROR_VER + "unidades productivas!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> listarUnidadPPorId(Integer id) {
        try {
            Optional<UnidadProductiva> optionalUnidad = unidadProductivaRepository.findById(id);
            if (optionalUnidad.isPresent()) {
                return ResponseEntity.ok(optionalUnidad);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_ID + id);
            }
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
            boolean existeUnidad = unidadProductivaRepository.existsByUnidadP(unidad);
            boolean existeCoordenadas = unidadProductivaRepository.existsByCoordenadas(coordenadas);
            if (existeUnidad && existeCoordenadas) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("La unidad productiva y las coordenadas no estan disponibles!");
            } else if (existeUnidad) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(String.format(Message.MENSAJE_ERROR_EXISTE, "esta unidad productiva"));
            } else if (existeCoordenadas) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(String.format(Message.MENSAJE_ERROR_EXISTE, "estas coordenadas"));
            }
            unidadP.setEstado(0);
            unidadP.setFechaCreacion(LocalDate.now());
            unidadProductivaRepository.save(unidadP);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Message.MENSAJE_EXITOSO_GUARDADO + "una unidad productiva");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> actualizarUnidadP(Integer id, UnidadProductiva unidadP) {
        try {
            boolean existeUnidad = unidadProductivaRepository.existsByUnidadP(unidadP.getUnidadP());
            boolean existeCoordenadas = unidadProductivaRepository.existsByCoordenadas(unidadP.getCoordenadas());
            Optional<UnidadProductiva> unidadOptional = unidadProductivaRepository.findById(id);
            if (unidadOptional.isPresent()) {
                UnidadProductiva unidadExistente = unidadOptional.get();
                if (
                        existeUnidad && !unidadP.getUnidadP().equals(unidadExistente.getUnidadP()) &&
                                existeCoordenadas && !unidadP.getCoordenadas().equals(unidadExistente.getCoordenadas())
                ) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("La unidad productiva y las coordenadas no estan disponibles!");
                } else if (!unidadP.getUnidadP().equals(unidadExistente.getUnidadP()) && existeUnidad) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(String.format(Message.MENSAJE_ERROR_EXISTE, "esta unidad productiva"));
                } else if (!unidadP.getCoordenadas().equals(unidadExistente.getCoordenadas()) && existeCoordenadas) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(String.format(Message.MENSAJE_ERROR_EXISTE, "estas coordenadas"));
                }
                // Copia todas las propiedades de unidadP a unidadExistente excepto el ID
                BeanUtils.copyProperties(unidadP, unidadExistente, "id", "fechaCreacion", "estado");
                unidadProductivaRepository.save(unidadExistente);
                return ResponseEntity.ok(Message.MENSAJE_EXITOSO_ACTUALIZADO + "la unidad productiva");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Message.MENSAJE_ERROR_ID + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> eliminarUnidadP(Integer id) {
        try {
            Optional<UnidadProductiva> optionalUnidad = unidadProductivaRepository.findById(id);
            if (optionalUnidad.isPresent()) {
                UnidadProductiva unidadP = optionalUnidad.get();
                unidadProductivaRepository.delete(unidadP);
                return ResponseEntity.ok(Message.MENSAJE_EXITOSO_ELIMINADO + "esta unidad productiva");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Message.MENSAJE_ERROR_ID + id);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }
}