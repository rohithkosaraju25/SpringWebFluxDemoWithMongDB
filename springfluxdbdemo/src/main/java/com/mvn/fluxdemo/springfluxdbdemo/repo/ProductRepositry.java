package com.mvn.fluxdemo.springfluxdbdemo.repo;

import com.mvn.fluxdemo.springfluxdbdemo.dto.ProductDto;
import com.mvn.fluxdemo.springfluxdbdemo.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepositry extends ReactiveMongoRepository<Product,String> {
    Flux<ProductDto> findByPriceBetween(Range<Double> priceRange);
}
