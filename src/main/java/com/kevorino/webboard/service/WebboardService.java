package com.kevorino.webboard.service;

import com.kevorino.webboard.dto.PageRequestDTO;
import com.kevorino.webboard.dto.PageResultDTO;
import com.kevorino.webboard.dto.WebboardDTO;
import com.kevorino.webboard.entity.Webboard;

public interface WebboardService {

    Long  register(WebboardDTO webboardDTO);

    PageResultDTO<WebboardDTO, Webboard> getList(PageRequestDTO requestDTO);

    WebboardDTO read(Long wno);

    //게시글 삭제
    void remove(Long wno);

    void modify(WebboardDTO dto);

    default Webboard dtoToEntity(WebboardDTO dto) {
        Webboard entity = Webboard.builder()
                .wno(dto.getWno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default WebboardDTO entityTODto(Webboard entity) {

        WebboardDTO dto = WebboardDTO.builder()
                .wno(entity.getWno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;

    }


}
