package com.coleccion.coleccionArte.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.spi.LocaleNameProvider;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObraResponseDTO {
    private Long idObra;
    private String titulo;
    private String autor;
    private Integer anio;
    private String tecnica;
    private String dimensiones;
    private String descripcion;
    private String imagenUrl;
    private boolean visible = true;
}
