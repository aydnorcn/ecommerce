package com.orcnaydn.ecommerce.repository;

import com.orcnaydn.ecommerce.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAll(Specification<Question> specs, Pageable pageable);
}
