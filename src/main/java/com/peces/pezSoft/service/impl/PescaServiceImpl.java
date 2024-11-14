package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.dtos.PescaDto;
import com.peces.pezSoft.model.Lote;
import com.peces.pezSoft.model.Pesca;
import com.peces.pezSoft.repository.LoteRepository;
import com.peces.pezSoft.repository.PescaRepository;
import com.peces.pezSoft.service.PescaService;
import com.peces.pezSoft.utils.Message;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PescaServiceImpl implements PescaService {

    private PescaRepository pescaRepository;
    private LoteRepository loteRepository;
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> verPescas(String filtro) {
        try {
            List<Pesca> pescas;
            if (filtro != null && !filtro.isEmpty()) {
                pescas = pescaRepository.findByLoteAndFecha(filtro);
            } else {
                pescas = pescaRepository.findAll();
            }
            // Se realiza una lista con el dto para ocultar datos
            List<PescaDto> pescaDtos = pescas.stream()
                    .map(pesca -> modelMapper.map(pesca, PescaDto.class))
                    .collect(Collectors.toList());
            if (!pescas.isEmpty()) {
                return ResponseEntity.ok(pescaDtos);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Message.MENSAJE_ERROR_VER + "pescas!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> verPescaPorId(Integer id) {
        try {
            Optional<Pesca> pesca = pescaRepository.findById(id);
            // Se realiza una opcion con el dto para ocultar datos
            Optional<PescaDto> optionalPesca = modelMapper.map(pesca, new TypeToken<Optional<PescaDto>>() {
            }.getType());
            if (optionalPesca.isPresent()) {
                return ResponseEntity.ok(optionalPesca);
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
    public ResponseEntity<?> agregarPesca(PescaDto pescaDto) {
        try {
            // El número de peces en el lote no será menor a 0
            if (pescaDto.getPecesPescados() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(String.format(Message.MENSAJE_ERROR_PECES_0, "pescados"));
            }
            if (pescaDto.getPesoPromedio() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_OBLIGATORIO);
            }
            // Obtener y validar el lote seleccionado
            Lote lote = loteRepository.findById(pescaDto.getLoteId())
                    .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado!"));
            // Calcular el nuevo número de peces en el lote
            int numeroPeces = lote.getNumeroPeces() - pescaDto.getPecesPescados();
            if (numeroPeces < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(String.format(Message.MENSAJE_ERROR_PECES, "pescados"));
            }
            // Crear la pesca
            Pesca pesca = new Pesca();
            pesca.setPecesPescados(pescaDto.getPecesPescados());
            pesca.setPesoPromedio(pescaDto.getPesoPromedio());
            pesca.setLote(lote);
            pesca.setFechaCreacion(LocalDate.now());
            pescaRepository.save(pesca);
            // Actualizar el número de peces en el lote
            lote.setNumeroPeces(numeroPeces);
            loteRepository.save(lote);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Message.MENSAJE_EXITOSO_GUARDADO + "una pesca");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> actualizarPesca(Integer id, PescaDto pescaDto) {
        try {
            Optional<Pesca> pescaOptional = pescaRepository.findById(id);
            if (pescaOptional.isPresent()) {
                Pesca pesca = pescaOptional.get();
                int pecesPescadosAntes = pesca.getPecesPescados();
                int pecesPescadosNuevos = pescaDto.getPecesPescados();
                // El número de peces en el lote no será menor a 0
                if (pecesPescadosNuevos <= 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(String.format(Message.MENSAJE_ERROR_PECES_0, "pescados"));
                }
                if (pescaDto.getPesoPromedio() <= 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Message.MENSAJE_ERROR_OBLIGATORIO);
                }
                // Obtener y validar el nuevo lote seleccionado
                Lote loteActual = loteRepository.findById(pescaDto.getLoteId())
                        .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado!"));
                // Calcular el nuevo número de peces iniciales en el lote
                Lote loteAnterior = pesca.getLote();
                int numeroPecesAnterior = loteAnterior.getNumeroPeces();
                int numeroPecesActual = loteActual.getNumeroPeces();
                boolean loteCambiado = !loteAnterior.getId().equals(loteActual.getId());
                if (loteCambiado) {
                    int numeroPecesDevueltos = numeroPecesAnterior + pecesPescadosAntes;
                    // Devolver todos los peces al lote anterior
                    loteAnterior.setNumeroPeces(numeroPecesDevueltos);
                    loteRepository.save(loteAnterior);
                    // Quitar peces a el nuevo lote
                    if (pescaDto.getPecesPescados() > loteActual.getNumeroPeces()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("No hay suficientes peces en el nuevo lote");
                    }
                    int numeroPecesNuevo = numeroPecesActual - pecesPescadosNuevos;
                    loteActual.setNumeroPeces(numeroPecesNuevo);
                    loteRepository.save(loteActual);
                } else {
                    // Calcular el ajuste para el mismo lote y verificar si hay suficientes peces disponibles
                    int numeroPeces = loteActual.getNumeroPeces() + pecesPescadosAntes;
                    int numeroPecesActualizado = numeroPeces - pecesPescadosNuevos;
                    if (pecesPescadosNuevos > numeroPeces) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(String.format(Message.MENSAJE_ERROR_PECES, "pescados"));
                    }
                    // Actualizar el número de peces en el lote
                    loteActual.setNumeroPeces(numeroPecesActualizado);
                    loteRepository.save(loteActual);
                }
                // Actualizar la pesca
                pesca.setPecesPescados(pescaDto.getPecesPescados());
                pesca.setPesoPromedio(pescaDto.getPesoPromedio());
                pesca.setLote(loteActual);
                pesca.setFechaCreacion(LocalDate.now());
                pescaRepository.save(pesca);
                return ResponseEntity.ok(Message.MENSAJE_EXITOSO_ACTUALIZADO + "la pesca");
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
    public ResponseEntity<?> eliminarPesca(Integer id) {
        try {
            Optional<Pesca> pescaOptional = pescaRepository.findById(id);
            if (pescaOptional.isPresent()) {
                Pesca pesca = pescaOptional.get();
                // Devolver los peces a el lote
                Lote lote = pesca.getLote();
                int pecesPescados = pesca.getPecesPescados();
                int numeroPeces = lote.getNumeroPeces();
                // Calcular el nuevo número de peces en el lote
                numeroPeces += pecesPescados;
                lote.setNumeroPeces(numeroPeces);
                loteRepository.save(lote);
                // Eliminar el registro
                pescaRepository.delete(pesca);
                return ResponseEntity.ok(Message.MENSAJE_EXITOSO_ELIMINADO + "esta pesca");
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