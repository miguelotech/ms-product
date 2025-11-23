package com.synopsis.product.controller;

import com.synopsis.product.openapi.dto.ProductRequest;
import com.synopsis.product.openapi.dto.ProductResponse;
import com.synopsis.product.service.ProductService;
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
class ProductControllerTest {

    @Mock
    private ProductService service;

    private ProductController controller;

    @BeforeEach
    void setUp() {
        controller = new ProductController(service);
    }

    @Test
    void getAllReturnsFluxFromService() {
        ProductResponse response = new ProductResponse().id(1L).nombre("Laptop");
        when(service.findAll()).thenReturn(Flux.just(response));

        StepVerifier.create(controller.getAll())
                .expectNext(response)
                .verifyComplete();

        verify(service).findAll();
    }

    @Test
    void getByIdWrapsResponseEntity() {
        ProductResponse response = new ProductResponse().id(7L).nombre("Mouse");
        when(service.findById(7L)).thenReturn(Mono.just(response));
        when(service.findById(8L)).thenReturn(Mono.empty());

        StepVerifier.create(controller.getById(7L))
                .assertNext(entity -> {
                    assertThat(entity.getBody()).isEqualTo(response);
                    assertThat(entity.getStatusCode().value()).isEqualTo(200);
                })
                .verifyComplete();

        StepVerifier.create(controller.getById(8L))
                .assertNext(entity -> assertThat(entity.getStatusCode().value()).isEqualTo(404))
                .verifyComplete();
    }

    @Test
    void createMapsCreatedStatus() {
        ProductRequest request = new ProductRequest()
                .nombre("Monitor")
                .precio(400.0)
                .stock(4)
                .activo(true);
        ProductResponse saved = new ProductResponse()
                .id(10L)
                .nombre("Monitor")
                .precio(400.0)
                .stock(4)
                .activo(true);
        when(service.save(any(ProductRequest.class))).thenReturn(Mono.just(saved));

        StepVerifier.create(controller.create(request))
                .assertNext(entity -> {
                    assertThat(entity.getStatusCode().value()).isEqualTo(201);
                    assertThat(entity.getBody()).isEqualTo(saved);
                })
                .verifyComplete();

        verify(service).save(request);
    }

    @Test
    void deleteReturnsNoContent() {
        when(service.delete(5L)).thenReturn(Mono.empty());

        StepVerifier.create(controller.delete(5L))
                .assertNext(entity -> assertThat(entity.getStatusCode().value()).isEqualTo(204))
                .verifyComplete();

        verify(service).delete(5L);
    }
}
