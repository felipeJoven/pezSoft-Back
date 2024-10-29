package com.peces.pezSoft.dtos;

import lombok.Data;

@Data
public class UsuarioDto {
    private Integer id;
    private String usuario;
    private String password;
    private String confirmPassword;
    private String nombre;
    private String apellido;
    private Long telefono;
    private String email;
    private int rolId;
    private String rolName;
}