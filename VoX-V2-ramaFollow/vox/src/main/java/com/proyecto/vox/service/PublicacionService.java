package com.proyecto.vox.service;

import com.proyecto.vox.model.Publicacion;
import com.proyecto.vox.model.Usuario;
import com.proyecto.vox.repository.PublicacionRepository;
import com.proyecto.vox.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear una nueva publicación asociada a un usuario
    @Transactional
    public Publicacion crearPublicacion(Publicacion publicacion, Long userId) {
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> 
            new RuntimeException("Usuario con ID " + userId + " no encontrado"));
        publicacion.setAutor(usuario);
        System.out.println("Guardando publicación: " + publicacion);
        Publicacion saved = publicacionRepository.save(publicacion);
        System.out.println("Publicación guardada: " + saved);
        return saved;
    }

    // Obtener todas las publicaciones de un usuario
    @Transactional(readOnly = true)
    public List<Publicacion> obtenerPublicacionesPorUsuario(Long userId) {
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> 
            new RuntimeException("Usuario con ID " + userId + " no encontrado"));
        return publicacionRepository.findByAutor(usuario);  // Devuelve las publicaciones de ese usuario
    }

    // Guardar la publicación
    public Publicacion save(Publicacion publicacion) {
        return publicacionRepository.save(publicacion);  // Llama a save del repositorio
    }
}

