package com.synopsis.product.service;

import com.synopsis.product.entity.Product;
import com.synopsis.product.entity.dto.*;
import com.synopsis.product.mapper.ProductMapper;
import com.synopsis.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Flux<ProductResponse> findAll() {
        return repository.findAll().map(mapper::toResponse);
    }

    public Mono<ProductResponse> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse);
    }

    public Mono<ProductResponse> save(ProductRequest request) {
        Product entity = mapper.toEntity(request);
        return repository.save(entity)
                .map(mapper::toResponse);
    }

    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }
}
