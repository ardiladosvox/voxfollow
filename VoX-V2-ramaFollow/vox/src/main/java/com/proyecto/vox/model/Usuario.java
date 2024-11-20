package com.proyecto.vox.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data; 

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String nombre;

    @Column(unique=true)
    private String correo;

    private String contraseña;
    private int edad;
    private String carrera;
    private String semestre;
    private String biografia;

    //

    @OneToMany(mappedBy = "autor")
    @JsonIgnore
    private List<Publicacion> publicaciones = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy="seguidor", fetch = FetchType.LAZY)
    List<Seguimiento> seguidos = new ArrayList<>();

    @OneToMany(mappedBy = "seguido", fetch = FetchType.LAZY)
    List<Seguimiento> seguidores = new ArrayList<>();


};