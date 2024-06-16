package com.orcnaydn.ecommerce.mapper;

import com.orcnaydn.ecommerce.dto.response.ReviewResponseDto;
import com.orcnaydn.ecommerce.entity.Review;

public class ReviewMapper {

    public static ReviewResponseDto mapToDto(Review review){
        if(review == null) return null;
        return new ReviewResponseDto(review.getId(),review.getUser().getId(),
                review.getProduct().getId(),review.getReview(),review.getScore());
    }
}
