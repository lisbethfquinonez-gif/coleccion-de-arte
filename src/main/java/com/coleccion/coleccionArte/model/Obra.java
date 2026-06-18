package com.coleccion.coleccionArte.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "obra")
@Data
public class Obra {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_obra")
        private Long idObra;

        @Column(nullable = false)
        private String titulo;

        @Column(nullable = false)
        private String autor;

        private Integer anio;

        private String tecnica;

        private String dimensiones;

        @Column(columnDefinition = "TEXT")
        private String descripcion;

        @Column(name = "imagen_url")
        private String imagenUrl;

        @Column(nullable = false)
        private boolean visible = true;

}
