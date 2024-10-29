package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.dtos.ProveedorDto;
import com.peces.pezSoft.model.Proveedor;
import com.peces.pezSoft.model.TipoIdentificacion;
import com.peces.pezSoft.model.TipoProveedor;
import com.peces.pezSoft.repository.ProveedorRepository;
import com.peces.pezSoft.repository.TipoIdentificacionRepository;
import com.peces.pezSoft.repository.TipoProveedorRepository;
import com.peces.pezSoft.service.ProveedorService;
import com.peces.pezSoft.utils.Message;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.Conditions;
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
public class ProveedorServiceImpl implements ProveedorService {

    private ProveedorRepository proveedorRepository;
    private TipoIdentificacionRepository tipoIdentificacionRepository;
    private TipoProveedorRepository tipoProveedorRepository;
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> verProveedores(String filtro) {
        try {
            List<Proveedor> proveedores;
            if (filtro != null && !filtro.isEmpty()) {
                proveedores = proveedorRepository.findByRazonSocialAndNumeroIdentificacion(filtro);
            } else {
                proveedores = proveedorRepository.findAll();
            }
            List<ProveedorDto> proveedorDtos = proveedores.stream()
                    .map(proveedor -> modelMapper.map(proveedor, ProveedorDto.class))
                    .collect(Collectors.toList());
            if (!proveedores.isEmpty()) {
                return ResponseEntity.ok(proveedorDtos);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Message.MENSAJE_ERROR_VER + "proveedores!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> verProveedorPorId(Integer id) {
        try {
            Optional<Proveedor> proveedor = proveedorRepository.findById(id);
            Optional<ProveedorDto> optionalProveedor = modelMapper.map(proveedor, new TypeToken<Optional<ProveedorDto>>() {
            }.getType());
            if (optionalProveedor.isPresent()) {
                return ResponseEntity.ok(optionalProveedor);
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
    public ResponseEntity<?> agregarProveedor(ProveedorDto proveedorDto) {
        try {
            boolean existeProveedor = proveedorRepository.existsByNumeroIdentificacion(proveedorDto.getNumeroIdentificacion());
            if (!existeProveedor) {
                Proveedor proveedor = modelMapper.map(proveedorDto, Proveedor.class);
                // Validar si los números de identificación y teléfono contienen la cantidad correcta de caracteres
                String identificacion = String.valueOf(proveedorDto.getNumeroIdentificacion());
                String telefono = String.valueOf(proveedorDto.getTelefono());
                if (identificacion.length() < 7 || identificacion.length() > 10) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("El número de documento debe contener entre 7 y 10 números!");
                }
                if (telefono.length() != 10) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("El télefono debe contener exactamente 10 números!");
                }
                // Verificar que existan los tipos de identificación y proveedores en la bd
                TipoIdentificacion tipoIdentificacion = tipoIdentificacionRepository.findById(proveedorDto.getTipoIdentificacionId())
                        .orElseThrow(() -> new EntityNotFoundException("Tipo de Identificación no encontrado!"));
                proveedor.setTipoIdentificacion(tipoIdentificacion);
                TipoProveedor tipoProveedor = tipoProveedorRepository.findById(proveedorDto.getTipoProveedorId())
                        .orElseThrow(() -> new EntityNotFoundException("Tipo de Proveedor no encontrado!"));
                proveedor.setTipoProveedor(tipoProveedor);
                proveedor.setFechaCreacion(LocalDate.now());
                proveedorRepository.save(proveedor);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Message.MENSAJE_EXITOSO_GUARDADO + "un proveedor");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(String.format(Message.MENSAJE_ERROR_EXISTE, "el proveedor"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> actualizarProveedor(Integer id, ProveedorDto proveedorDto) {
        try {
            boolean existeProveedor = proveedorRepository.existsByNumeroIdentificacion(proveedorDto.getNumeroIdentificacion());
            Optional<Proveedor> proveedorOptional = proveedorRepository.findById(id);
            if (proveedorOptional.isPresent()) {
                Proveedor proveedor = proveedorOptional.get();
                // Validaciones para actualizar con datos que no existen en la bd y poder actualizar otros campos
                if (!proveedorDto.getNumeroIdentificacion().equals(proveedor.getNumeroIdentificacion()) && existeProveedor) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(String.format(Message.MENSAJE_ERROR_EXISTE, "este proveedor!"));
                }
                // Validar si los números de identificación y teléfono contienen la cantidad correcta de caracteres
                String identificacion = String.valueOf(proveedorDto.getNumeroIdentificacion());
                String telefono = String.valueOf(proveedorDto.getTelefono());
                if (
                        !proveedorDto.getNumeroIdentificacion().equals(proveedor.getNumeroIdentificacion()) &&
                                identificacion.length() < 7 || identificacion.length() > 10
                ) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("El número de documento debe contener entre 7 y 10 números!");
                }
                if (!proveedorDto.getTelefono().equals(proveedor.getTelefono()) && telefono.length() != 10) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("El télefono debe contener exactamente 10 números!");
                }
                // Verificar que exitan los tipos de identificación y proveedores en la bd
                TipoIdentificacion tipoIdentificacion = tipoIdentificacionRepository.findById(proveedorDto.getTipoIdentificacionId())
                        .orElseThrow(() -> new EntityNotFoundException("Tipo de Identificación no encontrado"));
                proveedor.setTipoIdentificacion(tipoIdentificacion);
                TipoProveedor tipoProveedor = tipoProveedorRepository.findById(proveedorDto.getTipoProveedorId())
                        .orElseThrow(() -> new EntityNotFoundException("Tipo de Proveedor no encontrado"));
                proveedor.setTipoProveedor(tipoProveedor);
                proveedor.setNumeroIdentificacion(proveedorDto.getNumeroIdentificacion());
                // Configurar ModelMapper para ignorar los campos que no deben cambiar
                modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
                modelMapper.typeMap(ProveedorDto.class, Proveedor.class).addMappings(mapper -> {
                    mapper.skip(Proveedor::setId);
                    mapper.skip(Proveedor::setFechaCreacion);
                });
                // Aplicar la actualización
                modelMapper.map(proveedorDto, proveedor);
                proveedorRepository.save(proveedor);
                return ResponseEntity.ok(Message.MENSAJE_EXITOSO_ACTUALIZADO + "el proveedor");
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
    public ResponseEntity<?> eliminarProveedor(Integer id) {
        try {
            Optional<Proveedor> proveedorOptional = proveedorRepository.findById(id);
            if (proveedorOptional.isPresent()) {
                Proveedor proveedor = proveedorOptional.get();
                proveedorRepository.delete(proveedor);
                return ResponseEntity.ok(Message.MENSAJE_EXITOSO_ELIMINADO + "este proveedor");
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
