package com.kevorino.webboard.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data

public class PageResultDTO<DTO, EN> {

    //DTO리스트
    private List<DTO> dtoList;

    //총페이지번호
    private int totalPage;

    //현재페이지번호
    private int page;
    //목록사이즈
    private int size;

    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    //이전, 다음
    private boolean prev, next;

    //페이지 번호목록
    private List<Integer> pageList;
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber()+ 1; //총페이지번호
        this.size = pageable.getPageSize(); //목록사이즈

        int tempEnd = (int)(Math.ceil(page / 5.0)) * 5; //끝 페이지 번호

        start = tempEnd - 4; //시작 페이지 번호
        end = totalPage > tempEnd ? tempEnd : totalPage; //끝 페이지 번호

        prev = start > 1; //이전
        next = totalPage > tempEnd; //다음

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList()); //페이지 번호목록
    }


}
