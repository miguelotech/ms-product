package com.synopsis.product.controller;

import com.synopsis.product.entity.dto.ProductRequest;
import com.synopsis.product.entity.dto.ProductResponse;
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
        ProductResponse response = new ProductResponse(1L, "Laptop", null, null, null, null, null);
        when(service.findAll()).thenReturn(Flux.just(response));

        StepVerifier.create(controller.getAll())
                .expectNext(response)
                .verifyComplete();

        verify(service).findAll();
    }

    @Test
    void getByIdWrapsResponseEntity() {
        ProductResponse response = new ProductResponse(7L, "Mouse", null, null, null, null, null);
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
        ProductRequest request = new ProductRequest("Monitor", null, 400.0, 4, true);
        ProductResponse saved = new ProductResponse(10L, "Monitor", null, 400.0, 4, true, null);
        when(service.save(request)).thenReturn(Mono.just(saved));

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
