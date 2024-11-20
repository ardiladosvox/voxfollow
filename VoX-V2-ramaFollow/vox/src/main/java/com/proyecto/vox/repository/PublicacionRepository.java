package com.proyecto.vox.repository;

import com.proyecto.vox.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.proyecto.vox.model.Usuario;

//import proyecto.vox.test.repositoryTest.PublicacionRepositoryTest;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findByAutor(Usuario autor);  
}