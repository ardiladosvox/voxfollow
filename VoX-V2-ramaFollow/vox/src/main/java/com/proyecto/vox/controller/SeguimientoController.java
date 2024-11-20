package com.proyecto.vox.controller;

//import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyecto.vox.model.Seguimiento;
import com.proyecto.vox.model.Usuario;
import com.proyecto.vox.repository.UsuarioRepository;
import com.proyecto.vox.service.SeguimientoService;
import com.proyecto.vox.repository.SeguimientoRepository;


@RestController
@RequestMapping("/api/seguimiento")
public class SeguimientoController {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SeguimientoService seguimientoService;


    // Seguir a un usuario
    @PostMapping("/follow/{userId}")
public ResponseEntity<Map<String, Object>> followUser(
        @PathVariable Long userId,
        @RequestParam Long seguidorId) {

    Map<String, Object> response = new HashMap<>();
    try {
        // El servicio ya se inyecta automáticamente
        boolean exito = seguimientoService.seguirUsuario(seguidorId, userId);  // Cambio aquí (seguidorId, userId)

        if (exito) {
            response.put("success", true);
            response.put("message", "Usuario seguido exitosamente.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Ya estás siguiendo a este usuario.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    } catch (Exception e) {
        response.put("success", false);
        response.put("message", "Error al seguir al usuario: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}


}

/*
@PostMapping("/unfollow/{userId}")
public ResponseEntity<Map<String, Object>> unfollowUser(
        @PathVariable Long userId,
        @RequestParam Long seguidorId) {

    Map<String, Object> response = new HashMap<>();
    try {
        boolean exito = SeguimientoService.dejarDeSeguirUsuario(seguidorId, userId);

        if (exito) {
            response.put("success", true);
            response.put("message", "Has dejado de seguir al usuario.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "No seguías a este usuario.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    } catch (Exception e) {
        response.put("success", false);
        response.put("message", "Error al dejar de seguir al usuario: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
*/