package com.proyecto.vox.repository;

import com.proyecto.vox.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.vox.model.Publicacion;
import java.util.List;

//import proyecto.vox.test.repositoryTest.LikeRepositoryTest;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPublicacion(Publicacion publicacion);
}