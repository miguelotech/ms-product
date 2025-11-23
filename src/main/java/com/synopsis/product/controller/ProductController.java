package com.synopsis.product.controller;

import com.synopsis.product.openapi.dto.ProductRequest;
import com.synopsis.product.openapi.dto.ProductResponse;
import com.synopsis.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Productos")
public class ProductController {

    private final ProductService service;

    @GetMapping
    @Operation(summary = "Lista todos los productos")
    public Flux<ProductResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un producto por id")
    public Mono<ResponseEntity<ProductResponse>> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo producto")
    public Mono<ResponseEntity<ProductResponse>> create(@Valid @RequestBody ProductRequest request) {
        return service.save(request)
                .map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un producto por id")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return service.delete(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
