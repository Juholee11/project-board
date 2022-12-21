package com.bitstudy.app.domian;

import java.time.LocalDateTime;

public class Ex00_2_ArticleComment {

    private Long id;

    private Article article; // Article 파일에 내용을 여기에 넣는 작업 article.id 이런식으로 가져다 쓸 수 있다
/*  연관 관계 매핑
    연관 관계 없이 만든다면 -> private Long articleId; 이런식으로(관계형 데이터베이스 스타일) 하면 되지만
    현재 우리는 Article 와 ArticleComment 가 관계를 맺고 있는것을 객체 지향적으로 표현하려고 이렇게 사용한다(JPA 이용)
 */

    private String content; //본문
    private String hashtag; //해시태그

    //메타데이터(개발자가 보는 데이터를 의미
    private LocalDateTime created_at; //생성일시
    private String created_by; // 생성자
    private LocalDateTime modified_at; // 수정일시
    private String modified_by; // 수정자
}