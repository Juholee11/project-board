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
import java.util.Objects;

/*  할일 : Lombok 사용하기
  ** 주의 : maven 때와 같은 방식인 것들도 이름이 다르게 되어있으니 헷갈리지않게 주의할 것

    * 순서
    1. Lombok 을 이용해서 클래스를 엔티티로 변경
    2. 간단한 getter/setter, toString 등의 롬북 어노테이션 사용
    3. 동등성. 동일성 비교할 수 있는 코드 넣어보기
    * 여기는 DB 만드는거임

    JPA 란? 자바 ORM 기술 표준
    Entity 를 분석, crate 구문이나 insert 같은 sql 쿼리를 생성해준다.
    JDBC API 넣어주면 DB 접근도 해주고 객체와 테이블을 매핑해준다.
    *
*/

/* @Table : 엔티티와 매핑할 정보를 지정하고,
    사용법 : 정식 방법 > @Index(name = "원하는 명칭" , columnList = "사용 할 테이블명")
                    > name 생략 시, 원래 이름으로 사용한다
    @Index : 데이터베이스 인덱스는 추가, 쓰기 및 저장공간을 희생하여 테이블에 대한 데이터 검색 속도를 향상시키는 데이터 구조이다.
            @Entity 와 세트로 사용된다.
*/
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "created_at"),
        @Index(columnList = "created_by")
})
//@Entity /* 1. Lombok 을 이용해서 클래스를 엔티티로 변경 */
@Getter /* 2. Lombok 을 이용해서 @Getter 를 사용하면 알아서 모든 필드의 getter 가 생성된다 */
@ToString /* 2. Lombok 을 이용해서 @ToString 를 사용하면 알아서 모든 필드의 toString 가 생성된다 */
public class Ex01_1_Article_엔티티로_변경 {

    @Id // 전체 필드중에서 어떤 필드가 PK 인지 선언하는것. 이 부분이 없으면 @Entity 에서 에러 발생한다. @Entity 가 붙은 클래스는 JPA 가 관리하게 된다.
    //기본 키(PK)가 무엇인지 알려줘야 작동한다. 그것이 @ID 어노테이션이다. (@GeneratedValue 이 두개는 한세트라고 생각해야한다)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 필드가 autoIncrement 인 경우 해당 어노테이션을 사용하여 자동으로 값이 생성되게 해야한다. @Id 와 세트이다. ****기본키 전략이라고 불린다.
    private Long id;


/*  @Setter 도 @Getter 처럼 클래스 단위로 걸 수 있는데, 그렇게 할 시 모든 필드에 접근이 가능해지기때문에
    id 와 메타데이터의 경우는 내가 부여하는것이 아니라 JPA 에서 자동으로 부여하는 번호이다.
    메타 데이터들도 자동으로 JPA 가 세팅하게 만들어야 한다. 그래서 id 와 메타데이터는 @Setter 가 필요없다. @Setter 의 경우는 지금처럼 필요한 필드에만 사용할 것을 권장한다.
*/

/*  @Column : 해당 컬럼이 not null 인 경우 nullable = false 사용 (기본값은 true 이다)
    @Column 을 아예 안쓰면 null 이다.
    @Column(name="DESC", nullable=false, length=512) 생략 가능하다 길이 숫자 안쓰면 기본값 255 자동 적용
*/
    @Setter @Column(nullable = false) private String title; //제목
    @Setter @Column(nullable = false, length = 10000) private String content; //본문
    @Setter private String hashtag; //해시태그

    /*  양방향 바인딩 (Article - ArticleComment)
    */







/*  JPA auditing : jpa 에서 자동으로 세팅하게 해줄 때 사용 가능
                    사용 하려면 config 파일이 별도로 있어야 한다.
                    config 패키지 만들어서 JpaConfig 클래스 생성하기
*/

    //메타데이터(개발자가 보는 데이터를 의미)
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime created_at; //생성일시

    @CreatedBy
    @Column(nullable = false, length = 100)
    private String created_by; // 생성자 JpaConfig 사용 ( " bitstudy ") > 로그인 페이지를 하직 구현 하지 않았기 때문에 사용
    // 다른 생성일 시 같은것들은 알아낼 수 있지만 최초 생성자는 (현재코드상태)인증 받고 오지 않았기 때문에 따로 알아낼 수 없다.
    // 이때 아까만든 jpaConfig 파일을 사용한다( " bitstudy ")

    @LastModifiedDate
    @Column(nullable = false) private LocalDateTime modified_at; // 수정일시

    @LastModifiedBy
    @Column(nullable = false, length = 100) private String modified_by; // 수정자 JpaConfig 사용 (" bitstudy ")

    /*  위와 같이 어노테이션을 붙여주기만 하면 audititng 이 작동한다.
        @CreatedDate : 최초에 insert 할때 자동으로 한번 넣어준다.
        @CreatedBy : 최초에 insert 할때 자동으로 한번 넣어준다.
        @LastModifiedDate : 작성 당시의 시간을 실시간으로 넣어준다.
        @LastModifiedBy : 작성 당시의 이름을 실시간으로 넣어준다.
    */


    /** entity 를 만들때는 무조건 기본 생성자가 필요하다.
     * 퍼블릭 또는 프로텍티드만 가능한데 평생 아무데서도 기본생성자를 안쓰이게 하고싶어 protected 로 변경하였다
     * public(제한 X) > protected(동일 패키지 내 또는 파생 클래스) > default(동일한 패키지 내) > private(자기자신의 클래스 내)
    */
    protected Ex01_1_Article_엔티티로_변경() { }

    /*  사용자가 입력하는 값만 받기. 나머지는 시스템이 다 알아서 하게 해주면 된다.
    */
    private Ex01_1_Article_엔티티로_변경(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Ex01_1_Article_엔티티로_변경 of(String title, String content, String hashtag){

        return new Ex01_1_Article_엔티티로_변경(title, content, hashtag);
    }
    /** 정적 팩토리 메서드 (factory method pattern 중에 하나이다)
     *  객체 생성의 역할을 하는 클래스 메서드 라는 의미이다.
     *  of 메서드를 이용하여 위에있는 private 생성자를 직접적으로 사용해서 객체를 생성하게 하는 방법
     *  중요!!!!!!!!무조건 static 으로 놓아야 한다
     *  장점 : static 이기 때문에 new 를 이용해서 생성자를 만들지 않아도 된다.
     *        return 을 가지고있기 때문에 상속 할 때 값을 확인할 수 있다.(하위 자료형 객체를 반환할 수 있다)
     *        (중요) 객체 생성을 캡슐화 할 수 있다.
     */

    /*  *************엄청 어려운 개념임 개빡셈**************
        만약에 Article 클래스를 이용해서 게시글들을 리스트에 담아서 화면을 구성 할 건데, 그것을 하기위해서 Collection 을 이용해야 한다.
        Collection : 객체의 모음(그룹)
                     자바가 제공하는 최상위 컬렉션(인터페이스)
                     하이버네이트는 중복을 허용하고 순서를 보장하지 않는다고 가정
        - 하위 -
        Set : 중복 허용 X , 순서 보장안됨
        List : 중복 허용 O , 순서 있음
        Map : key , value 구조로 되어있는 특수 컬렉션

        List 에 넣거나 또는 List 에 있는 중복요소를 제거하거나, 정렬 할 경우 비교를 할 수 있어야 하기 때문에
        동일성 , 동등성 비교를 할 수 있는 equals , hashcode 를 구현 해야 한다.

        모든 데이터들은 비교해도 되지만, 다 불러와서 비교하면 느려질 수 있기때문에
        사실 id 만 같으면 두 엔티티가 같다는 의미 이니 id 만 가지고 비교하는걸 구현하다.

        alt + insert > equals and hashcode 선택 > 체크박스 여러개 나오는데 id만 계속 선택 후 생성하기
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ex01_1_Article_엔티티로_변경 article = (Ex01_1_Article_엔티티로_변경) o;
        return id.equals(article.id);
        // 다른 방법들
//        return (article.id).equals(id);
//        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /*  == 와 equals 와 hashcode 차이점
        == : 동일성 비교 / 값이랑 주소값까지 비교한다.
        equals : 동등성 비교 / 값만 비교한다.
        hashcode : 객체를 실벽하는 Integer 값.
                   객체가 가지고있는 데이터를 특정 알고리즘으로 적용해서 계산된 정수값을 hashcode 라 한다.
                   사용하는 이유는 객체를 비교할 때 들어가는 비용(시간)이 낮다.
        자바에서 2개의 객체를 비교할때에는 equals()를 사용하는데 여러객체를 비교 할 때에는 equals()를 사용하면 Integer 를 비교하는데 많은 시간이 소요된다.
    */
}
