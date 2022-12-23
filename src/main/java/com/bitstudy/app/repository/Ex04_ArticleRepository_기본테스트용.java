package com.bitstudy.app.repository;

import com.bitstudy.app.domian.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/*  TDD 를 위해 임시로 만들어 놓은 저장소이다.(이것으로 DB에 접근 할 예정)


*/
/*  TDD 를 위해 임시로 만들어 놓은 저장소이다.(이것으로 DB에 접근 할 예정)

    TDD 만드는방법
    1. 클래스명 위에 우클릭 > Go to > Test (ctrl + shift + T)
    2. JUnit5 버전인지 확인
    3. 이름 ArticleRepositoryTest 를 JpaRepositi 로 변경하기
*/

public interface Ex04_ArticleRepository_기본테스트용 extends JpaRepository<Article, Long> {
}
