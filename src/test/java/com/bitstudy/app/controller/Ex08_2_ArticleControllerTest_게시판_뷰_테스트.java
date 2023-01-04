package com.bitstudy.app.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** 하기전에 알아둘것!! : 이 테스트 코드를 작성하고 돌리면 결과적으로 404 에러 발생한다.
 *                      이유로는 ArticleController 에 작성된 내용이 없고 dao 같은 것들도 없기 때문이다.
 *  우선 작성하고 실제코드 (ArticleController) 와 연결되는지 확인
 *
 *  슬라이스 테스트 방식으로 진행 예정
 *                      */
/* autoConfigration 을 가져올 필요가 없기 때문에 슬라이스 테스트 사용 가능   */
//@WebMvcTest // 이렇게만 사용하면 모든 컨트롤러를 다 읽어들인다
@WebMvcTest(ArticleController.class) // 지금은 컨트롤러 디렉토리에 파일이 하나만 있어 상관없지만 많아지면 모든 컨트롤러들을 Bean 으로 읽기때문에 이와같이 사용
@DisplayName("view 컨트롤러 - 게시글")
class Ex08_2_ArticleControllerTest_게시판_뷰_테스트 {
    private final MockMvc mvc;

    public Ex08_2_ArticleControllerTest_게시판_뷰_테스트(@Autowired MockMvc mvc){
        this.mvc = mvc;
    }
    /*
    테스트는 엑섹 api 에 있는 순서대로 만들예정
    1. 게시판 리스트 페이지
    2. 게시글 상세 페이지
    3. 게시판 검색 전용
    4. 해시태그 검색 전용 페이지

    엑섹 api 에 보면 정의해놓은 view 부분에 url 이 있다. 그것보면서 하기
    1. /articles                   GET 게시판 페이지
    2. /articles/{article-id}      GET 개시글 페이지
    3. /articles/search            GET 게시판 검색 전용 페이지
    4. /articles/search-hashtag    GET 게시판 해시태그 검색 전용 페이지*/

    // 1. 게시판 리스트 페이지
    @Test
    public void articlesAll() throws Exception {
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                // 뷰를 만들고 있으니까 html 로 코드를 짜고 있을거입. /articles 로 받아온 데이터의 미디어 타입이 HTML 타입으로 되어있는지 확인
                // contentType 의 경우 exat match 라서 미디어 타입이 딱 text/html 로 나오는것만 허용 하기 때문에
                // contentTypeCompatibleWith 를 이용하여 호환되는 타입까지 맞다고 해주는것

                .andExpect(view().name("articles/index"))
                // 가져온 뷰 파일명이 index 인지 확인

                .andExpect(model().attributeExists("articles"));
                // 해당 뷰에서 게시글들이 떠야 하는데 그 말은 서버에서 데이터들을 가져왔다는 말이다. 그러면 모델 어트리뷰트로
                // 데이터를 밀어 넣어줬다는 말인데 그게 있는지 없는지 확인
                // model().attributeExists("articles") >articles 는 개발자가 임의로 걸어주는 키값, 맵에 articles 라는 키가 있는지 검사해라 라는 의미
    }
    // 2. 게시글 상세 페이지
    @Test
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상호출")
    public void articlesOne() throws Exception {
        mvc.perform(get("/articles/1"))/* 테스트니까 그냥 1번 글 가져와라 */
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));
                // 상세 페이지에는 댓글들도 같이 오니까 모델 어트리뷰트에 articleComments 키가 있는지 확인
    }

    // 3. 게시판 검색 전용
    @Test
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상호출")
    public void articleSearch() throws Exception {
        mvc.perform(get("/articles/search"))/* 테스트니까 그냥 1번 글 가져와라 */
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search"));
    }

    // 4. 해시태그 검색 전용 페이지
    @Test
    @DisplayName("[view][GET] 게시글 해시태그 전용 페이지 - 정상호출")
    public void articleSearchHashtag() throws Exception {
        mvc.perform(get("/articles/search-hashtag"))/* 테스트니까 그냥 1번 글 가져와라 */
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/search-hashtag"));
    }
}