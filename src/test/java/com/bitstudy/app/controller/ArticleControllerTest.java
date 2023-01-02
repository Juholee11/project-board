package com.bitstudy.app.controller;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
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
class ArticleControllerTest {
    private final MockMvc mvc;

    public ArticleControllerTest(@Autowired MockMvc mvc){
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
    2. /articles/{article-ic}      GET 개시글 페이지
    3. /articles/search            GET 게시판 검색 전용 페이지
    4. /articles/search-hashtag    GET 게시판 해시태그 검색 전용 페이지*/

}