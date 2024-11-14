package com.peces.pezSoft.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_identificacion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoIdentificacion extends Base {

    @Column(name = "tipo_identificacion", nullable = false)
    private String name;

}