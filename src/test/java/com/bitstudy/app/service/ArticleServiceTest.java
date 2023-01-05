package com.bitstudy.app.service;

import com.bitstudy.app.repository.ArticleRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//import static org.junit.jupiter.api.Assertions.*; Junit 사용 안할 예정

import org.assertj.core.api.Assertions;

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


}