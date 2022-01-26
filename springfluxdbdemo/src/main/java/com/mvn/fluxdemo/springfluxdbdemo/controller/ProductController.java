package com.mvn.fluxdemo.springfluxdbdemo.controller;

import com.mvn.fluxdemo.springfluxdbdemo.dto.ProductDto;
import com.mvn.fluxdemo.springfluxdbdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    public Flux<ProductDto> getAllProducts(){
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDto> getAProductById(@PathVariable String id){
        return productService.getProductById(id);
    }

    @GetMapping("/product-range")
    public Flux<ProductDto> getProductInRange(@RequestParam("min") double min,@RequestParam("max") double max){
        return productService.getProductInRange(min,max);
    }

    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDtoMono){
        return productService.saveProduct(productDtoMono);
    }

    @PutMapping("/update/{id}")
    public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> productDtoMono,@PathVariable String id){
        return productService.updateProduct(productDtoMono,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id){
        return productService.deleteProduct(id);
    }
}
