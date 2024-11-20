package com.proyecto.vox.serviceTest;

import com.proyecto.vox.model.Like;
import com.proyecto.vox.model.Publicacion;
import com.proyecto.vox.model.Usuario;
import com.proyecto.vox.repository.LikeRepository;
import com.proyecto.vox.repository.PublicacionRepository;
import com.proyecto.vox.repository.UsuarioRepository;
import com.proyecto.vox.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class LikeServiceTest {
    /*

    @Autowired
    private LikeService likeService;

    @MockBean
    private LikeRepository likeRepository;

    @MockBean
    private PublicacionRepository publicacionRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;

    private Publicacion publicacion;

    @BeforeEach
    void setUp() {
        // Se preparan los datos antes de la prueba
        publicacion = new Publicacion();
        publicacion.setDescripcion("Publicación de prueba");
        publicacion.setPubID(1L);

        // Simulamos que publicacionRepository devuelve una publicación con el id 1
        when(publicacionRepository.findById(1L)).thenReturn(java.util.Optional.of(publicacion));

        // Simulamos que el likeRepository guarda y devuelve un Like
        when(likeRepository.save(any(Like.class))).thenAnswer(invocation -> {
            Like like = invocation.getArgument(0);
            like.setId(1L); // Simulamos que el Like recibido tiene id 1
            return like;
        });

              // Simulamos un usuario
              Usuario usuario = new Usuario(); // Crear el usuario simulado
            usuario.setUserID(2L);
            usuario.setNombre("Usuario de prueba");

              // Simulamos que usuarioRepository devuelve un usuario con el id 2
              when(usuarioRepository.findById(2L)).thenReturn(java.util.Optional.of(usuario)); // Añadir esta línea
    }

    @Test
    void testDarLikeAnonimo() {
        Long pubId = 1L; // Se asigna el ID de la publicación

        // Simula la creación de un like anónimo
        Like like = likeService.darLikeAnonimo(pubId);

        assertNotNull(like); // Verifica que el like no es null
        assertEquals(pubId, like.getPublicacion().getPubID()); // Verifica que el like está asociado con la publicación correcta

        // Verifica que el like fue guardado en el repositorio
        verify(likeRepository, times(1)).save(any(Like.class)); // Verifica que se guardó una vez
    }

    @Test
    void testDarLikePublico() {
        Long pubId = 1L; // Se asigna el ID de la publicación
        Long userId = 2L; // Simula un usuario que da el like

        // Simula la creación de un like público
        Like like = likeService.darLikePublico(userId, pubId);

        assertNotNull(like); // Verifica que el like no es null
        assertEquals(pubId, like.getPublicacion().getPubID()); // Verifica que el like está asociado con la publicación correcta

        // Verifica que el like fue guardado en el repositorio
        verify(likeRepository, times(1)).save(any(Like.class)); // Verifica que se guardó una vez
    }

    @Test
    void testQuitarLike() {
        Long publicacionId = publicacion.getPubID(); 
        Long userId = 2L; // Simula un usuario que da el like

        // Simula que el usuario da un like público
        Like savedLike = likeService.darLikePublico(userId, publicacionId);
        Long likeId = savedLike.getId();

        // Simula la eliminación del like
        likeService.quitarLike(likeId);

        // Verifica que el like se eliminó de la base de datos
        verify(likeRepository, times(1)).deleteById(likeId); // Verifica que se eliminó una vez
    }
        */
}
