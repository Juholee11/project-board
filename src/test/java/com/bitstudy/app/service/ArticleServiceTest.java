package com.bitstudy.app.service;

import com.bitstudy.app.domian.type.SearchType;
import com.bitstudy.app.dto.ArticleDto;
import com.bitstudy.app.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mysema.commons.lang.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;// Junit 사용 안할 예정
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
//import static org.assertj.core.api.Assertions.*;
//import org.assertj.core.api.Assertions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//import static org.assertj.core.api.BDDAssertions.then;
//import static org.mockito.BDDMockito.given;

/**     서비스 비지니스 로직은 슬라이스 테스트 기능 사용 안하고 만들예정
        스프링부트 어플리케이션 컨셉 뜨는데 걸리는 시간을 없애려고 한다. (최대한 가볍게)
        디펜던시가 추가 되야 하는 부분에는 Mocking 을 하는 방식으로 한다.
        그래서 많이 사용하는 라이브러리가 mokito 라는것이 있다.(스프링 테스트 패키지에 내장되어있다)
        @ExtendWith(MockitoExtension.class)

 */

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    /* Mock 을 주입하는 거에다 @InjectMocks 을 달아줘야 한다. 그외에는 @Mock 을 달아준다 */
    @InjectMocks private ArticleService sut; // sut - system under test 약어. 실무에서 사용 되는 이름이다

    @Mock
    private ArticleRepository articleRepository; // 의존하는걸 ㄷ가져오야한다 (테스트 중간에 mocking 시 필요)

    /**  방법

             1. 검색
             2. 각 게시글 선택하면 해당 상세 페이지로 이동
             3. 페이지네이션
     */
    /* 1. 검색 */
    @DisplayName("검색어 없이 게시글을 검색 했을 경우, 게시글 리스트 반환하기")
    @Test
    void withoutKeywordreturnArticlesAll(){
        // Given - 페이지 기능을 넣기
        Pageable pageable = Pageable.ofSize(20); // 한 페이지에 몇개 가져올건지 결정하는것
//        given(articleRepository.findAll(pageable)).willReturn(Page.empty());
        //Pageable 은 org.spring ... 사용 / given 은 빨간 글 뜨면 org.mockito ... 선택

        // When - 검색어 내용 없는 경우(null) 실제 테스트 돌리는 부분이다
        Page<ArticleDto> articles = sut.searchArticles(null, null, pageable);

        // Then -
        assertThat(articles).isEmpty();
//        then(articleRepository).should().findAll(pageable);
    }

    @DisplayName("검색어 이용해서 게시글을 검색 했을 경우, 게시글 리스트 반환하기")
    @Test
    void withKeywordreturnArticlesAll(){
        // Given - 페이지 기능을 넣기
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(searchKeyword, pageable)).willReturn(Page.empty());

        // When - 검색어 내용 없는 경우(null) 실제 테스트 돌리는 부분이다
        Page<ArticleDto> articles = sut.searchArticles(searchType, searchKeyword, pageable);
        // Then -
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitleContaining(searchKeyword, pageable);

    }
}