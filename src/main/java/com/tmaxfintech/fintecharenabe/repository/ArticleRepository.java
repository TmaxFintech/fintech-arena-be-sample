package com.tmaxfintech.fintecharenabe.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.tmaxfintech.fintecharenabe.domain.Article;
import com.tmaxfintech.fintecharenabe.domain.QArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>, // 검색 기 (정확히 매칭되어야 검색됨. 부분 검색 안됨)
        QuerydslBinderCustomizer<QArticle> // 여기에 QClass 를 넣어야함. 스펙상 이게 옳음.
{
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) { // 검색 필터
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
        bindings.bind(root.title).first((StringExpression::containsIgnoreCase)); // like '%{v}%', index 를 타지 못할 수도 있음
        bindings.bind(root.content).first((StringExpression::containsIgnoreCase)); // like '%{v}%', index 를 타지 못할 수도 있음
        bindings.bind(root.hashtag).first((StringExpression::containsIgnoreCase)); // like '%{v}%', index 를 타지 못할 수도 있음
        bindings.bind(root.createdAt).first((DateTimeExpression::eq)); // like '%{v}%', index 를 타지 못할 수도 있음
        bindings.bind(root.createdBy).first((StringExpression::containsIgnoreCase)); // like '%{v}%', index 를 타지 못할 수도 있음
//        bindings.bind(root.title).first((StringExpression::likeIgnoreCase)); // like '${v}'
    }
}
