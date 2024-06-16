package com.orcnaydn.ecommerce.controller;

import com.orcnaydn.ecommerce.dto.PageResponseDto;
import com.orcnaydn.ecommerce.dto.response.ReviewResponseDto;
import com.orcnaydn.ecommerce.dto.review.CreateReviewDto;
import com.orcnaydn.ecommerce.dto.review.UpdateReviewDto;
import com.orcnaydn.ecommerce.entity.Review;
import com.orcnaydn.ecommerce.mapper.ReviewMapper;
import com.orcnaydn.ecommerce.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("reviews/{id}")
    public ReviewResponseDto getReviewById(@PathVariable("id") Long reviewId){
        return ReviewMapper.mapToDto(reviewService.getReviewById(reviewId));
    }

    @GetMapping("reviews")
    public PageResponseDto<ReviewResponseDto> getReviews(@RequestParam(name = "user",required = false) UUID userId,@RequestParam(name = "product",required = false) UUID productId,
                                              @RequestParam(name = "minScore", required = false) Integer minScore,@RequestParam(name = "maxScore", required = false) Integer maxScore,
                                              @RequestParam(name = "pageNo", defaultValue = "0",required = false) int pageNo, @RequestParam(name = "pageSize", defaultValue = "10",required = false) int pageSize){
        PageResponseDto<Review> reviews = reviewService.getReviews(userId, productId, minScore, maxScore, pageNo, pageSize);

        return new PageResponseDto<>(reviews.getContent().stream().map(ReviewMapper::mapToDto).collect(Collectors.toList()),reviews.getPageNo(),reviews.getPageSize(),reviews.getTotalElements(),reviews.getTotalPages());
    }

    @PostMapping("products/{id}/reviews")
    public ReviewResponseDto createReview(@PathVariable("id") UUID productId, @Valid @RequestBody CreateReviewDto dto){
        return ReviewMapper.mapToDto(reviewService.createReview(productId,dto));
    }

    @PutMapping("reviews/{id}")
    public ReviewResponseDto updateReview(@PathVariable("id") Long reviewId, @Valid @RequestBody UpdateReviewDto dto){
        return ReviewMapper.mapToDto(reviewService.updateReview(reviewId,dto));
    }

    @DeleteMapping("reviews/{id}")
    public String deleteReview(@PathVariable("id") Long reviewId){
        return reviewService.deleteReview(reviewId);
    }
}