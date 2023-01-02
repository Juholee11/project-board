package com.bitstudy.app.repository;

import com.bitstudy.app.domian.ArticleComment;
import com.bitstudy.app.domian.QArticleComment;
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
public interface Ex07_5_ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>,
        QuerydslBinderCustomizer<QArticleComment> {
@Override
default void customize(QuerydslBindings bindings, QArticleComment root){
        bindings.excludeUnlistedProperties(true);
        bindings.including( root.content, root.created_at, root.created_by);

        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.created_at).first(DateTimeExpression::eq);
        bindings.bind(root.created_by).first(StringExpression::containsIgnoreCase);

        }
        /*빌드 컨트롤 f9 하고 Hal 가서 체크하기
        * htt[://localhost:8080/api/articleComments?created_by=*/
}
