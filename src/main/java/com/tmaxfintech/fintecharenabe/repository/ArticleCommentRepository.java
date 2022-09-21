package com.tmaxfintech.fintecharenabe.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.tmaxfintech.fintecharenabe.domain.Article;
import com.tmaxfintech.fintecharenabe.domain.ArticleComment;
import com.tmaxfintech.fintecharenabe.domain.QArticle;
import com.tmaxfintech.fintecharenabe.domain.QArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticleComment> // 여기에 QClass 를 넣어야함. 스펙상 이게 옳음.
{
    List<ArticleComment> findByArticle_Id(Long articleId);
    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root) { // 검색 필터
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content, root.createdAt, root.createdBy);
        bindings.bind(root.content).first((StringExpression::containsIgnoreCase)); // like '%{v}%', index 를 타지 못할 수도 있음
        bindings.bind(root.createdAt).first((DateTimeExpression::eq)); // like '%{v}%', index 를 타지 못할 수도 있음
        bindings.bind(root.createdBy).first((StringExpression::containsIgnoreCase)); // like '%{v}%', index 를 타지 못할 수도 있음
    }
}
