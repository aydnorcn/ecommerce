package com.orcnaydn.ecommerce.service;

import com.orcnaydn.ecommerce.dto.PageResponseDto;
import com.orcnaydn.ecommerce.dto.order.CreateOrderDto;
import com.orcnaydn.ecommerce.entity.Order;
import com.orcnaydn.ecommerce.entity.OrderItem;
import com.orcnaydn.ecommerce.entity.Product;
import com.orcnaydn.ecommerce.entity.User;
import com.orcnaydn.ecommerce.exception.NoAuthorityException;
import com.orcnaydn.ecommerce.exception.ResourceNotFoundException;
import com.orcnaydn.ecommerce.filter.OrderFilter;
import com.orcnaydn.ecommerce.repository.OrderItemRepository;
import com.orcnaydn.ecommerce.repository.OrderRepository;
import com.orcnaydn.ecommerce.utils.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.DoubleAdder;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final OrderItemRepository itemRepository;

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found!"));
    }

    public PageResponseDto<Order> getOrders(UUID userId, Double minPrice, Double maxPrice, Integer status, int pageNo, int pageSize) {
        User user = userService.getUserIfExists(userId);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Specification<Order> specification = OrderFilter.filter(user, minPrice, maxPrice, status);
        Page<Order> orders = orderRepository.findAll(specification, pageable);

        return new PageResponseDto<>(orders);
    }

    public Order createOrder(CreateOrderDto dto) {
        User user = userService.getCurrentAuthenticatedUser();

        Order savedOrder = orderRepository.save(new Order());

        DoubleAdder totalPrice = new DoubleAdder();
        List<OrderItem> items = new ArrayList<>();
        dto.getProducts().forEach(x -> {
            Product product = productService.getProductById(x.getProductId());
            OrderItem item = mapProductToOrderItem(product, x.getQuantity(), user, savedOrder);
            totalPrice.add(item.getTotalPrice());
            items.add(item);
        });

        totalPrice.sum();
        savedOrder.setUser(user);
        savedOrder.setTotalPrice(totalPrice.doubleValue());
        savedOrder.setStatus(OrderStatus.PREPARING);
        savedOrder.setItems(items);
        return orderRepository.save(savedOrder);
    }

    public String deleteOrder(UUID orderId) {
        Order order = getOrderById(orderId);

        if (!hasUserAuthority(order))
            throw new NoAuthorityException("You don't have permission!");

        orderRepository.delete(order);

        return "Order successfully deleted!";
    }

    private boolean hasUserAuthority(Order order) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        return order.getUser().equals(currentUser) || userService.isCurrentAuthenticatedUserAdmin();
    }

    private OrderItem mapProductToOrderItem(Product product, Integer quantity, User user, Order order) {
        OrderItem item = OrderItem.builder()
                .product(product)
                .name(product.getName())
                .description(product.getDescription())
                .store(product.getStore())
                .quantity(quantity)
                .eachPrice(product.getPrice())
                .totalPrice(quantity * product.getPrice())
                .category(product.getCategory())
                .user(user)
                .order(order).build();

        return itemRepository.save(item);
    }
}