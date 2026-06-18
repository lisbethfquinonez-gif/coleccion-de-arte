package com.coleccion.coleccionArte.contoller;

import com.coleccion.coleccionArte.dto.ObraRequestDTO;
import com.coleccion.coleccionArte.model.Obra;
import com.coleccion.coleccionArte.service.ObraService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin/obras")
@RequiredArgsConstructor
public class ObraAdminController {

    private final ObraService obraService;

    @GetMapping
    public String listar(HttpSession session, Model model) {

        if (session.getAttribute("usuario") == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("obras", obraService.obtenerTodas());

        return "obras/lista";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(HttpSession session, Model model) {

        if (session.getAttribute("usuario") == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("obra", new ObraRequestDTO());

        return "obras/formulario";
    }

    /*@PostMapping("/guardar")
    public String guardarObra(@ModelAttribute("obra") ObraRequestDTO dto,
                              HttpSession session) {

        if (session.getAttribute("usuario") == null) {
            return "redirect:/admin/login";
        }

        obraService.guardarObra(dto);

        return "redirect:/admin/obras";
    }*/
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id,
                                          HttpSession session,
                                          Model model) {

        if (session.getAttribute("usuario") == null) {
            return "redirect:/admin/login";
        }

        Obra obra = obraService.obtenerPorId(id);

        model.addAttribute("obra", obra);

        return "obras/editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarObra(@PathVariable Long id,
                                 @ModelAttribute ObraRequestDTO dto,
                                 @RequestParam(value = "imagen", required = false) MultipartFile imagen,
                                 HttpSession session) throws IOException {

        if (session.getAttribute("usuario") == null) {
            return "redirect:/admin/login";
        }

        if (imagen != null && !imagen.isEmpty()) {

            String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();

            String rutaCarpeta =
                    System.getProperty("user.dir") + "/../../obrasSubidas/";

            File carpeta = new File(rutaCarpeta);

            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File archivoDestino = new File(rutaCarpeta + nombreArchivo);

            imagen.transferTo(archivoDestino);

            dto.setImagenUrl("/obrasSubidas/" + nombreArchivo);

        } else {
            Obra obraActual = obraService.obtenerPorId(id);
            dto.setImagenUrl(obraActual.getImagenUrl());
        }

        obraService.actualizarObra(id, dto);

        return "redirect:/admin/obras";
    }

    @GetMapping("/ocultar/{id}")
    public String ocultarObra(@PathVariable Long id,
                              HttpSession session) {

        if (session.getAttribute("usuario") == null) {
            return "redirect:/admin/login";
        }

        obraService.ocultarObra(id);

        return "redirect:/admin/obras";
    }
    @PostMapping("/guardar")
    public String guardarObra(@ModelAttribute("obra") ObraRequestDTO dto,
                              @RequestParam("imagen") MultipartFile imagen,
                              HttpSession session) throws IOException {

        if (session.getAttribute("usuario") == null) {
            return "redirect:/admin/login";
        }

        if (!imagen.isEmpty()) {

            String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();

            String rutaCarpeta =
                    System.getProperty("user.dir") + "/../../obrasSubidas/";
            System.out.println(System.getProperty("user.dir"));
            File carpeta = new File(rutaCarpeta);

            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File archivoDestino = new File(rutaCarpeta + nombreArchivo);

            imagen.transferTo(archivoDestino);

            dto.setImagenUrl("/obrasSubidas/" + nombreArchivo);
        }

        obraService.guardarObra(dto);

        return "redirect:/admin/obras";
    }
    @GetMapping("/mostrar/{id}")
    public String mostrarObra(@PathVariable Long id,
                              HttpSession session) {

        if (session.getAttribute("usuario") == null) {
            return "redirect:/admin/login";
        }

        obraService.visibilidaObra(id);

        return "redirect:/admin/obras";
    }
    @GetMapping("/eliminar-definitivo/{id}")
    public String eliminarDefinitivo(@PathVariable Long id,
                                     HttpSession session) {

        if (session.getAttribute("usuario") == null) {
            return "redirect:/admin/login";
        }

        obraService.eliminarDefinitivamente(id);

        return "redirect:/admin/obras";
    }
}
