package com.orcnaydn.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private Integer quantity;
    private Double eachPrice;
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
