package com.orcnaydn.ecommerce.service;

import com.orcnaydn.ecommerce.dto.answer.CreateUpdateAnswerDto;
import com.orcnaydn.ecommerce.entity.Answer;
import com.orcnaydn.ecommerce.entity.Question;
import com.orcnaydn.ecommerce.entity.User;
import com.orcnaydn.ecommerce.exception.NoAuthorityException;
import com.orcnaydn.ecommerce.exception.ResourceNotFoundException;
import com.orcnaydn.ecommerce.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final UserService userService;

    public Answer getAnswerById(Long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() -> new ResourceNotFoundException("Answer not found!"));
    }

    public List<Answer> getAnswersByQuestion(Long questionId) {
        Question question = questionService.getQuestionById(questionId);

        return answerRepository.findAllByQuestion(question);
    }

    public Answer createAnswer(Long questionId, CreateUpdateAnswerDto dto) {
        Question question = questionService.getQuestionById(questionId);
        User user = userService.getCurrentAuthenticatedUser();

        if (!hasUserAuthority(question))
            throw new NoAuthorityException("You don't have permission!");

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setMessage(dto.getMessage());

        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Long answerId, CreateUpdateAnswerDto dto) {
        Answer answer = getAnswerById(answerId);

        if (!hasUserAuthority(answer.getQuestion()))
            throw new NoAuthorityException("You don't have permission!");

        answer.setMessage(dto.getMessage());

        return answerRepository.save(answer);
    }

    public String deleteAnswer(Long answerId) {
        Answer answer = getAnswerById(answerId);

        if (!hasUserAuthority(answer.getQuestion()))
            throw new NoAuthorityException("You don't have permission!");

        answerRepository.delete(answer);

        return "Answer successfully deleted!";
    }


    private boolean hasUserAuthority(Question question) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        return question.getProduct().getStore().getOwners().contains(currentUser) || userService.isCurrentAuthenticatedUserAdmin();
    }
}