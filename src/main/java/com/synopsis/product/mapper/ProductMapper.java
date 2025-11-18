package com.synopsis.product.mapper;

import com.synopsis.product.entity.Product;
import com.synopsis.product.entity.dto.ProductRequest;
import com.synopsis.product.entity.dto.ProductResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    Product toEntity(ProductRequest request);
    ProductResponse toResponse(Product entity);

    @AfterMapping
    default void ensureCreationDate(@MappingTarget Product product) {
        if (product.getFechaCreacion() == null) {
            product.setFechaCreacion(LocalDateTime.now());
        }
    }
}
