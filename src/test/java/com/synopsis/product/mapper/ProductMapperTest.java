package com.synopsis.product.mapper;

import com.synopsis.product.entity.Product;
import com.synopsis.product.openapi.dto.ProductRequest;
import com.synopsis.product.openapi.dto.ProductResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void toEntityInitializesCreationDate() {
        ProductRequest request = new ProductRequest()
                .nombre("Mouse")
                .descripcion("Inalambrico")
                .precio(35.0)
                .stock(20)
                .activo(true);

        Product entity = mapper.toEntity(request);

        assertThat(entity.getNombre()).isEqualTo("Mouse");
        assertThat(entity.getDescripcion()).isEqualTo("Inalambrico");
        assertThat(entity.getPrecio()).isEqualTo(35.0);
        assertThat(entity.getStock()).isEqualTo(20);
        assertThat(entity.getActivo()).isTrue();
        assertThat(entity.getFechaCreacion()).isNotNull();
    }

    @Test
    void toResponseCopiesAllValues() {
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        Product entity = Product.builder()
                .id(9L)
                .nombre("Teclado")
                .descripcion("Mecanico")
                .precio(120.0)
                .stock(15)
                .activo(true)
                .fechaCreacion(createdAt)
                .build();

        ProductResponse response = mapper.toResponse(entity);

        assertThat(response.getId()).isEqualTo(9L);
        assertThat(response.getNombre()).isEqualTo("Teclado");
        assertThat(response.getDescripcion()).isEqualTo("Mecanico");
        assertThat(response.getPrecio()).isEqualTo(120.0);
        assertThat(response.getStock()).isEqualTo(15);
        assertThat(response.getActivo()).isTrue();
        assertThat(response.getFechaCreacion()).isEqualTo(createdAt);
    }

    @Test
    void ensureCreationDateKeepsExistingValue() {
        LocalDateTime existingCreationDate = LocalDateTime.now().minusWeeks(1);
        Product product = Product.builder()
                .nombre("Monitor")
                .descripcion("24 pulgadas")
                .fechaCreacion(existingCreationDate)
                .build();

        mapper.ensureCreationDate(product);

        assertThat(product.getFechaCreacion()).isEqualTo(existingCreationDate);
    }
}
