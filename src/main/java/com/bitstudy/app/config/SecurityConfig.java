package com.bitstudy.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*  spring security 를 설치하고 실행하면 어떤 url 로 접근을 하면 무조건 로그인 화면으로 넘어간다.
*   DefaultLoginPageGeneratingFilter 검색 하면 해당 파일 맨위에 /login 이라고 있음
*   아직은 로그인 기능이 없으니까 요청하는 url 대로 화면에 나타나게 할것이다.
*
*   그래도 login 페이지가 없어지는것은 아니다. 주소 넣으면 나오긴 한다.
*   http://localhost:8080/articles
*   http://localhost:8080/login
* */

@Configuration  // 설정파일 등록하는 애너테이션
@EnableWebSecurity // 블로그나 교재에는 무조건 넣으라고 하지만 이제는 안넣어도 된다(auto-configration 에 들어가있다.)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /** HttpSecurity : 세부적인 보안 기능을 설정할 수 있는 api 제공
         *                 URL 접근 권한 설정
         *                 커스텀 로그인 페이지 지원
         *                 인증 후 성공 / 실패 핸들링
         *                 사용자 로그인 / 로그아웃
         *                 CSRF 공격으로부터 보호

         */


        return http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
        // HttpSecurity 의 authorizeHttpRequests 에서 모든 요청(anyRequest() 부분)이 인증을 허용(permitAll())하겠다
                .formLogin() // 로그인 페이지를 만들고
                .and().build(); // 빌드 해라

        /**  formLogin() 이후 사용 할 수 있는 메서드
         *      http.formLogin()
         *              .loginPage("/login.html")               로그인 페이지 보여줄때
         *              .defaultSuccessUrl("index")             로그인 성공 후 이동할 페이지 경로
         *              .failureUrl("/login.html?error=true")   로그인 실패 후 이동할 페이지 경로
         *              .usernameParameter("아이디(유저네임)")     로그인 이후 아이디 파라미터명 설정 */
        // 이후 다시 브라우저 가서 http://localhost:8080/articles 접속 확인
    }
}
