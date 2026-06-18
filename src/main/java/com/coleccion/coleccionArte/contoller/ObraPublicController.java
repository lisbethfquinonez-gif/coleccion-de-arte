package com.coleccion.coleccionArte.contoller;

import com.coleccion.coleccionArte.model.Obra;
import com.coleccion.coleccionArte.service.ObraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/obras/publicas")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
public class ObraPublicController {

    private final ObraService obraService;

    @GetMapping
    public List<Obra> listarObrasPublicas() {
        return obraService.obtenerObrasGaleriaOrdenadas();
    }
}