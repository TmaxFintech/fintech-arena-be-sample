package com.tmaxfintech.fintecharenabe.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시")
@WebMvcTest(ArticleController.class) // 해당 컨트롤러만 입력받음 (경량화)
class ArticleControllerTest {

    private final MockMvc mvc;

    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

//    @Disabled("구현중") // 통과하는 테스트가 아니므로 build 가 안될 것이므로 제외함
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    @Test
    public void givenNoting_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index")) // 디렉토리 위치와 파일명
                .andExpect(model().attributeExists("articles")); // 뷰는 데이터가 있어야함. (미리 예상하고 테스트 작성)
    }

    @Disabled("구현중")
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNoting_whenRequestingArticlesView_thenReturnsArticleView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article")) // 뷰는 데이터가 있어야함. (미리 예상하고 테스트 작성)
                .andExpect(model().attributeExists("articleComments")); // 뷰는 데이터가 있어야함. (미리 예상하고 테스트 작성)

    }

    @Disabled("구현중")
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    public void givenNoting_whenRequestingArticleSearchView_thenReturnsArticlesView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search"));

    }

    @Disabled("구현중")
    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void givenNoting_whenRequestingArticleHashtagSearchView_thenReturnsHashtagSearchView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search-hashtag"));

    }
}
