package com.kevorino.webboard.service;

import com.kevorino.webboard.dto.PageRequestDTO;
import com.kevorino.webboard.dto.PageResultDTO;
import com.kevorino.webboard.dto.WebboardDTO;
import com.kevorino.webboard.entity.QWebboard;
import com.kevorino.webboard.entity.Webboard;
import com.kevorino.webboard.repository.WebboardRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor

@Log4j2
public class WebboardServiceImpl implements WebboardService {

    private final WebboardRepository repository; //반드시 final로 선언

    @Override
    public Long register(WebboardDTO dto) {

        log.info("DTO------------------");
        log.info(dto);

        Webboard entity = dtoToEntity(dto);

        log.info(entity);


        repository.save(entity);

        return entity.getWno(); //wno값 반환


    }

    @Override
    public WebboardDTO read(Long wno) {

        Optional<Webboard> result = repository.findById(wno);

        if(result.isPresent()){
            return entityTODto(result.get());
        }
        return null;
        //결과가 존재하면 DTO로 변환하여 반환, 그렇지 않으면 null 반환
    }


    //게시글 삭제
    @Override
    public void remove(Long wno) {repository.deleteById(wno);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){//Querydsl1 처리
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QWebboard qWebboard = QWebboard.webboard;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qWebboard.wno.gt(0L); //wno > 0 조건만 생성
        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) { //검색조건이 없는 경우
            return booleanBuilder;
        }

        //검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qWebboard.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qWebboard.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qWebboard.writer.contains(keyword));
        }

        //모든조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;

    }


    @Override
    public void modify(WebboardDTO dto) {

        //업데이트 하는 항목 '제목','내용'
        Optional<Webboard> result = repository.findById(dto.getWno());

        if(result.isPresent()) {
            Webboard entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }

    }

    @Override
    public PageResultDTO<WebboardDTO, Webboard> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("wno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO);




        Page<Webboard> result = repository.findAll(booleanBuilder, pageable);

        Function<Webboard, WebboardDTO> fn = (entity -> entityTODto(entity));

        return new PageResultDTO<>(result, fn);

    }



}
