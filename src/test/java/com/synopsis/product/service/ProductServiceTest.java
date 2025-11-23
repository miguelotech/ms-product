package com.synopsis.product.service;

import com.synopsis.product.entity.Product;
import com.synopsis.product.mapper.ProductMapper;
import com.synopsis.product.openapi.dto.ProductRequest;
import com.synopsis.product.openapi.dto.ProductResponse;
import com.synopsis.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService(repository, mapper);
    }

    @Test
    void findAllReturnsMappedResponses() {
        Product entity = Product.builder()
                .id(1L)
                .nombre("Laptop")
                .descripcion("Gamer")
                .precio(1500.0)
                .stock(5)
                .activo(true)
                .build();
        ProductResponse response = new ProductResponse()
                .id(1L)
                .nombre("Laptop")
                .descripcion("Gamer")
                .precio(1500.0)
                .stock(5)
                .activo(true);

        when(repository.findAll()).thenReturn(Flux.just(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        StepVerifier.create(service.findAll())
                .expectNext(response)
                .verifyComplete();

        verify(repository).findAll();
        verify(mapper).toResponse(entity);
    }

    @Test
    void findByIdPropagatesRepositoryResult() {
        Product entity = Product.builder().id(7L).nombre("Mouse").build();
        ProductResponse response = new ProductResponse().id(7L).nombre("Mouse");

        when(repository.findById(7L)).thenReturn(Mono.just(entity));
        when(repository.findById(8L)).thenReturn(Mono.empty());
        when(mapper.toResponse(entity)).thenReturn(response);

        StepVerifier.create(service.findById(7L))
                .expectNext(response)
                .verifyComplete();

        StepVerifier.create(service.findById(8L))
                .verifyComplete();

        verify(repository).findById(7L);
    }

    @Test
    void savePersistsEntityAndReturnsResponse() {
        ProductRequest request = new ProductRequest()
                .nombre("Teclado")
                .descripcion("Mecanico")
                .precio(99.0)
                .stock(10)
                .activo(true);
        Product toPersist = Product.builder().nombre("Teclado").build();
        Product saved = Product.builder().id(3L).nombre("Teclado").build();
        ProductResponse expected = new ProductResponse()
                .id(3L)
                .nombre("Teclado")
                .descripcion("Mecanico")
                .precio(99.0)
                .stock(10)
                .activo(true);

        when(mapper.toEntity(request)).thenReturn(toPersist);
        when(repository.save(toPersist)).thenReturn(Mono.just(saved));
        when(mapper.toResponse(saved)).thenReturn(expected);

        StepVerifier.create(service.save(request))
                .assertNext(response -> assertThat(response).isEqualTo(expected))
                .verifyComplete();

        verify(mapper).toEntity(request);
        verify(repository).save(toPersist);
        verify(mapper).toResponse(saved);
    }

    @Test
    void deleteDelegatesToRepository() {
        when(repository.deleteById(4L)).thenReturn(Mono.empty());

        StepVerifier.create(service.delete(4L))
                .verifyComplete();

        verify(repository).deleteById(4L);
    }
}
