package com.bitstudy.app.domian;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/** 할일 : Article.java / ArticleComment.java 에 있는 중복되는 필드 취합하기
 *  1. Article 에 있는 메타 데이터들 가져오기(auditing 에 관련된 필드들)
 *  2. 클래스 레벨에 @MappedSuperClass 달아주기
 *  3. auditing 과 관련된 어노테이션 다 가져오기
 *      ex:Article 에서는 @EntityListeners(AuditingEntityListener.class)
 *  파싱 : 일정한 문법을 토대로 나열된 data 를 그 문법에 맞춰서 분석한 후 새로 구성하는 것
 *  4.
 */
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class Ex06_3_AuditingFields_공통필드_분리하기 {
//Article 중복 필드
@CreatedDate
@Column(nullable = false)
private LocalDateTime created_at;

@CreatedBy
@Column(nullable = false, length = 100)
private String created_by;

@LastModifiedDate
@Column(nullable = false) private LocalDateTime modified_at;

@LastModifiedBy
@Column(nullable = false, length = 100) private String modified_by;
///////////////////
}
