package com.kevorino.webboard.controller;

import com.kevorino.webboard.dto.PageRequestDTO;
import com.kevorino.webboard.dto.PageResultDTO;
import com.kevorino.webboard.dto.WebboardDTO;
import com.kevorino.webboard.entity.Webboard;
import com.kevorino.webboard.service.WebboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/webboard/*")
@Log4j2
@RequiredArgsConstructor
public class WebboardController {
    
    private final WebboardService service; //final로 선언

    @GetMapping("/")
    public String index() {
        return "redirect:/webboard/list";
    }
    
    
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list............." + pageRequestDTO);
        PageResultDTO<WebboardDTO, Webboard> list = service.getList(pageRequestDTO);
        model.addAttribute("result", list);
        model.addAttribute("pageRequestDTO", pageRequestDTO);

    }

    @GetMapping("/main")
    public void main(PageRequestDTO pageRequestDTO, Model model) {

        log.info("main............." + pageRequestDTO);

        PageResultDTO<WebboardDTO, Webboard> list = service.getList(pageRequestDTO);

        model.addAttribute("result", list);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(WebboardDTO dto, RedirectAttributes redirectAttributes){

        log.info("dto..." + dto);

        //새로 추가된 엔티티 번호
        Long wno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", wno);

        return "redirect:/webboard/list";
    }

    @GetMapping("/read")
    public void read(@ModelAttribute("pageRequestDTO") PageRequestDTO requestDTO,
                     @RequestParam Long wno,Model model) {

        WebboardDTO dto = service.read(wno);

        model.addAttribute("dto", dto);

    }

    @GetMapping("/modify")
    public void modify(long wno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("wno: " + wno);

        WebboardDTO dto = service.read(wno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(WebboardDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {

        log.info("modify dto: " + dto);

        service.modify(dto);

        Long wno = dto.getWno();


        redirectAttributes.addAttribute("wno", dto.getWno());
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());


        return "redirect:/webboard/read?wno=" + wno;
    }

    /*삭제*/
    @PostMapping("/remove")
    public String remove(Long wno, RedirectAttributes redirectAttributes) {

        log.info("remove......." + wno);

        service.remove(wno);

        redirectAttributes.addFlashAttribute("msg", wno);

        return "redirect:/webboard/list";
    }
    
    

}
