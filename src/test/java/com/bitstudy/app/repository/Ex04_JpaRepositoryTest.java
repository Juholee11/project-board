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
//    Autowired
//    private ArticleRepository articleRepository;
//    Autowired
//    private ArticleCommentRepository articleCommentRepository;
//    @Autowired
//    private ArticleRepository articleRepository;
//    @Autowired
//    private ArticleRepository articleRepository;
//    @Autowired
//    private ArticleRepository articleRepository;

//    @Autowired
//    private ArticleRepository articleRepository;
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
       6.saveAndFlush() :
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
    @DisplayName("select 테스트")
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
    @DisplayName("insert 테스트")
    @Test
    void insertTest(){
        /** 기존의 article 개수를 센 다음에 insert 하고 기존꺼 보다 현재꺼가 1차이가 나면 insert 제대로 됐다는 뜻 */

        // 기존 카운트 구하기
        long prevCount = articleRepository.count();

        // insert 하기
        Article article = Article.of("제목","내용","#해시태그");
        articleRepository.save(article);

        // 기존꺼랑 현재꺼랑 개수 차이 구하기
        assertThat(articleRepository.count()).isEqualTo(prevCount + 1) ;
        /** 주의 !!!!! : 이상태로 테스트를 돌리면 created_at 못찾는다고 에러난다
         *              이유는 jpaConfig 파일에 auditing 을 사용하겠다 세팅했는데
         *              해당 엔티티(Article.java)에서 auditing 을 쓴다고 명시 안한 상태이기 때문이다.
         *              엔티티(Article.java) 가서 클래스 레벨로 @EntityListeners(AuditingEntityListener.class) 적기*/
    }
    /** 3. Update 테스트*/
    @DisplayName("update 테스트")
    @Test
    void updateTest(){
        /** 기존의 데이터 하나있어야 하고 그것을 수정했을때 관찰예정
         * 1. 기존의 컨텍스트로부터 하나 엔티티를 객체를 가져온다. (DB에서 한줄 뽑아온다.)
         *  */

        /** 순서 - 1. 기존의 영속성 컨텍스트로 부터 하나 엔티티를 객체를 가져온다 (DB에서 한줄 뽑아오기)
                    articleRepository > 기존의 영속성 컨텍스트로부터
                    findVyId(1L) > 하나의 엔티티 객체를 가져온다(번호:1)
                    .orElseThrow() > 없으면 throw 시켜서 일단 테스트가 끝나도록*/
        Article article = articleRepository.findById(1L).orElseThrow();
        /** 순서 - 2. 업데이트로 해시태그를 바꾸기
                    엔티티에 있는 setter 를 이용해서 updateHashtag 에있는 문자열로 업데이트하기
                  (1) 변수 updateHasgtag 에 바꿀 문자열 저장
                  (2) 엔티티(article)에 있는 setter 를 이용해서 변수 updateHashtag 에 있는 문자열을 넣고
                    (해시태그 바꿀꺼니 setHashtag. 이름 어찌할지 모르면 실제 엔티티 파일가서 setterr 만들어 보기 , 이름 그대로 사용 하면 됨 )*/
        String updateHashtag = "#abcd";
        article.setHashtag(updateHashtag);
        /** 순서 - 3. 데이터 베이스에 업데이트 하기 */
//        articleRepository.save(article);
        Article savedArticle = articleRepository.saveAndFlush(article);
        /** save 로 놓고 테스트를 돌리면 콘솔(run /service )탭에 update 구문이 나오지않고 select 구문만 나온다.
         *  이유 : 영속성 컨텍스트로부터 가져온 데이터를 그냥 save만 하고 아무것도 하지않고 끝내면 롤백 되니까 스프링부트는 원래의 값으로 돌아갈것이다.
         *        그래서 했다 치고 update 를 하지 않는다 대신 코드의 유효성 확인 따라서 save를 하고 flush 를 해야 한다.
         *
         *        flush 란?
         *        push 와 비슷한 기능이다.
         *        역할
         *        1. 변경점 감지
         *        2. 수정된 Entity (현 Article) 저장소 등록
         *        3. sql 저장소에 있는 쿼리를 DB에 전송 */
        /** 순서 - 4. 위에서 바꾼 savedArticle 에 업데이트된 hashtag 필드에 updateHashtag 에 저장된 값 (#abcd) 있는지 확인 */
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag",updateHashtag);
    }
    /** 4. Delete 테스트*/
    @DisplayName("delete 테스트")
    @Test
    void deleteTest(){
        /** 기존 데이터가 있다 가정하고 그 중에 값을 하나 꺼내고 지워야 한다.
         * 1. 기존의 영속성 컨텍스트로부터 하나 엔디디 객체를 가져온다 > findById
         * 2. 지우면 DB에서 하나 사라지기 때문에 count()를 구해놓고 > 레포지터리.count()
         * 3. delete 하고 ( -1) > .delete();
         * 4. 2번에서 구한 count 화 지금 순간의 개수 비교해서1차이나면 테스트 통과 > .isEqualTo()*/

        /** 순서
         * articleRepository > 기존의 영속성 컨텍스트로부터
         * findVyId(1L) > 하나의 엔티티 객체를 가져온다(번호:1)
         * .orElseThrow() > 없으면 throw 시켜서 일단 테스트가 끝나도록
         */
//        1. 기존의 영속성 컨텍스트로부터 하나 엔디디 객체를 가져온다 > findById
        Article article = articleRepository.findById(1L).orElseThrow();

//        2. 지우면 DB에서 하나 사라지기 때문에 count()를 구해놓고 > 레포지터리.count()
//        게시글(articleRepository) 뿐 아니라 연관 댓글 (articleCommentRepository)까지 삭제 할거라 두개의 개수를 알아내자
        long prevArticleCount = articleRepository.count();
        long prevArticleCommentCount = articleCommentRepository.count();// 데이터베이스에 있는 모든 댓글의 수
        int deletedCommentSize = article.getArticleComments().size(); // 해당 게시글에 딸려있는 댓글의 수
        // 나중에 모든 댓글의 수 - 게시글에 딸려있는 댓글의 수 = 몇개가 지워졌는지 알 수 있다.

//        3. delete 하고 ( -1) > .delete();
        articleRepository.delete(article);

//        4. 2번에서 구한 count 화 지금 순간의 개수 비교해서1차이나면 테스트 통과 > .isEqualTo()*/
        assertThat(articleRepository.count()).isEqualTo(prevArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(prevArticleCommentCount - deletedCommentSize);
    }
}