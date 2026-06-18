package com.coleccion.coleccionArte.service;

import com.coleccion.coleccionArte.dto.LoginDTO;
import com.coleccion.coleccionArte.dto.UsuarioRegistroDTO;
import com.coleccion.coleccionArte.model.Usuario;
import com.coleccion.coleccionArte.repository.UsuarioRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    //logear usuario
    public Usuario autenticar(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByNombre(dto.getNombre()).orElse(null);

        if (usuario == null) return null;
        if (!usuario.getPassword().equals(dto.getPassword())) return null;

        return usuario;
    }
        // Listar todos los usuarios del sistema
        public List<Usuario> obtenerTodosLosUsuarios() {
            return usuarioRepository.findAll();
        }

    // Crear usuario
    public Usuario crearUsuario(UsuarioRegistroDTO usuarioRegistrar) {

        if (usuarioRepository.findByNombre(usuarioRegistrar.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese nombre");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioRegistrar.getNombre());
        usuario.setPassword(usuarioRegistrar.getPassword());
        return usuarioRepository.save(usuario);
    }



        // Eliminar un usuario
        public void eliminarUsuario(Long id) {
            if (usuarioRepository.existsById(id)) {
                usuarioRepository.deleteById(id);
            } else {
                throw new RuntimeException("No se puede eliminar. Usuario no encontrado.");
            }
        }

}
