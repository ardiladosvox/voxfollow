package com.proyecto.vox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /*Se renderiza cada pantalla desde templates*/
    @GetMapping("/login")
    public String loginPage() {
        return "login"; 
    }

    @GetMapping("/registro")
    public String registroPage() {
        return "registro"; 
    }

    @GetMapping("/principal")
    public String principalPage() {
        return "principal"; 
    }

    /*Se muestra la pantalla de inicio*/
    @GetMapping("/")
    public String index() {
        return "index";  
    }

    @GetMapping("/perfil")
    public String perfilPage() {
        return "perfil";
    }

    @GetMapping("/editar-perfil")
    public String editarperfilPage() {
        return "editar-perfil";
    }
}