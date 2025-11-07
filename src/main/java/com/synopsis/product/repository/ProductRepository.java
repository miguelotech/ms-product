package com.synopsis.product.repository;

import com.synopsis.product.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    //consultas personalizadas, pondré lo de los procedimientos almacenados.

}
