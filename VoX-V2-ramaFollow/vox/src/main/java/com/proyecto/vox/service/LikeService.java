package com.proyecto.vox.service;

import com.proyecto.vox.model.Like;
import com.proyecto.vox.model.Publicacion;
import com.proyecto.vox.model.Usuario;
import com.proyecto.vox.repository.LikeRepository;
import com.proyecto.vox.repository.PublicacionRepository;
//import com.proyecto.vox.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    /* 
    @Autowired
    private UsuarioRepository usuarioRepository;
    */

    // Método para dar like anónimo
    @Transactional
    public Like darLikeAnonimo(Long publicacionId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId).orElse(null);
        if (publicacion != null) {
            Like like = new Like();
            like.setAnonimoLike(true);
            like.setPublicacion(publicacion);
            return likeRepository.save(like);
        }
        return null;
    }

    // Método para dar like público
    @Transactional
    public Like darLikePublico(Long usuarioId, Long publicacionId) {
        Optional<Publicacion> publicacionOpt = publicacionRepository.findById(publicacionId);
        if (publicacionOpt.isEmpty()) {
            throw new IllegalArgumentException("La publicación no existe.");
        }
    
        Publicacion publicacion = publicacionOpt.get();
        Usuario usuario = new Usuario(); // Simula un usuario real
        usuario.setUserID(usuarioId);
    
        Like like = new Like();
        like.setPublicacion(publicacion);
        like.setUsuario(usuario);
    
        return likeRepository.save(like);
    }

    // Método para quitar un like
    @Transactional
    public void quitarLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}