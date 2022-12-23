package com.bitstudy.app.domian;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/** Article 과 ArticleComment 에 있는 공통 필드(created_at,created_by 등 메타데이터)들을 별도로 빼서 관리할것임
 *  이유는 앞으로 Article 과 ArticleComment 처럼 fk 같은 것으로 엮여 있는 테이블들을 만들경우, 모든 domain 안에 있는 파일들에 많은 중복코드들이 들어가게 된다.
 *  그래서 별도의 파일에 공통되는 것을들 다 모아서 사용 하는 것 해보기 !!
 *  참고 : 공통 필드를 빼는것은 회사마다 다르다
 *        중복 코드를 싫어하여 한 파일에 몰아 두는 회사가 있고
 *        유지보수의 이점이 있다
 *
 *        중복 코드를 괜찮아서 각 파일에 두는 회사가 있다.
 *        각 파일에 모든 정보를 가지고 있다. 변경 시 유연하게 코드를 수정 할 수 있다.
 *   추출은 두가지 방법으로 할 수 있다.
 *   1. @Embedded - 공통되는 필드들을 하나의 클래스로 만들어서 @Embedded 있는곳에서 치환 하는 방식
 *   2. @MappedSupperClass - 요즘 실무에서 사용하는 방식
 *                           @MappedSupperClass 어노테이션이 붙은 곳에서 사용
 *   차이점 : 사실 두가지 비슷하지만 @Enbedded 방식을 하게 되면 필드 하나 추가된다
 *           영속성 컨텍스트를 통해 데이터를 넘겨 받아 어플리케이션으로 열었을 때에는 어차피 AuditingFields 와 같이 보인다.
 *           중간에 한단계가 더 생긴거와 같음
 *
 *           @MappedSupperClass 는 표준 JPA 에서 제공해주는 클래스 중간단계없이 바로 동작하는것 */

//@EntityListeners(AuditingEntityListener.class) // 이거 없으면 테스트할때 created_at 때문에 에러발생(Ex04파일)
@Table(indexes = {
    @Index(columnList = "title"),
    @Index(columnList = "hashtag"),
    @Index(columnList = "created_at"),
    @Index(columnList = "created_by")
})
@Entity
@Getter
@ToString
public class Ex06_1_Article_공통필드_분리하기 extends AuditingFields {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@Setter @Column(nullable = false) private String title;
@Setter @Column(nullable = false, length = 10000) private String content;
@Setter private String hashtag;

@OrderBy("id")
@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)

@ToString.Exclude
private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

/*****************************************************************************/
        /** 1. Embedded 방식
         class Tmp{
             @CreatedDate@Column(nullable = false) private LocalDateTime created_at;
             @CreatedBy@Column(nullable = false, length = 100) private String created_by;
             @LastModifiedDate@Column(nullable = false) private LocalDateTime modified_at;
             @LastModifiedBy@Column(nullable = false, length = 100) private String modified_by;
         }
         @Embedded Tmp tmp; */
        /** 2. auditingFields.java 필드 복사*/
//    @CreatedDate
//    @Column(nullable = false)
//    private LocalDateTime created_at;
//
//    @CreatedBy
//    @Column(nullable = false, length = 100)
//    private String created_by;
//
//    @LastModifiedDate
//    @Column(nullable = false) private LocalDateTime modified_at;
//
//    @LastModifiedBy
//    @Column(nullable = false, length = 100) private String modified_by;
/*****************************************************************************/



protected Ex06_1_Article_공통필드_분리하기() { }

private Ex06_1_Article_공통필드_분리하기(String title, String content, String hashtag) {
    this.title = title;
    this.content = content;
    this.hashtag = hashtag;
}

public static Ex06_1_Article_공통필드_분리하기 of(String title, String content, String hashtag){
    return new Ex06_1_Article_공통필드_분리하기(title, content, hashtag);
}

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ex06_1_Article_공통필드_분리하기 article = (Ex06_1_Article_공통필드_분리하기) o;
    return id.equals(article.id);
}

@Override
public int hashCode() {
    return Objects.hash(id);
}

}
