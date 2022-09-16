package com.tmaxfintech.fintecharenabe.repository;

import com.tmaxfintech.fintecharenabe.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
