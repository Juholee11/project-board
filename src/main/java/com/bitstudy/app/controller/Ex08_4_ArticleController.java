package com.bitstudy.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/*  뷰 엔드포인트 관련 컨트롤러
    엑섹 api 에 보면 정의해놓은 view 부분에 url 이 있다. 그것보면서 하기
    /articles                   GET 게시판 페이지
    /articles/{article-ic}      GET 개시글 페이지
    /articles/search            GET 게시판 검색 전용 페이지
    /articles/search-hashtag    GET 게시판 해시태그 검색 전용 페이지



    - thymeleaf
    뷰 파일은 html 로 작업 예정인데, 타임리프를 설치 함으로써 스프링은 이제 html 파일을 마크업으로 보지않고 타임리프 템플릿 파일로 인식한다.
    따라서 이 html 파일을 아무데서나 작성 할 수 없고 resources > templates 폴더 안에서만 작성이 가능하다.
    그 외에 css 나 image, js 들은 resources > static 폴더 안에 작성 가능

    */
@Controller
@RequestMapping("/articles") // 모든 경로들은 /articles 로 시작하니까 클래스 레벨에 1차로 @RequestMapping("/articles") 걸어놓음
public class Ex08_4_ArticleController {

    @GetMapping
    public String articles(ModelMap map){
        /* ModelMap : 테스트 파일에서 .andExpect(model().attributeExists("artivles")); 를 이용하여 articles 라는 키값으로 데이터를 넣어주기로하여 필요
        *
        * Model , ModelMap 은 같은거임
        * Model 은 인터페이스
        * ModelMap 은 클래스(구현체)*/
        map.addAttribute("articles", List.of());// 키: articles, 값 : 그냥 List
        return "articles/index";
    }

}
