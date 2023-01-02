package com.bitstudy.app.repository;

import com.bitstudy.app.domian.Article;
import com.bitstudy.app.domian.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/*
    QueryDsl 의 QuerydslPredicateExecutor 와 QuerydslBinderCustomizer 를 이용해서 검샛기능을 만들자
*/
@RepositoryRestResource
public interface Ex07_4_ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle> {// QuerydslBinderCustomizer는 QArticle 을 사용 하는데 이건 build.gralde 에서 queryDsl 을 build 하고 와야함
    // 설명 : QuerydslPredicateExecutor 는 Article 안에 있는 모든 필드에 대한 기본 검색 기능을 추가해준다.
        //      순서



    @Override
     default void customize(QuerydslBindings bindings, QArticle root){

        /* 1. 바인딩
            현재 QuerydslPredicateExecutor 때문에 Article 에 있는 모든 필드에 대한 검색이 열려있는 상태이다.
            하지만 우리가 원하는건 선택적 필드(제목, 본문, id , 글쓴이, 해시태그)만 검색에 사용되도록 하고 싶다.
            따라서 선택적으로 검색을 하게 하기 위해  bindings.excludeUnlistedProperties(true); 를 사용한다.
            excludeUnlistedProperties 는 리스팅을 하지않은 property 는 검색에 포함할지 말지 결정 할 수 있는 메서드이다.
            true 면 검색에서 제외, false 는 모든 프로퍼티를 열어주는 것
        */
        bindings.excludeUnlistedProperties(true);

        /* 2. 검색용(원하는) 필드를 지정(추가)하는 부분
            including 을 이용해서 title, content, created_by, created_at, hashtag 검색 가능하게 하기
            id 는 인증기능을 달아서 유저 정보를 알아 올 수 있을때 할 예쩡
            사용법: 'root.필드명'
         */
        bindings.including(root.title, root.content, root.created_at, root.created_by, root.hashtag);

        /* 3. '정확한 검색'만 되었는데 'or 검색' 가능하게 바꾸기 */
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase);
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);// like '%${문자열}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.created_at).first(DateTimeExpression::eq); // 이건 날짜니까 DateTimeExpression 하고 eq 는 equals 의 의미 날짜필드는 정확한 검색만 되도록 설정
        // 근데 이렇게 하면 시분초가 다 0으로 인식됨 이부분 나중에 별도 작업 예정
        bindings.bind(root.created_by).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
    }
    /* ArticleCommentRepository 똑같이 하기*/

}
