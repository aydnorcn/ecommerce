package com.orcnaydn.ecommerce.service;

import com.orcnaydn.ecommerce.dto.PageResponseDto;
import com.orcnaydn.ecommerce.dto.product.CreateProductDto;
import com.orcnaydn.ecommerce.dto.product.UpdateProductDto;
import com.orcnaydn.ecommerce.entity.*;
import com.orcnaydn.ecommerce.exception.NoAuthorityException;
import com.orcnaydn.ecommerce.exception.ResourceNotFoundException;
import com.orcnaydn.ecommerce.filter.ProductFilter;
import com.orcnaydn.ecommerce.repository.OrderItemRepository;
import com.orcnaydn.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final CategoryService categoryService;
    private final OrderItemRepository itemRepository;

    public Product getProductById(UUID productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    public PageResponseDto<Product> getProducts(Long storeId, Long categoryId, Double minPrice, Double maxPrice,
                                                Float minTotalScore, Float maxTotalScore, int pageNo, int pageSize) {
        Store store = storeService.getStoreIfExists(storeId);
        Category category = categoryService.getCategoryIfExists(categoryId);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Specification<Product> specification = ProductFilter.filter(store, category, minPrice, maxPrice, minTotalScore, maxTotalScore);
        Page<Product> products = productRepository.findAll(specification, pageable);

        return new PageResponseDto<>(products);
    }

    public Product createProduct(Long storeId, CreateProductDto dto) {
        Store store = storeService.getStoreById(storeId);
        Category category = categoryService.getCategoryById(dto.getCategoryId());

        if (!storeService.hasUserAuthority(store))
            throw new NoAuthorityException("You don't have permission!");

        return productRepository.save(initalizeProduct(store, category, dto));
    }

    public Product updateProduct(UUID productId, UpdateProductDto dto) {
        Product product = getProductById(productId);
        Store store = storeService.getStoreById(dto.getStoreId());
        Category category = categoryService.getCategoryById(dto.getCategoryId());

        if (!storeService.hasUserAuthority(product.getStore()))
            throw new NoAuthorityException("You don't have permission!");

        return productRepository.save(updateProduct(product, store, category, dto));
    }

    public String deleteProduct(UUID productId) {
        Product product = getProductById(productId);

        if (!storeService.hasUserAuthority(product.getStore()))
            throw new NoAuthorityException("You don't have permission!");

        productRepository.delete(product);

        return "Product successfully deleted!";
    }

    public void calculateTotalScoreOfProduct(Product product) {
        Integer sumOfScore = product.getReviews().stream()
                .map(Review::getScore)
                .reduce(0, Integer::sum);

        product.setTotalScore(((float) sumOfScore / (float) product.getReviews().size()));

        productRepository.save(product);

        storeService.calculateStoreTotalScore(product.getStore().getId());
    }

    private Product initalizeProduct(Store store, Category category, CreateProductDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(category)
                .store(store)
                .totalScore(0f).build();
    }

    private Product updateProduct(Product product, Store store, Category category, UpdateProductDto dto) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStore(store);
        product.setCategory(category);

        return product;
    }

    public boolean isUserBoughtProduct(User user, Product product) {
        List<OrderItem> items = itemRepository.findAllByProductAndUser(product, user);

        return !items.isEmpty();
    }

    public Product getProductIfExists(UUID productId) {
        return (productId != null) ? getProductById(productId) : null;
    }
}