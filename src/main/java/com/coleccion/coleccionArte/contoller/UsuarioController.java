package com.coleccion.coleccionArte.contoller;
import org.springframework.ui.Model;
import com.coleccion.coleccionArte.dto.UsuarioRegistroDTO;
import com.coleccion.coleccionArte.model.Usuario;
import com.coleccion.coleccionArte.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/registrarAdmin")
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody UsuarioRegistroDTO usuario) {
        Usuario user = usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerTodosLosUsuarios());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente con ID: " + id);
    }
}
