package com.orcnaydn.ecommerce.controller;

import com.orcnaydn.ecommerce.dto.PageResponseDto;
import com.orcnaydn.ecommerce.dto.question.CreateUpdateQuestionDto;
import com.orcnaydn.ecommerce.dto.response.QuestionResponseDto;
import com.orcnaydn.ecommerce.entity.Question;
import com.orcnaydn.ecommerce.mapper.QuestionMapper;
import com.orcnaydn.ecommerce.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("questions/{id}")
    public QuestionResponseDto getQuestionById(@PathVariable("id") Long questionId) {
        return QuestionMapper.mapToDto(questionService.getQuestionById(questionId));
    }

    @GetMapping("questions")
    public PageResponseDto<QuestionResponseDto> getQuestions(@RequestParam(name = "user", required = false) UUID userId, @RequestParam(name = "product", required = false) UUID productId,
                                                             @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                             @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        PageResponseDto<Question> questions = questionService.getQuestions(userId, productId, pageNo, pageSize);

        return new PageResponseDto<>(questions.getContent().stream().map(QuestionMapper::mapToDto).collect(Collectors.toList()), questions.getPageNo(), questions.getPageSize(), questions.getTotalElements(), questions.getTotalPages());
    }

    @PostMapping("products/{id}/questions")
    public QuestionResponseDto createQuestion(@PathVariable("id") UUID productId, @Valid @RequestBody CreateUpdateQuestionDto dto) {
        return QuestionMapper.mapToDto(questionService.createQuestion(productId, dto));
    }

    @DeleteMapping("questions/{id}")
    public String deleteQuestion(@PathVariable("id") Long questionId) {
        return questionService.deleteQuestion(questionId);
    }

}