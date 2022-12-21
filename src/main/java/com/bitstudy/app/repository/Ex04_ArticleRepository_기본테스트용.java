package com.bitstudy.app.repository;

import com.bitstudy.app.domian.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/*  TDD 를 위해 임시로 만들어 놓은 저장소이다.(이것으로 DB에 접근 할 예정)


*/

public interface Ex04_ArticleRepository_기본테스트용 extends JpaRepository<Article, Long> {
}
