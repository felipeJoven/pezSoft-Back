package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.model.Especie;
import com.peces.pezSoft.repository.EspecieRepository;
import com.peces.pezSoft.utils.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EspecieServiceImplTest {

    @Mock
    private EspecieRepository especieRepository;

    @InjectMocks
    private EspecieServiceImpl especieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarEspecies() {
    }

    // Listar por Id
    @Test
    void listarEspeciePorId_idExistente_deberiaRetornarEspecie() {
        // Arrange
        Integer id = 1;
        Especie especie = new Especie();
        especie.setId(id);
        especie.setEspecie("Mojarra");
        when(especieRepository.findById(id)).thenReturn(Optional.of(especie));
        // Act
        ResponseEntity<?> respuesta = especieService.listarEspeciePorId(id);
        // Assert
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(Optional.of(especie), respuesta.getBody());
    }

    @Test
    void listarEspeciePorId_deberiaRetornarNotFound_deberiaRetornarErrorServidor() {
        // Arrange
        Integer id = 999;
        when(especieRepository.findById(id)).thenReturn(Optional.empty());
        // Act
        ResponseEntity<?> respuesta = especieService.listarEspeciePorId(id);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode(), "El estado de la respuesta debe ser NOT_FOUND");
        assertEquals(Message.MENSAJE_ERROR_ID + id, respuesta.getBody(), "El mensaje de error no coincide");
    }

    @Test
    void agregarEspecie() {
    }

    @Test
    void actualizarEspecie() {
    }

    @Test
    void eliminarEspecie() {
    }
}