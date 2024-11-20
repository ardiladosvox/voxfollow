
package com.proyecto.vox.model;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.ManyToOne;
    import lombok.Data;
    import jakarta.persistence.*;

//import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.proyecto.vox.model.*;

    @Data
    @Entity
    @Table(name = "likes")
    public class Like {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long likeID;

        private Boolean anonimoLike;

        /*Se hace el join column para establecer relación con la de 
        //publicaciones*/
        @ManyToOne
        @JsonIgnore
        @JoinColumn(name = "publicacion_pubid")
        private Publicacion publicacion;

        @ManyToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        @JoinColumn(name = "usuario_id")
        private Usuario usuario;

        public void setAnonimoLike(boolean anonimoLike) {
            this.anonimoLike = anonimoLike;
        }

        public void setPublicacion(Publicacion publicacion) {
            this.publicacion = publicacion;
        }

        public Long getId() {
            return likeID;
        }

        public void setId(Long id) {
            this.likeID = id;
        }

        public boolean isAnonimoLike() {
            return anonimoLike;
        }

        public Publicacion getPublicacion() {
            return publicacion;
        }

        public Boolean getAnonimoLike() {
            return anonimoLike;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }
    };
