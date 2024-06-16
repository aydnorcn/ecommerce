package com.orcnaydn.ecommerce.service;

import com.orcnaydn.ecommerce.dto.PageResponseDto;
import com.orcnaydn.ecommerce.dto.review.CreateReviewDto;
import com.orcnaydn.ecommerce.dto.review.UpdateReviewDto;
import com.orcnaydn.ecommerce.entity.Product;
import com.orcnaydn.ecommerce.entity.Review;
import com.orcnaydn.ecommerce.entity.User;
import com.orcnaydn.ecommerce.exception.APIException;
import com.orcnaydn.ecommerce.exception.AlreadyExistsException;
import com.orcnaydn.ecommerce.exception.NoAuthorityException;
import com.orcnaydn.ecommerce.exception.ResourceNotFoundException;
import com.orcnaydn.ecommerce.filter.ReviewFilter;
import com.orcnaydn.ecommerce.repository.OrderItemRepository;
import com.orcnaydn.ecommerce.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ProductService productService;
    private final OrderItemRepository itemRepository;

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException("Review not found!"));
    }

    public PageResponseDto<Review> getReviews(UUID userId, UUID productId, Integer minScore, Integer maxScore, int pageNo, int pageSize) {
        User user = userService.getUserIfExists(userId);
        Product product = productService.getProductIfExists(productId);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Specification<Review> specification = ReviewFilter.filter(user, product, minScore, maxScore);
        Page<Review> reviews = reviewRepository.findAll(specification, pageable);

        return new PageResponseDto<>(reviews);
    }

    public Review createReview(UUID productId, CreateReviewDto dto) {
        User user = userService.getCurrentAuthenticatedUser();
        Product product = productService.getProductById(productId);

        if (!productService.isUserBoughtProduct(user, product))
            throw new APIException(HttpStatus.BAD_REQUEST, "User can't review this product!");

        if (reviewRepository.findByUserAndProduct(user, product).isPresent())
            throw new AlreadyExistsException("You already reviewed this product!");

        Review review = reviewRepository.save(initializeReview(user, product, dto));
        productService.calculateTotalScoreOfProduct(product);

        return review;
    }

    public Review updateReview(Long reviewId, UpdateReviewDto dto) {
        Review review = getReviewById(reviewId);

        review.setReview(dto.getReview());
        review.setScore(dto.getScore());

        Review savedReview = reviewRepository.save(review);
        productService.calculateTotalScoreOfProduct(review.getProduct());

        return savedReview;
    }

    public String deleteReview(Long reviewId) {
        Review review = getReviewById(reviewId);

        if (!hasUserAuthority(review))
            throw new NoAuthorityException("You don't have permission!");

        reviewRepository.delete(review);
        productService.calculateTotalScoreOfProduct(review.getProduct());

        return "Review successfully deleted!";
    }

    private boolean hasUserAuthority(Review review) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        return review.getUser().equals(currentUser) || userService.isCurrentAuthenticatedUserAdmin();
    }

    private Review initializeReview(User user, Product product, CreateReviewDto dto) {
        return Review.builder()
                .user(user)
                .product(product)
                .review(dto.getReview())
                .score(dto.getScore()).build();
    }
}