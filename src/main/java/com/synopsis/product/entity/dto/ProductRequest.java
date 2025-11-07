package com.synopsis.product.entity.dto;

import jakarta.validation.constraints.*;

// los datos que nosotros enviamos
public record ProductRequest(
        @NotBlank String nombre,
        String descripcion,
        @NotNull @Positive Double precio,
        @NotNull @PositiveOrZero Integer stock,
        Boolean activo // (true or false)
){}
