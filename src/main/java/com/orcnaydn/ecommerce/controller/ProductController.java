package com.orcnaydn.ecommerce.controller;

import com.orcnaydn.ecommerce.dto.PageResponseDto;
import com.orcnaydn.ecommerce.dto.product.CreateProductDto;
import com.orcnaydn.ecommerce.dto.product.UpdateProductDto;
import com.orcnaydn.ecommerce.dto.response.ProductResponseDto;
import com.orcnaydn.ecommerce.entity.Product;
import com.orcnaydn.ecommerce.mapper.ProductMapper;
import com.orcnaydn.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") UUID productId){
        return ProductMapper.mapToDto(productService.getProductById(productId));
    }

    @GetMapping("products")
    public PageResponseDto<ProductResponseDto> getProducts(@RequestParam(name = "store", required = false) Long storeId, @RequestParam(name = "category", required = false) Long categoryId,
                                                @RequestParam(name = "minPrice", required = false) Double minPrice,@RequestParam(name = "maxPrice", required = false) Double maxPrice,
                                                @RequestParam(name = "minScore", required = false) Float minScore,@RequestParam(name = "maxScore", required = false) Float maxScore,
                                                @RequestParam(name = "pageNo", defaultValue = "0",required = false) int pageNo, @RequestParam(name = "pageSize", defaultValue = "10",required = false) int pageSize){
        PageResponseDto<Product> products = productService.getProducts(storeId, categoryId, minPrice, maxPrice, minScore, maxScore, pageNo, pageSize);

        return new PageResponseDto<>(products.getContent().stream().map(ProductMapper::mapToDto).collect(Collectors.toList()),products.getPageNo(),products.getPageSize(),products.getTotalElements(),products.getTotalPages());
    }

    @PostMapping("stores/{id}/products")
    public ProductResponseDto createProduct(@PathVariable("id") Long storeId,@Valid @RequestBody CreateProductDto dto){
        return ProductMapper.mapToDto((productService.createProduct(storeId,dto)));
    }

    @PutMapping("products/{id}")
    public ProductResponseDto updateProduct(@PathVariable("id") UUID productId, @Valid @RequestBody UpdateProductDto dto){
        return ProductMapper.mapToDto((productService.updateProduct(productId,dto)));
    }

    @DeleteMapping("products/{id}")
    public String deleteProduct(@PathVariable("id") UUID productId){
        return productService.deleteProduct(productId);
    }
}