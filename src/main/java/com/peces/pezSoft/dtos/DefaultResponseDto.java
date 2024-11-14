package com.peces.pezSoft.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponseDto {

    private String rol; // Campo para almacenar el rol
    private String usuario; // Campo para almacenar el usuario

    // Constructor que acepta solo un mensaje
    public DefaultResponseDto(String message) {
        this.message = message;
    }
    private String message;
}