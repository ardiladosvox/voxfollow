package com.proyecto.vox.serviceTest;

import com.proyecto.vox.model.Publicacion;
import com.proyecto.vox.model.Usuario;
import com.proyecto.vox.repository.PublicacionRepository;
import com.proyecto.vox.repository.UsuarioRepository;
import com.proyecto.vox.service.PublicacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

class PublicacionServiceTest {

    @InjectMocks
    private PublicacionService publicacionService;

    @Mock
    private PublicacionRepository publicacionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private Publicacion publicacion1;
    private Publicacion publicacion2;

    @BeforeEach
    void setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this);

        // Crear el usuario de prueba
        usuario = new Usuario();
        usuario.setUserID(1L);
        usuario.setNombre("Usuario Test");

        // Crear publicaciones de prueba
        publicacion1 = new Publicacion();
        publicacion1.setPubID(1L);
        publicacion1.setDescripcion("Publicación 1");
        publicacion1.setAutor(usuario);  // Asociar al usuario (como autor)

        publicacion2 = new Publicacion();
        publicacion2.setPubID(2L);
        publicacion2.setDescripcion("Publicación 2");
        publicacion2.setAutor(usuario);  // Asociar al usuario (como autor)

        // Configurar el mock para que devuelva las publicaciones del autor
        when(publicacionRepository.findByAutor(usuario)).thenReturn(java.util.List.of(publicacion1, publicacion2));
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));
    }

    @Test
    void testObtenerPublicacionesPorUsuario() {
        Long userId = 1L;

        // Crear usuario mockeado
        Usuario usuario = new Usuario();
        usuario.setUserID(userId);

        // Crear publicaciones de prueba
        Publicacion publicacion1 = new Publicacion();
        publicacion1.setDescripcion("Publicación 1");
        publicacion1.setAutor(usuario);  // Asocia con el autor

        Publicacion publicacion2 = new Publicacion();
        publicacion2.setDescripcion("Publicación 2");
        publicacion2.setAutor(usuario);  // Asocia con el autor

        List<Publicacion> publicaciones = List.of(publicacion1, publicacion2);

        // Mocking de los repositorios
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario)); // Mock para el repositorio de Usuario
        when(publicacionRepository.findByAutor(usuario)).thenReturn(publicaciones); // Mock para el repositorio de Publicaciones

        // Llamada al servicio
        List<Publicacion> foundPublicaciones = publicacionService.obtenerPublicacionesPorUsuario(userId);

        // Validación de los resultados
        assertNotNull(foundPublicaciones);
        assertEquals(2, foundPublicaciones.size());
        assertEquals(publicacion1.getDescripcion(), foundPublicaciones.get(0).getDescripcion());
        assertEquals(publicacion2.getDescripcion(), foundPublicaciones.get(1).getDescripcion());

        // Verificación de las interacciones con los mocks
        verify(usuarioRepository, times(1)).findById(userId);
        verify(publicacionRepository, times(1)).findByAutor(usuario);
    }

}
