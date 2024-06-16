package com.orcnaydn.ecommerce.controller;

import com.orcnaydn.ecommerce.dto.answer.CreateUpdateAnswerDto;
import com.orcnaydn.ecommerce.dto.response.AnswerResponseDto;
import com.orcnaydn.ecommerce.mapper.AnswerMapper;
import com.orcnaydn.ecommerce.service.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("answers/{id}")
    public AnswerResponseDto getAnswerById(@PathVariable("id") Long answerId){
        return AnswerMapper.mapToDto(answerService.getAnswerById(answerId));
    }

    @GetMapping("questions/{id}/answers")
    public List<AnswerResponseDto> getAnswersByQuestion(@PathVariable("id") Long questionId){
        return answerService.getAnswersByQuestion(questionId).stream().map(AnswerMapper::mapToDto).collect(Collectors.toList());
    }

    @PostMapping("questions/{id}/answers")
    public AnswerResponseDto createAnswer(@PathVariable("id") Long questionId, @Valid @RequestBody CreateUpdateAnswerDto dto){
        return AnswerMapper.mapToDto(answerService.createAnswer(questionId,dto));
    }

    @PutMapping("answers/{id}")
    public AnswerResponseDto updateAnswer(@PathVariable("id") Long answerId, @Valid @RequestBody CreateUpdateAnswerDto dto){
        return AnswerMapper.mapToDto(answerService.updateAnswer(answerId,dto));
    }

    @DeleteMapping("answers/{id}")
    public String deleteAnswer(@PathVariable("id") Long answerId){
        return answerService.deleteAnswer(answerId);
    }
}