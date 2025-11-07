package com.synopsis.product.mapper;

import com.synopsis.product.entity.dto.*;
import com.synopsis.product.entity.Product;
import org.mapstruct.*;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequest request);
    ProductResponse toResponse(Product entity);

    //después del mapeo si no hay fecha pasa la actual
    @AfterMapping
    default void setFechaCreacion(@MappingTarget Product product) {
        if (product.getFechaCreacion() == null) {
            product.setFechaCreacion(LocalDateTime.now());
        }
    }
}
