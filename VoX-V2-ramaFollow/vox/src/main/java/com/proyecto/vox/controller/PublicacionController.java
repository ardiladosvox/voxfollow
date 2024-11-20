package com.proyecto.vox.controller;

import com.proyecto.vox.model.Publicacion;
import com.proyecto.vox.model.Usuario;
//import com.proyecto.vox.service.PublicacionService;
//import com.proyecto.vox.service.UsuarioService;
import com.proyecto.vox.repository.PublicacionRepository;
import com.proyecto.vox.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//import java.util.Date;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    /*
    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private UsuarioService usuarioService;
    */

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Endpoint para crear una publicación sin DTO
    @PostMapping("/crear")
    public Publicacion crearPublicacion(@RequestBody Publicacion publicacion, @RequestParam Long userId) {
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> 
            new RuntimeException("Usuario con ID " + userId + " no encontrado"));
        publicacion.setAutor(usuario);
        return publicacionRepository.save(publicacion);
    }

    // Endpoint para obtener todas las publicaciones de un usuario
    @GetMapping("/usuario/{usuarioId}")
        public List<Publicacion> getPublicacionesPorUsuario(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> 
        new RuntimeException("Usuario con ID " + usuarioId + " no encontrado"));
        return publicacionRepository.findByAutor(usuario);  // Devuelve las publicaciones de ese usuario
    }
}
