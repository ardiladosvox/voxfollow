    package com.proyecto.vox.model;

    import java.util.ArrayList;
    import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.ManyToOne;
    import jakarta.persistence.OneToMany;
    import lombok.Data;
    import jakarta.persistence.FetchType;
    import jakarta.persistence.JoinColumn;
   // import jakarta.persistence.ManyToOne;
    import jakarta.persistence.CascadeType; 

    @Data
    @Entity
    public class Publicacion {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long pubID;

        private String descripcion;
        private Date fecha;
        private Boolean anonimo;

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        @JoinColumn(name = "usuario_id")
        private Usuario autor;

        @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonIgnore
        private List<Like> likes = new ArrayList<>();

        public Long getId() {
            return pubID;
        }

        public void setId(Long id) {
            this.pubID = id;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public void setAutor(Usuario user) {
            this.autor = user;
        }

        public Usuario getAutor() {
            return this.autor;
        }

         /*Se sobreescribe el método toString para hacer las pruebas*/
        @Override
        public String toString() {
            return "Publicacion(pubID=" + pubID + ", descripcion=" + descripcion + ", fecha=" + fecha + 
                ", anonimo=" + anonimo + ", autor=" + (autor != null ? autor.getNombre() : "null") + 
                ", likes=" + likes.size() + ")";
        }

        public void setFecha(Date fecha) {
            if (fecha == null) {
                this.fecha = new Date();
            } else {
                this.fecha = fecha;
            }
        }
        
    };
