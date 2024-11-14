package com.peces.pezSoft.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerador {

    //Método para generar el token por medio de la authentication
    public String generarToken(Authentication authentication, String usuario, String rol){
        String username = authentication.getName();
        Date tiempoActual = new Date();
        Date expiracionToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_TOKEN);

        //Linea para generar el token
        String token = Jwts.builder() //Construimos un token JWT llamado token
                .setSubject(username) //Acá establecemos el nombre de usuario que está iniciando sesión
                .claim("user", usuario)
                .claim("rol", rol)
                .setIssuedAt(new Date()) //Establecemos la fecha de emisión del token en el momento actual
                .setExpiration(expiracionToken) //Establecemos la fecha de caducidad del token
                .signWith(SignatureAlgorithm.HS512,ConstantesSeguridad.JWT_FIRMA) // Método para firmar nuestro token y así evitar la manipulación o modificación
                .compact(); //Este método finaliza la construcción del token y lo convierte en una cadena compacta
        return token;
    }

    //Método para extraer un Username apartir de un token
    public Object obtenerClaimsDeJwt(String token, String tipo){
        Claims claims = Jwts.parser() //El método parser se usa con el fin de analizar el token
                .setSigningKey(ConstantesSeguridad.JWT_FIRMA) //Establece la clave de firma, que se usa para verificar la firma del token
                .parseClaimsJws(token) //Se usa para verificar la firma del token, apartir del String "token"
                .getBody(); //Obtenemos el Claims(cuerpo) ya verificado del token el cual contendrá la información de nombre de usuario, fecha de espiración y firma del token
        if (tipo.equals("subject")) {
            return claims.getSubject();
        } else {
            return claims.get(tipo);
        }
    }

    //Método para validar el token
    public  Boolean validarToken(String token){
        try {
            //Validación del token por medio de la firma que contiene el String token(token)
            Jwts.parser().setSigningKey(ConstantesSeguridad.JWT_FIRMA).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("Jwt ha expirado o está incorrecto");
        }
    }

}