package com.orcnaydn.ecommerce.mapper;

import com.orcnaydn.ecommerce.dto.response.QuestionResponseDto;
import com.orcnaydn.ecommerce.entity.Question;

public class QuestionMapper {

    public static QuestionResponseDto mapToDto(Question question){
        if(question == null) return null;
        return new QuestionResponseDto(question.getId(),question.getUser().getId(),
                question.getProduct().getId(),question.getQuestion());
    }
}
