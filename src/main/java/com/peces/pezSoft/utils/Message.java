package com.peces.pezSoft.utils;

public class Message {

    // Errores
    public static final String MENSAJE_ERROR_SERVIDOR = "Error en el servidor: ";
    public static final String MENSAJE_ERROR_LOGIN = "Credenciales invalidas";
    public static final String MENSAJE_ERROR_EMAIL = "El correo electrónico ya existe, intenta con otro";
    public static final String MENSAJE_ERROR_EMAIL_VACIO = "Debe ingresar un correo electrónico!";
    public static final String MENSAJE_ERROR_PASSWORD = "Las contraseñas no coinciden";
    public static final String MENSAJE_ERROR_SOLICITUD = "Error al procesar la solicitud: ";
    public static final String MENSAJE_ERROR_TELEFONO = "El número de teléfono debe tener exactamente 10 dígitos";
    public static final String MENSAJE_ERROR_EXISTE = "Ya existe %s en la base de datos!";

    // No existe
    public static final String MENSAJE_ERROR_ROL = "El rol no existe";
    public static final String MENSAJE_ERROR_ROL_ADMIN = "El rol Admin no se encontró en la base de datos!";
    public static final String MENSAJE_ERROR_SIN_USUARIOS = "No existe ningún usuario en la base de datos!";
    public static final String MENSAJE_ERROR_VER = "No se encontraron ";
    public static final String MENSAJE_ERROR_ID = "No se encontró el Id: ";
    public static final String MENSAJE_ERROR_OBLIGATORIO = "Este campo no puede ir vacío!";
    public static final String MENSAJE_ERROR_PECES = "El número de peces %s no puede ser mayor a los existentes en el lote!";
    public static final String MENSAJE_ERROR_PECES_0 = "El valor de peces %s debe ser mayor a 0 para crear este registro!";

    // Autorizado
    public static final String MENSAJE_ERROR_AUTORIZADO = "Usuario con rol '%s' no autorizado ";
    public static final String MENSAJE_ERROR_AUTORIZADO_ADMIN = "Solamente puede acceder un %s";

    // Exitosos
    public static final String MENSAJE_EXITOSO_REGISTRO = "Registro de %s exitoso";
    public static final String MENSAJE_EXITOSO_GUARDADO = "Se ha guardado exitosamente ";
    public static final String MENSAJE_EXITOSO_ACTUALIZADO = "Se ha actualizado exitosamente ";
    public static final String MENSAJE_EXITOSO_ELIMINADO = "Se ha eliminado exitosamente ";
}
