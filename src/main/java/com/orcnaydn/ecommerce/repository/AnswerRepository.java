package com.orcnaydn.ecommerce.repository;

import com.orcnaydn.ecommerce.entity.Answer;
import com.orcnaydn.ecommerce.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {

    List<Answer> findAllByQuestion(Question question);
}