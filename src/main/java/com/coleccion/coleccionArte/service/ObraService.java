package com.coleccion.coleccionArte.service;

import com.coleccion.coleccionArte.dto.ObraRequestDTO;
import com.coleccion.coleccionArte.model.Obra;
import com.coleccion.coleccionArte.repository.ObraRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ObraService {
    private final ObraRepository obraRepository;

    public ObraService(ObraRepository obraRepository) {
        this.obraRepository = obraRepository;
    }
    //guardar obra
    public Obra guardarObra(ObraRequestDTO dto) {

        Obra obra = new Obra();

        obra.setTitulo(dto.getTitulo());
        obra.setAutor(dto.getAutor());
        obra.setAnio(dto.getAnio());
        obra.setTecnica(dto.getTecnica());
        obra.setDimensiones(dto.getDimensiones());
        obra.setDescripcion(dto.getDescripcion());
        obra.setImagenUrl(dto.getImagenUrl());

        obra.setVisible(true);

        return obraRepository.save(obra);
    }

    /*public Obra guardarObra(ObraRequestDTO obradto) {
        // 1. Validaciones
        if (obradto.getTitulo() == null || obradto.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
        if (obradto.getAutor() == null || obradto.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("El autor es obligatorio");
        }

        // 2. Mapeamos los datos del DTO a la Entidad Obra
        Obra obra = new Obra();
        obra.setTitulo(obradto.getTitulo());
        obra.setAutor(obradto.getAutor());
        obra.setAnio(obradto.getAnio());
        obra.setTecnica(obradto.getTecnica());
        obra.setDimensiones(obradto.getDimensiones());
        obra.setDescripcion(obradto.getDescripcion());
        obra.setImagenUrl(obradto.getImagenUrl());

        // Forzamos el borrado lógico en true (visible)
        obra.setVisible(true);

        // 3. Guardamos en la base de datos y devolvemos el objeto guardado
        // Este objeto 'obraGuardada' ya incluye el idObra que le dio MySQL
        return obraRepository.save(obra);
    }*/
    //mostrar obras
    public List<Obra> obtenerObrasGaleria() {
        // Trae solo las obras que tienen visible = true
        return obraRepository.findByVisibleTrue();
    }
    public List<Obra> obtenerObrasGaleriaOrdenadas() {
        return obraRepository.findByVisibleTrueOrderByTituloAsc();
    }

    // buscador
    public List<Obra> buscarObras(String texto) {

        if (texto == null || texto.trim().isEmpty()) {
            return obraRepository.findByVisibleTrue();
        }
        // Buscamos por título o autor ignorando mayúsculas/minúsculas
        return obraRepository.findByVisibleTrueAndTituloContainingIgnoreCaseOrVisibleTrueAndAutorContainingIgnoreCase(texto, texto);
    }

    //actualizar
    public Obra actualizarObra(Long id, ObraRequestDTO dto) {
        // Verificamos si la obra existe en MySQL
        Obra obraExistente = obraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La obra con ID " + id + " no existe."));

        // Actualizamos sus campos con los nuevos datos del DTO
        obraExistente.setTitulo(dto.getTitulo());
        obraExistente.setAutor(dto.getAutor());
        obraExistente.setAnio(dto.getAnio());
        obraExistente.setTecnica(dto.getTecnica());
        obraExistente.setDimensiones(dto.getDimensiones());
        obraExistente.setDescripcion(dto.getDescripcion());
        obraExistente.setImagenUrl(dto.getImagenUrl());


        // Guardamos los cambios
        return obraRepository.save(obraExistente);
    }
    //cambiar la visibilidad
    public void visibilidaObra(Long id) {
        Obra obra = obraRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Obra no encontrada"));

        obra.setVisible(true);

        obraRepository.save(obra);
    }

    //eliminar (ocultar)
    public void ocultarObra(Long id) {
        Obra obra = obraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La obra con ID " + id + " no existe."));
        obra.setVisible(false);
        obraRepository.save(obra);
    }
    public List<Obra> obtenerTodas() {
        return obraRepository.findAll();
    }

    // Buscar una obra por ID
    public Obra obtenerPorId(Long id) {
        return obraRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Obra no encontrada"));
    }

    // Eliminar físicamente (opcional)
    public void eliminarDefinitivamente(Long id) {
        obraRepository.deleteById(id);
    }

}
