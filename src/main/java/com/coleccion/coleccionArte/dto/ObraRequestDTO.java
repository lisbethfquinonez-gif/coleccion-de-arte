package com.coleccion.coleccionArte.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ObraRequestDTO {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    @NotBlank(message = "El autor es obligatorio")
    private String autor;
    private Integer anio;
    private String tecnica;
    private String dimensiones;
    private String descripcion;
    private String imagenUrl;
}