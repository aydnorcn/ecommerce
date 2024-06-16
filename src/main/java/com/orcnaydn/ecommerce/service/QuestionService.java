package com.orcnaydn.ecommerce.service;

import com.orcnaydn.ecommerce.dto.PageResponseDto;
import com.orcnaydn.ecommerce.dto.question.CreateUpdateQuestionDto;
import com.orcnaydn.ecommerce.entity.Product;
import com.orcnaydn.ecommerce.entity.Question;
import com.orcnaydn.ecommerce.entity.User;
import com.orcnaydn.ecommerce.exception.NoAuthorityException;
import com.orcnaydn.ecommerce.exception.ResourceNotFoundException;
import com.orcnaydn.ecommerce.filter.QuestionFilter;
import com.orcnaydn.ecommerce.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final ProductService productService;

    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Question not found!"));
    }

    public PageResponseDto<Question> getQuestions(UUID userId, UUID productId, int pageNo, int pageSize) {
        User user = userService.getUserIfExists(userId);
        Product product = productService.getProductIfExists(productId);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Specification<Question> specification = QuestionFilter.filter(user, product);
        Page<Question> questions = questionRepository.findAll(specification, pageable);

        return new PageResponseDto<>(questions);
    }

    public Question createQuestion(UUID productId, CreateUpdateQuestionDto dto) {
        User user = userService.getCurrentAuthenticatedUser();
        Product product = productService.getProductById(productId);

        return questionRepository.save(initalizeQuestion(user, product, dto));
    }

    public String deleteQuestion(Long questionId) {
        Question question = getQuestionById(questionId);

        if (!hasUserAuthority(question))
            throw new NoAuthorityException("You don't have permission!");

        questionRepository.delete(question);

        return "Question successfully deleted!";
    }


    private boolean hasUserAuthority(Question question) {
        User currentUser = userService.getCurrentAuthenticatedUser();
        return question.getUser().equals(currentUser) || userService.isCurrentAuthenticatedUserAdmin();
    }

    private Question initalizeQuestion(User user, Product product, CreateUpdateQuestionDto dto) {
        return Question.builder()
                .user(user)
                .product(product)
                .question(dto.getQuestion()).build();
    }
}