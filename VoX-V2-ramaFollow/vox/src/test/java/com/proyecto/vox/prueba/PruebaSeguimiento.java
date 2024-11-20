package com.proyecto.vox.prueba;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proyecto.vox.model.Seguimiento;
import com.proyecto.vox.model.Usuario;
import com.proyecto.vox.repository.SeguimientoRepository;
import com.proyecto.vox.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class PruebaSeguimiento {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SeguimientoRepository seguimientoRepository;

    @Test
    @Transactional
    void ejemploPrueba() {

        try {
         
            Usuario usuario1 = new Usuario();
            usuario1.setNombre("jaime");
            usuario1 = usuarioRepository.save(usuario1);
    
            Usuario usuario2 = new Usuario();
            usuario2.setNombre("juan");
            usuario2 = usuarioRepository.save(usuario2);

            Usuario usuario3 = new Usuario();
            usuario3.setNombre("julian");
            usuario3 = usuarioRepository.save(usuario3);

            Usuario usuario4 = new Usuario();
            usuario4.setNombre("jose");
            usuario4 = usuarioRepository.save(usuario4);

            usuarioRepository.flush();

            Seguimiento seguimiento = new Seguimiento(usuario1, usuario2);
            seguimiento = seguimientoRepository.save(seguimiento);
            usuario1.getSeguidos().add(seguimiento);
            usuario1 = usuarioRepository.save(usuario1);
            usuario2.getSeguidores().add(seguimiento);
            usuario2 = usuarioRepository.save(usuario2);

            seguimiento = new Seguimiento(usuario1, usuario3);
            seguimiento = seguimientoRepository.save(seguimiento);
            usuario1.getSeguidos().add(seguimiento);
            usuario1 = usuarioRepository.save(usuario1);
            usuario3.getSeguidores().add(seguimiento);
            usuario3 = usuarioRepository.save(usuario3);

            seguimientoRepository.flush();

            usuario1 = usuarioRepository.findById(usuario1.getUserID()).get();

            for (Seguimiento seg : usuario1.getSeguidos()) {
                String nombre = seg.getSeguido().getNombre();
                System.out.println(nombre);
            }

            usuario3 = usuarioRepository.findById(usuario3.getUserID()).get();

            for (Seguimiento seg : usuario3.getSeguidores()) {
                String nombre = seg.getSeguidor().getNombre();
                System.out.println(nombre);
            }


        } catch (Exception e) {

            System.out.println(e.getMessage());
            fail();

        }

    }


}
