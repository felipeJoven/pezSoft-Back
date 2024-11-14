package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.dtos.MortalidadDto;
import com.peces.pezSoft.model.Lote;
import com.peces.pezSoft.model.Mortalidad;
import com.peces.pezSoft.repository.LoteRepository;
import com.peces.pezSoft.repository.MortalidadRepository;
import com.peces.pezSoft.service.MortalidadService;
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
public class MortalidadServiceImpl implements MortalidadService {

    private MortalidadRepository mortalidadRepository;
    private LoteRepository loteRepository;
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> verMortalidades(String filtro) {
        try {
            List<Mortalidad> mortalidades;
            if (filtro != null && !filtro.isEmpty()) {
                mortalidades = mortalidadRepository.findByLoteAndFecha(filtro);
            } else {
                mortalidades = mortalidadRepository.findAll();
            }
            // Se realiza una lista con el dto para ocultar datos
            List<MortalidadDto> mortalidadDtos = mortalidades.stream()
                    .map(mortalidad -> modelMapper.map(mortalidad, MortalidadDto.class))
                    .collect(Collectors.toList());
            if (!mortalidades.isEmpty()) {
                return ResponseEntity.ok(mortalidadDtos);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Message.MENSAJE_ERROR_VER + "mortalidades!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> verMortalidadPorId(Integer id) {
        try {
            Optional<Mortalidad> mortalidad = mortalidadRepository.findById(id);
            // Se realiza una opcion con el dto para ocultar datos
            Optional<MortalidadDto> optionalMortalidad = modelMapper.map(mortalidad, new TypeToken<Optional<MortalidadDto>>() {
            }.getType());
            if (optionalMortalidad.isPresent()) {
                return ResponseEntity.ok(optionalMortalidad);
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
    public ResponseEntity<?> agregarMortalidad(MortalidadDto mortalidadDto) {
        try {
            // El número de peces en el lote no será menor a 0
            if (mortalidadDto.getPecesMuertos() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(String.format(Message.MENSAJE_ERROR_PECES_0, "muertos"));
            }
            if (mortalidadDto.getObservacion().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Message.MENSAJE_ERROR_OBLIGATORIO);
            }
            // Obtener y validar el lote seleccionado
            Lote lote = loteRepository.findById(mortalidadDto.getLoteId())
                    .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado!"));
            // Calcular el nuevo número de peces en el lote
            int numeroPeces = lote.getNumeroPeces() - mortalidadDto.getPecesMuertos();
            if (numeroPeces < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(String.format(Message.MENSAJE_ERROR_PECES, "muertos"));
            }
            // Crear la mortalidad
            Mortalidad mortalidad = new Mortalidad();
            mortalidad.setPecesMuertos(mortalidadDto.getPecesMuertos());
            mortalidad.setObservacion(mortalidadDto.getObservacion());
            mortalidad.setLote(lote);
            mortalidad.setFechaCreacion(LocalDate.now());
            mortalidadRepository.save(mortalidad);
            // Actualizar el número de peces en el lote
            lote.setNumeroPeces(numeroPeces);
            loteRepository.save(lote);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Message.MENSAJE_EXITOSO_GUARDADO + "una mortalidad");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> actualizarMortalidad(Integer id, MortalidadDto mortalidadDto) {
        try {
            Optional<Mortalidad> mortalidadOptional = mortalidadRepository.findById(id);
            if (mortalidadOptional.isPresent()) {
                Mortalidad mortalidad = mortalidadOptional.get();
                int pecesMuertosAntes = mortalidad.getPecesMuertos();
                int pecesMuertosNuevos = mortalidadDto.getPecesMuertos();
                // El número de peces en el lote no será menor a 0
                if (pecesMuertosNuevos <= 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(String.format(Message.MENSAJE_ERROR_PECES_0, "muertos"));
                }
                if (mortalidadDto.getObservacion().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Message.MENSAJE_ERROR_OBLIGATORIO);
                }
                // Obtener y validar el nuevo lote seleccionado
                Lote loteActual = loteRepository.findById(mortalidadDto.getLoteId())
                        .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado!"));
                // Calcular el nuevo número de peces iniciales en el lote
                Lote loteAnterior = mortalidad.getLote();
                int numeroPecesAnterior = loteAnterior.getNumeroPeces();
                int numeroPecesActual = loteActual.getNumeroPeces();
                boolean loteCambiado = !loteAnterior.getId().equals(loteActual.getId());
                if (loteCambiado) {
                    int numeroPecesDevueltos = numeroPecesAnterior + pecesMuertosAntes;
                    // Devolver todos los peces al lote anterior
                    loteAnterior.setNumeroPeces(numeroPecesDevueltos);
                    loteRepository.save(loteAnterior);
                    // Quitar peces a el nuevo lote
                    if (pecesMuertosNuevos > numeroPecesActual) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("No hay suficientes peces en el nuevo lote");
                    }
                    int numeroPecesNuevo = numeroPecesActual - pecesMuertosNuevos;
                    loteActual.setNumeroPeces(numeroPecesNuevo);
                    loteRepository.save(loteActual);
                } else {
                    // Calcular el ajuste para el mismo lote y verificar si hay suficientes peces disponibles
                    int numeroPeces = numeroPecesActual + pecesMuertosAntes;
                    int numeroPecesActualizado = numeroPeces - pecesMuertosNuevos;
                    if (pecesMuertosNuevos > numeroPeces) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(String.format(Message.MENSAJE_ERROR_PECES, "muertos"));
                    }
                    // Actualizar el número de peces en el lote
                    loteActual.setNumeroPeces(numeroPecesActualizado);
                    loteRepository.save(loteActual);
                }
                // Actualizar la mortalidad
                mortalidad.setPecesMuertos(mortalidadDto.getPecesMuertos());
                mortalidad.setObservacion(mortalidadDto.getObservacion());
                mortalidad.setLote(loteActual);
                mortalidad.setFechaCreacion(LocalDate.now());
                mortalidadRepository.save(mortalidad);
                return ResponseEntity.ok(Message.MENSAJE_EXITOSO_ACTUALIZADO + "la mortalidad");
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
    public ResponseEntity<?> eliminarMortalidad(Integer id) {
        try {
            Optional<Mortalidad> mortalidadOptional = mortalidadRepository.findById(id);
            if (mortalidadOptional.isPresent()) {
                Mortalidad mortalidad = mortalidadOptional.get();
                // Devolver los peces a el lote
                Lote lote = mortalidad.getLote();
                int pecesMuertos = mortalidad.getPecesMuertos();
                int numeroPeces = lote.getNumeroPeces();
                // Calcular el nuevo número de peces en el lote
                numeroPeces += pecesMuertos;
                lote.setNumeroPeces(numeroPeces);
                loteRepository.save(lote);
                // Eliminar el registro
                mortalidadRepository.delete(mortalidad);
                return ResponseEntity.ok(Message.MENSAJE_EXITOSO_ELIMINADO + "esta mortalidad");
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