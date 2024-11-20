package com.proyecto.vox.controller;
import com.proyecto.vox.model.Usuario;
import com.proyecto.vox.service.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para registrar un nuevo usuario
    @PostMapping("/registro")
    public Usuario registro(@RequestBody Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    // Endpoint para hacer log in
    @PostMapping("/login")
    public Usuario login(@RequestParam String correo, @RequestParam String contraseña) {
        Usuario usuario = usuarioService.login(correo, contraseña);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos");
        }
        return usuario;
    }

    // Obtener perfil por ID
    @GetMapping("/{id}")
    public Usuario obtenerPerfil(@PathVariable Long id) {
        return usuarioService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    // Actualizar perfil parcialmente
    @PatchMapping("/{id}")
    public Usuario actualizarPerfilParcial(@PathVariable Long id, @RequestBody Usuario datosActualizados) {
        Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Solo actualiza los campos que no sean null o válidos
        if (datosActualizados.getNombre() != null) {
            usuario.setNombre(datosActualizados.getNombre());
        }
        if (datosActualizados.getEdad() > 0) { // Validación de edad positiva
            usuario.setEdad(datosActualizados.getEdad());
        }
        if (datosActualizados.getCarrera() != null) {
            usuario.setCarrera(datosActualizados.getCarrera());
        }
        if (datosActualizados.getSemestre() != null) {
            usuario.setSemestre(datosActualizados.getSemestre());
        }
        if (datosActualizados.getBiografia() != null) {
            usuario.setBiografia(datosActualizados.getBiografia());
        }

        return usuarioService.registrarUsuario(usuario);
    } // Guardar cambios

        @GetMapping("/{id}/seguidores")
    public List<Usuario> obtenerSeguidores(@PathVariable Long id) {
        return usuarioService.obtenerSeguidores(id);
    }

    // Endpoint para obtener los seguidos de un usuario
    @GetMapping("/{id}/seguidos")
    public List<Usuario> obtenerSeguidos(@PathVariable Long id) {
        return usuarioService.obtenerSeguidos(id);
    }

    @GetMapping
    public ResponseEntity<Usuario> buscarUsuarioPorCorreo(@RequestParam String correo) {
        Usuario usuario = usuarioService.buscarPorCorreo(correo);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/añadir")
    public ResponseEntity<Map<String, String>> añadirUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioAñadido = usuarioService.añadirUsuario(usuario);
        
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Usuario añadido: " + usuarioAñadido.getNombre());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}