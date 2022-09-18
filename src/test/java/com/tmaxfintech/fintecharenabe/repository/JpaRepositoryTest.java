package com.tmaxfintech.fintecharenabe.repository;

import com.tmaxfintech.fintecharenabe.domain.Article;
import com.tmaxfintech.fintecharenabe.config.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("testdb")
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest // 생성자 주입이 아래와 같이 가능해짐
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;

        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        var articles = articleRepository.findAll();

        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        var previousCount = articleRepository.count();

        // When
        var savedArticle = articleRepository.save(Article.of("new article", "new content", "#tmaxfintech"));

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // Given
        var article = articleRepository.findById(1L).orElseThrow(); // 없으면 test 종료
        var updatedHashtag = "#tmaxcommerce";
        article.setHashtag(updatedHashtag);

        // When
        var savedArticle = articleRepository.saveAndFlush(article); // flush를 해줘야함. update query발생시킴, 메소드단위로 자동으로 트랜잭셔널 걸려있음 (@DataJpaTest). 기본값 롤백

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow(); // 없으면 test 종료
        var previousArticleCount = articleRepository.count(); // 게시글 수
        var previousArticleCommentCount = articleCommentRepository.count(); // 댓글 수
        var deletedCommentsSize = article.getArticleComments().size();

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }
}