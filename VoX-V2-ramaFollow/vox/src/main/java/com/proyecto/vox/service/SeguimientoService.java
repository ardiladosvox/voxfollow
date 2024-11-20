package com.proyecto.vox.service;

//import com.proyecto.vox.model.Publicacion;
import com.proyecto.vox.model.Seguimiento;
import com.proyecto.vox.model.Usuario;
//import com.proyecto.vox.repository.PublicacionRepository;
import com.proyecto.vox.repository.SeguimientoRepository;
import com.proyecto.vox.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeguimientoService {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Permite a un usuario seguir a otro usuario.
     *
     * @param seguidorId ID del usuario que sigue.
     * @param seguidoId ID del usuario a seguir.
     * @return true si el seguimiento fue exitoso, false si ya existe el seguimiento.
     */
    

     public boolean seguirUsuario(Long seguidorId, Long seguidoId) {
        Optional<Usuario> seguidorOpt = usuarioRepository.findById(seguidorId);
        Optional<Usuario> seguidoOpt = usuarioRepository.findById(seguidoId);
    
        if (seguidorOpt.isPresent() && seguidoOpt.isPresent()) {
            Usuario seguidor = seguidorOpt.get();
            Usuario seguido = seguidoOpt.get();
    
            // Verificar si ya existe el seguimiento
            if (seguimientoRepository.existsBySeguidorAndSeguido(seguidor, seguido)) {
                return false;
            }
    
            // Crear y guardar el nuevo seguimiento
            Seguimiento seguimiento = new Seguimiento(seguidor, seguido);
            seguimientoRepository.save(seguimiento);
            return true;
        }
    
        return false; // Retornar false si alguno de los usuarios no existe
    }
    

    /**
     * Permite a un usuario dejar de seguir a otro usuario.
     *
     * @param seguidorId ID del usuario que sigue.
     * @param seguidoId ID del usuario al que se desea dejar de seguir.
     * @return true si el seguimiento fue eliminado exitosamente, false si no existía.
     */
    public boolean dejarDeSeguirUsuario(Long seguidorId, Long seguidoId) {
        Optional<Usuario> seguidorOpt = usuarioRepository.findById(seguidorId);
        Optional<Usuario> seguidoOpt = usuarioRepository.findById(seguidoId);

        if (seguidorOpt.isPresent() && seguidoOpt.isPresent()) {
            Usuario seguidor = seguidorOpt.get();
            Usuario seguido = seguidoOpt.get();

            // Buscar el seguimiento existente
            Optional<Seguimiento> seguimientoOpt = seguimientoRepository.findBySeguidorAndSeguido(seguidor, seguido);
            if (seguimientoOpt.isPresent()) {
                seguimientoRepository.delete(seguimientoOpt.get());
                return true;
            }
        }

        return false; // Retornar false si el seguimiento no existe o alguno de los usuarios no existe
    }
}
