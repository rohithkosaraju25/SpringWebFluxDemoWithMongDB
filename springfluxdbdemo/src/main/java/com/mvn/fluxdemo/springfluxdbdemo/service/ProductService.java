package com.mvn.fluxdemo.springfluxdbdemo.service;

import com.mvn.fluxdemo.springfluxdbdemo.dto.ProductDto;
import com.mvn.fluxdemo.springfluxdbdemo.entity.Product;
import com.mvn.fluxdemo.springfluxdbdemo.repo.ProductRepositry;
import com.mvn.fluxdemo.springfluxdbdemo.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepositry productRepositry;

    public Flux<ProductDto> getProducts(){
        return productRepositry.findAll().map(AppUtils :: entityToDto);
    }

    public Mono<ProductDto> getProductById(String id){
        return productRepositry.findById(id).map(AppUtils::entityToDto);
    }

    public Flux<ProductDto> getProductInRange(double min, double max){
        return productRepositry.findByPriceBetween(Range.closed(min,max));
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(productRepositry::insert)
                .map(AppUtils::entityToDto);
    }

    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, String id){
      return  productRepositry.findById(id)
                .flatMap(p->productDtoMono.map(AppUtils::dtoToEntity))
                .doOnNext(e->e.setId(id))
                .flatMap(productRepositry::save)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteProduct(String id){
        return productRepositry.deleteById(id);
    }
}
