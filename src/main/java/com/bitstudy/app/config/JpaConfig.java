package com.bitstudy.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/* @Configuration 이라고 선언하면 설정 파일을 만들기 위한 어노테이션으로 인식한다. or Bean 을 등록하기 위한 어노테이션
    사용 하면 JpaConfig 클래스는 Configuration bean 이 된다.
    간단하게 설명하면 @Configuration 선언하면 시스템이 "야 이거 설정파일이야 ~ Bean 으로 등록해줘" 라는 의미

 */
@Configuration

/*  JPA 에서 auditing 을 가능하게 하는 어노테이션
    Jpa auditing 이란 ? Spring Data jpa 에서 자동으로 값을 넣어주는 기능이다.
                        jpa 에서 자동으로 세팅하게 해줄때 사용하는 기능이다
                        특정 데이터를 보고있다가 > 생성, 수정이 되면 자동으로 값을 넣어주는 기능
*/
@EnableJpaAuditing
public class JpaConfig {
        // 사람 이름 정보 넣어주려고 만드는 config 설정
    @Bean
    public AuditorAware<String> auditorAware(){
        return() -> Optional.of("bitstudy");
        // 이렇게 세팅하면 앞으로 JPA 로 auditing 할 때 마다 모든 사람의 이름은 bitstudy 로 넣게된다.
        // 로그인 페이지를 하직 구현 하지 않았기 때문에 사용
        // TODO: 나중에 스프링 시큐리티로 인증기능 붙일때 수정이 필요하다
    }


}
