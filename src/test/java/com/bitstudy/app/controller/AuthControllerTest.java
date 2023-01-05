package com.bitstudy.app.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** 로그인 관련된 기능 테스트를 위한 페이지
 *  로그인 페이지는 스프링 시큐리티와 부트가 만들어서 준것이다.
 *  한마디로 이미 테스트가 다 검증이 되어 별도로 테스트할 것은 없다.
 *  최소한으로만 테스트 예정
 *  저 기능이 우리 서비스에 존재하는지만 확인 할 것 .
 *  별도로 실제 AuthController 파일이 존재 하지 않아도 된다.
 *
 *  예상 시나리오 : get("/login") 로 페이지를 날렸을 때 상태 코드가 200나오면 패스 통과 */
@Import(SecurityException.class)//AtricleControllerTest 와 동일한 환경에서 테스트 하기 위해 이것을 넣었음
@WebMvcTest
public class AuthControllerTest {

    private final MockMvc mvc;

    public AuthControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    @DisplayName("[view][GET] 로그인 페이지 - 정상호출")
    public void loginPass() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}
