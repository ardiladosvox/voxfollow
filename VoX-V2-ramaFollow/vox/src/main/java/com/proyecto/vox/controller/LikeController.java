package com.proyecto.vox.controller;

import com.proyecto.vox.model.Like;
import com.proyecto.vox.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    // Endpoint para dar like anónimo
    @PostMapping("/anonimo/{publicacionId}")
    public Like darLikeAnonimo(@PathVariable Long publicacionId) {
        return likeService.darLikeAnonimo(publicacionId);
    }

    // Endpoint para dar like público
    @PostMapping("/publico/{publicacionId}")
    public Like darLikePublico(@PathVariable Long publicacionId, @RequestParam Long usuarioId) {
        return likeService.darLikePublico(publicacionId, usuarioId);
    }

    // Endpoint para quitar like
    @DeleteMapping("/quitar/{likeId}")
    public void quitarLike(@PathVariable Long likeId) {
        likeService.quitarLike(likeId);
    }
}