package com.bitstudy.app.repository;


import com.bitstudy.app.config.JpaConfig;
import com.bitstudy.app.domian.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest // 슬라이드 테스트
/**  슬라이드 테스트란? /
     지난번 TDD 때 각 메서드들을 다 남남으로 서로 알아보지 못하게만들엇엇다 이와같이 각각 메서드가 테스트한 결과를 못보게 짤라서 만드는것이다.
*/
@Import(JpaConfig.class)
/* 원래대로라면 JPA 에서 모든 정보를 컨트롤 해야하지만 JpaConfig 의 경우는 읽어오질 못한다 왜냐하면 우리가 별도로 만든 파일이기 때문에 별도로 지정을 해야한다(Import)
   안하게 되면 config 안에 명시한 JpaAuditing 기능이 동작하지 않는다.
   */
class Ex04_JpaRepositoryTest {
    private final Ex04_ArticleRepository_기본테스트용 articleRepository;
    private final Ex05_ArticleCommentRepository_기본테스트용 articleCommentRepository;
    /*원래는 둘 다 @Autowired 가 있어야 하지만 JUnit5 버전과 최신 버전의 스프링 부트를 이용하면 Test 에서 생성자 주입패턴 사용가능*/

    /* 생성자 만들기 - 여기서는 다른 파일에서 매게변수로 보내주는걸 받는것이라서 위랑 상관없이 @Autowired 를 붙여야 한다*/
    public Ex04_JpaRepositoryTest(@Autowired Ex04_ArticleRepository_기본테스트용 articleRepository,
    @Autowired Ex05_ArticleCommentRepository_기본테스트용 articleCommentRepository){
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    /* 트랜젝션 시 사용하는 메서드
       사용법 : repository 명.메소드()
       메소드 종류
       1. findAll() : 모든 컬럼을 조회 할 때 사용(페이징 가능 / pageable)
                      당연히 select 작업을 하지만 잠깐 사이에 해당 테이블에 어떤 변화가 있었는지 알 수 없기 떄문에 select 전
                      먼저 최신 데이터를 잡기 위해 update 를 한다.
                      ** 동작 순서 : update > select
       2. findById() : 한 건에 대한 데이터 조회 시 사용
                       PK 기준으로 레코드 한 건 조회 한다.
                       ()안에 글번호를 넣어줘야한다
       3. save() : 레코드 저장 시 사용(insert , update 관련 시 사용)
       4. count() : 레코드 개수 뽑을 때 사용
       5. delete() : 레코드 삭제
       -------------------------------------------------------------------------------------------------------------

       테스트용 데이터 가져오기(가짜데이터)
       https://www.mockaroo.com/ 접속
       Field Name : 컬럼명
       Type : 데이터 타입
       Format: SQL
       Table Name : 테이블 명
       #Rows : 몇개 만들건지
       blank : null 값 얼마나 줄지


    */
    /** 1. Select 테스트 */
    @DisplayName("셀렉트 테스트")
    @Test
    void selectTest(){
        /** 셀렉팅을 할 예정으로 articleRepository 를 기준으로 테스트 예정 */
        /** maven 방식 : dao > mapper 로 정보 보내고 DB 갔다 와서 Controller 까지 보내는데 dao 에서 dto 를 List 에 담아서 return
         *
         *  */
        List<Article> articles= articleRepository.findAll();
        /** assertJ 를 이용해서 테스트 할 예정
            List 에 담겨있는 articles 가 NotNull 이고 사이즈가 ??개면 통과

         */
        assertThat(articles).isNotNull().hasSize(100);
    }
    /** 2. Insert 테스트*/
    @Test
    void insertTest(){
        /** 기존의 article 개수를 센 다음에 insert 하고 기존꺼 보다 현재꺼가 1차이가 나면 insert 제대로 됐다는 뜻 */

        // 기존 카운트 구하기
        long prevCount = articleRepository.count();
        
        // insert 하기

        // 기존꺼랑 현재꺼랑 개수 차이 구하기
        articleRepository.count();
    }
}