package com.orcnaydn.ecommerce.mapper;

import com.orcnaydn.ecommerce.dto.response.ProductResponseDto;
import com.orcnaydn.ecommerce.entity.Product;

public class ProductMapper {

    public static ProductResponseDto mapToDto(Product product){
        if(product == null) return null;
        return new ProductResponseDto(product.getId(),product.getName(),product.getDescription(),
                product.getPrice(),product.getCategory(),product.getStore().getId(),product.getTotalScore());
    }
}
