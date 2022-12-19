package com.bitstudy.app.domian;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String title; //제목
    private String content; //본문
    private String hashtag; //해시태그

    //메타데이터(개발자가 보는 데이터를 의미
    private LocalDateTime createdAt; //생성일시
    private String createdBy; // 생성자
    private LocalDateTime modifiedAt; // 수정일시
    private String modifiedBy; // 수정자
}
