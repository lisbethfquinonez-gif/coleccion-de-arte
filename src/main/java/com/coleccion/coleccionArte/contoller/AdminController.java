package com.coleccion.coleccionArte.contoller;

import com.coleccion.coleccionArte.dto.LoginDTO;
import com.coleccion.coleccionArte.model.Usuario;
import com.coleccion.coleccionArte.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.coleccion.coleccionArte.dto.UsuarioRegistroDTO;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "login"; // login.html en templates
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginDTO") LoginDTO dto,
                        HttpSession session,
                        Model model) {

        Usuario usuario = usuarioService.autenticar(dto);

        if (usuario == null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }

        session.setAttribute("usuario", usuario);
        return "redirect:/admin/panel";
    }

    @GetMapping("/panel")
    public String panel(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/admin/login";
        }
        return "admin-panel"; // admin-panel.html en templates
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuarioRegistroDTO", new UsuarioRegistroDTO());
        return "registro-admin";
    }

    @PostMapping("/registro")
    public String registrarAdmin(@ModelAttribute("usuarioRegistroDTO") UsuarioRegistroDTO dto,
                                 Model model) {
        try {
            usuarioService.crearUsuario(dto);
            model.addAttribute("mensaje", "Administrador registrado correctamente. Ahora puedes iniciar sesión.");
            model.addAttribute("loginDTO", new LoginDTO());
            return "login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "registro-admin";
        }
    }
}
