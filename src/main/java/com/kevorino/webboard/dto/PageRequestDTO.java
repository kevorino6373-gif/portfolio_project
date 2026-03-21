package com.kevorino.webboard.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page; //페이지 번호
    private int size; //페이지당 데이터 개수

    private String type; // 검색 조건
    private String keyword; // 검색 키워드

    public PageRequestDTO() {
        this.page = 1; //페이지 번호
        this.size = 10; // 페이지 안에 표시할 글의 개 수
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
        //-1을 하는 이유는 페이지 번호는 1부터 시작하지만 Pageable은 0부터 시작하기 때문
    }

}
