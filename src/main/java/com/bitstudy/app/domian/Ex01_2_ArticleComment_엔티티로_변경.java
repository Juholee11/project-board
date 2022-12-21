package com.bitstudy.app.domian;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/*  할일 : Lombok 사용하기
  ** 주의 : maven 때와 같은 방식인 것들도 이름이 다르게 되어있으니 헷갈리지않게 주의할 것

    * 순서
    1. Lombok 을 이용해서 클래스를 엔티티로 변경
    2. 간단한 getter/setter, toString 등의 롬북 어노테이션 사용
    3. 동등성. 동일성 비교할 수 있는 코드 넣어보기
    * 여기는 DB 만드는거임

*/
/*  @Table : 테이블과 매핑 한다는 의미
    JPA 가 관리한다.
    PK 키 알아볼 수 있게 필드 중 하나에 @ Id 어노테이션 달아줘야한다.

*/
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "created_at"),
        @Index(columnList = "created_by")
})
@Getter
@ToString
@Entity
public class Ex01_2_ArticleComment_엔티티로_변경 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false) private Article article; // Article 파일에 내용을 여기에 넣는 작업 article.id 이런식으로 가져다 쓸 수 있다
//    @Setter @Column(nullable = false) private Long articleId;
    /*  연관 관계 매핑
    연관 관계 없이 만든다면 -> private Long articleId; 이런식으로(관계형 데이터베이스 스타일) 하면 되지만
    현재 우리는 Article 와 ArticleComment 가 관계를 맺고 있는것을 객체 지향적으로 표현하려고 이렇게 사용한다(JPA 이용)
    그러기 위해서 필요한 것은 단방향 이라는 뜻의 어노테이션인 @ManuToOne 을 써주고,
    이건 필수 값이다 라는 의미로 (optional = false) 을 선언한다.
    댓글은 여러개 : 게시글은 1개 이기에 단방향 방식을 사용 (n:1)
 */

    @Setter
    @Column(nullable = false, length = 500)
    private String content; //본문


    //메타데이터(개발자가 보는 데이터를 의미
    @CreatedDate
    @Column(nullable = false) private LocalDateTime created_at; //생성일시

    @CreatedBy
    @Column(nullable = false, length = 100)private String created_by; // 생성자

    @LastModifiedDate
    @Column(nullable = false)private LocalDateTime modified_at; // 수정일시

    @LastModifiedBy
    @Column(nullable = false, length = 100)private String modified_by; // 수정자
}