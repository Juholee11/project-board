package com.bitstudy.app.config;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {

    /** @Bean 영역에 thymeleaf3Properties 와
        @ConfigurationProperties 부분에 빨간 밑줄이 생성된다.
        빨간 밑줄을 없애기 위해 main 파일로 접근(AppApplication.java) > @ConfigurationPropertiesScan 어노테이션 넣기 */

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
            /** thymeleafTemplateResolver 라는 반을 등록하는데 리턴타입은 SpringResourceTemplateResolver
             *  그런데 이게 파임리프 auto-configration 을 불렀을 때 자동으로 잡힌다. 그런데 decoupledLogic 을 세팅하는건
             *  이미 만들어져있다.(외부 프로퍼티라서 인식을 못할 뿐이다) 그래서 인식 할 수 있게 allication.yaml 가서 thymeleaf 를 별도로
             *  등록해야한다.
             *  */
    ) {

        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());
        return defaultTemplateResolver;
    }


    @RequiredArgsConstructor
    @Getter
    @ConstructorBinding
    @ConfigurationProperties("spring.thymeleaf3")
    public static class Thymeleaf3Properties {
        private final boolean decoupledLogic;
    }
}
