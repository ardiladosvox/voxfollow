package com.proyecto.vox.serviceTest;  

import com.proyecto.vox.service.UsuarioService;
import com.proyecto.vox.repository.UsuarioRepository;
import com.proyecto.vox.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; 
@SpringBootTest
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testRegistrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@correo.com");
        usuario.setContraseña("12345");
        usuario.setNombre("Juan");

        Usuario savedUsuario = usuarioService.registrarUsuario(usuario);
        assertNotNull(savedUsuario);
        assertEquals(usuario.getCorreo(), savedUsuario.getCorreo());

        /*Se verifica que realmente se encuentre guardado en la base de datos*/
        Usuario foundUsuario = usuarioRepository.findByCorreo(usuario.getCorreo());
        assertNotNull(foundUsuario);
        assertEquals(usuario.getCorreo(), foundUsuario.getCorreo());
    }
}