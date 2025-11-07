package com.synopsis.product.entity.dto;
import java.time.LocalDateTime;


//los datos que nos da como respuesta
public record ProductResponse(
        Long id,
        String nombre,
        String descripcion,
        Double precio,
        Integer stock,
        Boolean activo,
        LocalDateTime fechaCreacion
){}
