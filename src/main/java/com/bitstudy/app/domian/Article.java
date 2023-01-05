package com.bitstudy.app.domian;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "created_at"),
        @Index(columnList = "created_by")
})
@Entity
@Getter
@ToString(callSuper = true) // 상위 (UserAccount) 에 있는 toString 까지 출력 핧 수 있도록  callSuper 적용
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private UserAccount userAccount;


    @Setter @Column(nullable = false) private String title;
    @Setter @Column(nullable = false, length = 10000) private String content;
    @Setter private String hashtag;

//    @OrderBy("id")
    @OrderBy("created_at desc") // 댓글리스트를 최근 시간것으로 정렬되도록 바꿈
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)

    @ToString.Exclude
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

/*****************************************************************************/

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



    protected Article() { }

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag){
        return new Article(userAccount, title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
