package com.fish.fishNet.utils;

public class Message {

    // Errores
    public static final String MENSAJE_ERROR_SERVIDOR = "Error en el servidor: ";
    public static final String MENSAJE_ERROR_LOGIN = "Credenciales invalidas";
    public static final String MENSAJE_ERROR_EMAIL = "El correo electrónico ya existe, intenta con otro";
    public static final String MENSAJE_ERROR_EMAIL_VACIO = "Debe ingresar un correo electrónico";
    public static final String MENSAJE_ERROR_PASSWORD = "Las contraseñas no coinciden";
    public static final String MENSAJE_ERROR_SOLICITUD = "Error al procesar la solicitud: ";
    public static final String MENSAJE_ERROR_TELEFONO = "El número de teléfono debe tener exactamente 10 dígitos";
    public static final String MENSAJE_ERROR_CREAR_USUARIO = "Solo los administradores pueden agregar usuarios";

    // No existe
    public static final String MENSAJE_ERROR_ROL = "El rol no existe";
    public static final String MENSAJE_ERROR_SELECCIONAR_ROL = "No ha seleccionado un rol!";
    public static final String MENSAJE_ERROR_ROL_ADMIN = "El rol Admin no se encontró en la base de datos!";
    public static final String MENSAJE_ERROR_SIN_USUARIOS = "No existe ningún usuario en la base de datos!";
    public static final String MENSAJE_ERROR_USUARIO_ID = "No se encontró un usuario con el Id: ";
    public static final String MENSAJE_ERROR_DATO_ID = "No se encontró el Id especificado";


    // Ver
    public static final String MENSAJE_ERROR_AUTOIZADO_VER = "Solo los %s pueden ver un %s";
    public static final String MENSAJE_ERROR_AUTORIZADO = "Usuario con rol '%s' no autorizado ";
    public static final String MENSAJE_ERROR_AUTORIZACION = "Usuario no autorizado ";
    public static final String MENSAJE_ERROR_AUTORIZADO_ADMIN = "Solamente puede acceder un %s";
    public static final String MENSAJE_ERROR_AUTORIZADO_ALCALDE = "Solamente puede acceder un %s";

    // Especie
    public static final String MENSAJE_ERROR_ESPECIE = "La especie ya existe";
    public static final String MENSAJE_ERROR_ESPECIE_ID = "No se encontró la especie con ese ID: .";

    // Unidad Productiva
    public static final String MENSAJE_ERROR_UNIDAD = "Ya existe una unidad productiva con el mismo nombre o coordenadas.";
    public static final String MENSAJE_ERROR_UNIDAD_ID = "No se encontró la unidad productiva con ese ID: .";

    // Exitosos
    public static final String MENSAJE_EXITOSO_REGISTRO = "Registro de %s exitoso";
    public static final String MENSAJE_EXITOSO_GUARDADO = "Se ha guardado exitosamente ";
    public static final String MENSAJE_EXITOSO_ACTUALIZADO = "Usuario actualizado exitosamente";
    public static final String MENSAJE_EXITOSO_ELIMINADO = "Usuario eliminado exitosamente";
    public static final String MENSAJE_EXITOSO_RECORDATORIO_ACTUALIZAR = "Recordatorio actualizado correctamente";
    public static final String MENSAJE_EXITOSO_INQUIETUD_CREADA = "Inquietud creada exitosamente";
    public static final String MENSAJE_EXITOSO_INQUIETUD_ACTUALIZADA = "Se ha actualizado la inquietud";
    public static final String MENSAJE_EXITOSO_INQUIETUD_ELIMINADA = "Inquietud eliminada exitosamente";
    public static final String MENSAJE_EXITOSO_NOTIFICACION_ELIMINADA = "Notificación eliminada exitosamente";
    public static final String MENSAJE_EXITOSO_FACTURA_ACTUALIZADA = "Factura actualizada exitosamente";
    public static final String MENSAJE_EXITOSO_FACTURA_ELIMINADA = "Factura eliminada exitosamente";
}
