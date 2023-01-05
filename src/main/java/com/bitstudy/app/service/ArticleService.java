package com.bitstudy.app.service;


import com.bitstudy.app.domian.type.SearchType;
import com.bitstudy.app.dto.ArticleDto;
import com.bitstudy.app.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 기능 관련된 로직을 다 여기다 짤 예정이다.
 * */
@Service
@RequiredArgsConstructor // 필수 필드에 생성자를 자동으로 만들어주는 롬복 애너테이션
@Transactional // 이 클래스 동작 하다가 하나라도 잘못되면 다시 롤백 시켜라는 뜻
public class ArticleService {

    private final ArticleRepository articleRepository; // 아티클 관련 서비스 이기 때문에 ArticleRepository 필요

    // 검색용
    @Transactional(readOnly = true) // 트랜잭션을 읽기전용 모드로 설정 실수로 커밋해도 flush 가 되지않아 엔티티가 등록/수정/삭제가 되지 않는다.
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable){

        return Page.empty();
    }


}
