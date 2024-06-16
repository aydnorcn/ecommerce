package com.orcnaydn.ecommerce.mapper;

import com.orcnaydn.ecommerce.dto.response.AnswerResponseDto;
import com.orcnaydn.ecommerce.entity.Answer;

public class AnswerMapper {

    public static AnswerResponseDto mapToDto(Answer answer){
        if(answer == null) return null;
        return new AnswerResponseDto(answer.getId(),answer.getQuestion().getId(),answer.getMessage());
    }
}