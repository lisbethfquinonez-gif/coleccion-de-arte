package com.coleccion.coleccionArte.repository;

import com.coleccion.coleccionArte.model.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObraRepository extends JpaRepository<Obra,Long> {

        //Devuelve solo las obras donde visible = true (Para la galería general)
        List<Obra> findByVisibleTrue();
        List<Obra> findByVisibleTrueOrderByTituloAsc();
        //Busca obras visibles cuyo título O autor contengan el texto buscado
        List<Obra> findByVisibleTrueAndTituloContainingIgnoreCaseOrVisibleTrueAndAutorContainingIgnoreCase(String titulo, String autor);
        List<Obra> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(
                String titulo,
                String autor);
        List<Obra> findByVisibleTrueAndAnioOrderByTituloAsc(Integer anio);
}
