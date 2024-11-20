package com.proyecto.vox.repository;

import com.proyecto.vox.model.Seguimiento;
import com.proyecto.vox.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;



@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {
    boolean existsBySeguidorAndSeguido(Usuario seguidor, Usuario seguido);

    Optional<Seguimiento> findBySeguidorAndSeguido(Usuario seguidor, Usuario seguido);
}

