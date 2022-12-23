package com.bitstudy.app.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
        슬라이스 테스트 : 기능별(레이어별) 잘라서 특정 부분(기능)만 테스트 할 수 있는 것

    - 통합 테스트 에너테이션
        @SpringBootTest - 스프링이 관리하는 모든 빈을 등록시켜 테스트 진행하기 때문에 무겁다(느리다)
                          * 테스트 할때 가볍게 하기 위해 @WebMvcTest 를 사용해서 Web 레이어 관련 빈들만 등록한 상태로 테스트 할 수 있다.
                            단, web 레이어 관련된 빈들만 등록되므로 Service 는 등록되지 않는다. 따라서 Mock 관련 어노테이션을 이용하여
                            가짜로 만들어줘야 한다.

    - 슬라이스 테스트 에너테이션
        1. @WebMvcTest - 슬라이스 테스트에서 대표적인 어노테이션
                         Controller 를 테스트 할 수 있도록 관련 설정을 제공한다.
                         @WebMvcTest 를 선언하면 web 과 관련된 Bean 만 주입되고, MockMvc 를 알아볼 수 있게 된다.
                         *MockMvc 는 웹 어플리케이션을 어플리케이션 서버에 배포 하지 않고, 가짜로 테스트용 MVC 환경을 조성하여
                         요청 및 전송, 응답기능을 제공해주는 유틸리티 클래스이다.

                         간단히 얘기하자면, 내가 컨트롤러 테스트를 하고 싶을 때 실제 서버에 올리지 않고 테스트용으로 시뮬레이션 하여
                         MVC가 되도록 해주는 클래스.
                         그냥 컨트롤러 슬라이스 테스트 한다고 하면 @WebMvcTest 랑 @MockMvc 사용하면 된다.

        2. @DataJpaTest - JPA 레포지터리 테스트 할 때 사용
                          @Entity 가 있는 엔티티 클래스들을 스캔하여 테스트를 위한 JPA 레포지터리들을 설정
                          단, @Component 나 @ConfigurationProperties Bean 들은 무시한다.
        3. @RestClientTest - (클라이언트 입장에서) API 연동 테스트
                             테스트 코드 내에서 Mock 서버를 띄울 수 있다.(response, request 에 대한 사전 정의가 가능)
 */
@WebMvcTest
public class DataRestTest {
    /** MockMvc 테스트 방법
     1. MockMvc 생성(빈 준비)
     2. MovkMvc 에게 요청에 대한 정보를 입력(주입)
     3. 요청에 대한 응답값을 expect 를 이용해서 테스트
     4. expect 다 하면 테스트 통과
     */
    private final MockMvc mvc;          //1. MockMvc 생성(빈 준비)


    public DataRestTest(@Autowired MockMvc mvc) {  //2. MovkMvc 에게 요청에 대한 정보를 입력(주입)
        this.mvc = mvc;
    }

    // [api] - 게시글 리스트 전체 조회
    @DisplayName("[api] - 게시글 리스트 전체 조회")
    @Test
    void articles() throws Exception {
        /* 현재 테스트는 실패하는 것이 정상이다
           해당 api 를 찾을 수 없다.
           콘솔창에 MockHttpServletRequest 부분에 URI="/api/articles 있을 것이다. 복사해서 브라우저에 http://localhost:8080/api/articles
           넣어보면 데이터가 나온다.

           왜 여기선 안되냐면 @WebMvcTest 는 슬라이스 테스트 이기 때문에 controller 외의 빈들은 로드하지 않았기 때문이다*/
        mvc.perform(get("/api/articles")) // get 입력 후 ctrl + space 하면 (이것을 딥다이브 라고함)
        .andExpect(status().isOk())//현재 200이 들어왔는지 //status > MockMvcResultMathcers.status
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
    }
}
