package com.kevorino.webboard.repository;

import com.kevorino.webboard.entity.QWebboard;
import com.kevorino.webboard.entity.Webboard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class WebboardRepositoryTests {

    @Autowired
    private WebboardRepository webboardRepository;

    @Test
    // 더미데이터 10개 삽입
    public void insertDummies() {

        IntStream.rangeClosed(1,1).forEach(i -> {

           Webboard webboard = Webboard.builder()
                   .title("Title..." + i)
                   .content("Content..." + i)
                   .writer("user" + (i % 10))
                   .build();
           System.out.println(webboardRepository.save(webboard));

        });

    }

    @Test
    public void updateTest() {

        Optional<Webboard> result = webboardRepository.findById(20L); //존재하는 번호로 테스트

        if(result.isPresent()) {
            Webboard webboard = result.get();
            webboard.changeTitle("Changed Title...");
            webboard.changeContent("Changed Content...");

            webboardRepository.save(webboard);
        }

    }

    //단일항목 검색
    @Test
    public void tesetQuery1() {

       Pageable pageable = PageRequest.of(0, 10, Sort.by("wno").descending());

        QWebboard qWebboard = QWebboard.webboard; //1

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder(); //2

        BooleanExpression expression = qWebboard.title.contains(keyword); //3

        builder.and(expression); //4

        Page<Webboard> result = webboardRepository.findAll(builder, pageable); //5

        result.stream().forEach(webboard -> {
            System.out.println(webboard);
        });

    }

    @Test
    //다중항목 검색
    public void tesetQuery2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("wno").descending());

        QWebboard qWebboard = QWebboard.webboard; //1

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qWebboard.title.contains(keyword);
        BooleanExpression exContent = qWebboard.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent); // 1---------------

        builder.and(exAll); //2----------------

        builder.and(qWebboard.wno.gt(0L)); //3----------------

        Page<Webboard> result = webboardRepository.findAll(builder, pageable);

        result.stream().forEach(webboard -> {
            System.out.println(webboard);
        });



    }



}
