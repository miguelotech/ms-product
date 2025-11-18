package com.synopsis.product.mapper;

import com.synopsis.product.entity.Product;
import com.synopsis.product.entity.dto.ProductRequest;
import com.synopsis.product.entity.dto.ProductResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequest request);
    ProductResponse toResponse(Product entity);

    @AfterMapping
    default void ensureCreationDate(@MappingTarget Product.ProductBuilder builder) {
        if (builder.build().getFechaCreacion() == null) {
            builder.fechaCreacion(LocalDateTime.now());
        }
    }
}
