package com.proyecto.vox.repository;

import com.proyecto.vox.model.Usuario;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import proyecto.vox.test.repositoryTest.UsuarioRepositoryTest;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCorreo(String correo);
}

