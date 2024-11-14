package com.peces.pezSoft.service.impl;

import com.peces.pezSoft.model.Rol;
import com.peces.pezSoft.repository.RolRepository;
import com.peces.pezSoft.service.RolService;
import com.peces.pezSoft.utils.Message;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RolServiceImpl implements RolService {

    private RolRepository rolRepository;

    @Override
    public ResponseEntity<?> listarRoles() {
        try {
            List<Rol> roles = rolRepository.findAll();
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Message.MENSAJE_ERROR_SERVIDOR + e.getMessage());
        }
    }
}