package com.bitstudy.app.repository;

import com.bitstudy.app.domian.Article;
import com.bitstudy.app.domian.QArticle;
import com.bitstudy.app.domian.UserAccount;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{



}
