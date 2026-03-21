package com.kevorino.webboard.service;

import com.kevorino.webboard.dto.PageRequestDTO;
import com.kevorino.webboard.dto.PageResultDTO;
import com.kevorino.webboard.dto.WebboardDTO;
import com.kevorino.webboard.entity.Webboard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WebboardServiceTests {

    @Autowired
    private WebboardService service;

    @Test
    public void testRegister() {
        WebboardDTO webboardDTO = WebboardDTO.builder()
                .title("Test Title")
                .content("Test Content")
                .writer("user00")
                .build();

        System.out.println(service.register(webboardDTO));
    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(5).build();

        PageResultDTO<WebboardDTO, Webboard> resultDTO = service.getList(pageRequestDTO);

        System.out.println("prev: " + resultDTO.isPrev());
        System.out.println("next: " + resultDTO.isNext());
        System.out.println("total: " + resultDTO.getTotalPage());

        System.out.println("-------------------------------");
        for (WebboardDTO webboardDTO : resultDTO.getDtoList()) {
            System.out.println(webboardDTO);
        }

        System.out.println("=================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));

    }

    @Test
    public void testSearch() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(5)
                .type("tc")
                .keyword("20")
                .build();

        PageResultDTO<WebboardDTO, Webboard> resultDTO = service.getList(pageRequestDTO);

        System.out.println("prev: " + resultDTO.isPrev());
        System.out.println("next: " + resultDTO.isNext());
        System.out.println("total: " + resultDTO.getTotalPage());

        System.out.println("-------------------------------");
        for (WebboardDTO webboardDTO : resultDTO.getDtoList()) {
            System.out.println(webboardDTO);
        }

        System.out.println("=================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));

    }



}
