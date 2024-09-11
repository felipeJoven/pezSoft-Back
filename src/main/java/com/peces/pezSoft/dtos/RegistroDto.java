package com.peces.pezSoft.dtos;

import lombok.Data;

@Data
public class RegistroDto {

    private String username;
    private String password;
    private String confirmPassword;
    private String nombre;
    private String apellido;
    private Long telefono;
    private String email;
    private int rolId;

}
