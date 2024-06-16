package com.orcnaydn.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerResponseDto {

    private Long id;
    private Long question;
    private String message;
}