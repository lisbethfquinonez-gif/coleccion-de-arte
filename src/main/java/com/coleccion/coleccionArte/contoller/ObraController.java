package com.coleccion.coleccionArte.contoller;

import com.coleccion.coleccionArte.dto.ObraRequestDTO;
import com.coleccion.coleccionArte.model.Obra;
import com.coleccion.coleccionArte.service.ObraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ObraController {
    private final ObraService obraService;

    public ObraController(ObraService obraService) {
        this.obraService = obraService;
    }
    @PostMapping
    public ResponseEntity<Obra> crearObra(
            @Valid @RequestBody ObraRequestDTO dto) {

        Obra obraGuardada = obraService.guardarObra(dto);

        return new ResponseEntity<>(
                obraGuardada,
                HttpStatus.CREATED
        );
    }
    /*
    @PostMapping
    public ResponseEntity<String> crearObra(@RequestBody ObraRequestDTO requestDTO) {
        try {
            // Ejecutamos la lógica del servicio y obtenemos la obra guardada
            Obra obraCreada = obraService.guardarObra(requestDTO);

            // Creamos el mensaje de texto usando los datos reales de la obra
            String mensajeExito = "La obra '" + obraCreada.getTitulo() + "' se ha creado correctamente en la colección.";

            // Respondemos con el texto y un estado 201 Created (que es el correcto para creaciones)
            return new ResponseEntity<>(mensajeExito, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Si las validaciones del servicio fallan (título o autor vacíos)
            // El controlador atrapa el error y responde con un código 400 Bad Request y el porqué del fallo
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/
    // 2. OBTENER GALERÍA COMPLETA (GET http://localhost:8080/api/obras)
    @GetMapping
    public ResponseEntity<List<Obra>> listarGaleria() {
        List<Obra> galeria = obraService.obtenerObrasGaleria();
        return new ResponseEntity<>(galeria, HttpStatus.OK);
    }
    //Galeria ordenada alfabeticamente asendente
    public ResponseEntity<List<Obra>> listarGaleriaOrdenada(){
        List<Obra> galeriaOrdenada = obraService.obtenerObrasGaleriaOrdenadas();
        return new ResponseEntity<>(galeriaOrdenada, HttpStatus.OK);
    }

    // 3. BUSCAR CON FILTRO (GET http://localhost:8080/api/obras/buscar?texto=garrido)
    @GetMapping("/buscar")
    public ResponseEntity<List<Obra>> filtrarObras(@RequestParam String texto) {
        List<Obra> resultados = obraService.buscarObras(texto);
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }

    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<String> modificarObra(@PathVariable Long id, @RequestBody ObraRequestDTO requestDTO) {
        try {
            Obra obraEditada = obraService.actualizarObra(id, requestDTO);
            return new ResponseEntity<>("La obra '" + obraEditada.getTitulo() + "' se ha actualizado con éxito.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/{id}/mostrar")
    public ResponseEntity<String> mostrarObra(
            @PathVariable Long id) {

        obraService.visibilidaObra(id);

        return ResponseEntity.ok("La obra vuelve a estar visible.");
    }


    // 5. OCULTAR/BORRAR LÓGICO (DELETE http://localhost:8080/api/obras/5)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarObra(@PathVariable Long id) {
        try {
            obraService.ocultarObra(id);
            return new ResponseEntity<>("La obra ha sido retirada de la galería pública.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
