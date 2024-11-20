package com.proyecto.vox.service;

import com.proyecto.vox.model.Seguimiento;
import com.proyecto.vox.model.Usuario;
import com.proyecto.vox.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario login(String correo, String contraseña) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            return usuario;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long userId) {
        return usuarioRepository.findById(userId);
    }

   // Método para obtener los seguidores de un usuario
   public List<Usuario> obtenerSeguidores(Long userId) {
    Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    List<Usuario> seguidores = new ArrayList<>();
    for (Seguimiento seguimiento : usuario.getSeguidores()) {
        seguidores.add(seguimiento.getSeguidor());
    }
    return seguidores;
}

// Método para obtener los seguidos de un usuario
public List<Usuario> obtenerSeguidos(Long userId) {
    Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    List<Usuario> seguidos = new ArrayList<>();
    for (Seguimiento seguimiento : usuario.getSeguidos()) {
        seguidos.add(seguimiento.getSeguido());
    }
    return seguidos;
}

public Usuario buscarPorCorreo(String correo) {
    return usuarioRepository.findByCorreo(correo);
}

public Usuario añadirUsuario(Usuario usuario) {
    // Aquí podrías aplicar lógica para verificar si ya existe o cualquier otra regla
    return usuarioRepository.save(usuario);
}


    
}
