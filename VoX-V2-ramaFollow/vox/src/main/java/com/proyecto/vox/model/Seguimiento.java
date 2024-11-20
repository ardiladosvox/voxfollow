package com.proyecto.vox.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JsonIgnore
    Usuario seguidor;

    @ManyToOne
    @JsonIgnore
    Usuario seguido;

    public Seguimiento(Usuario seguidor, Usuario seguido) {
        this.seguidor = seguidor;
        this.seguido = seguido;
    }

}