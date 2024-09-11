package com.peces.pezSoft.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_alimento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoAlimento extends Base {
    
    @Column(name = "tipo_alimento", nullable = false)
    private String tipoAlimento;

}
