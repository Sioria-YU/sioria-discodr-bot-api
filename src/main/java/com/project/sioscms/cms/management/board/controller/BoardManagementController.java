package com.project.sioscms.cms.management.board.controller;

import com.project.sioscms.apps.attach.domain.dto.AttachFileGroupDto;
import com.project.sioscms.apps.attach.domain.entity.AttachFileGroup;
import com.project.sioscms.apps.attach.service.AttachFileService;
import com.project.sioscms.apps.board.domain.dto.BoardDto;
import com.project.sioscms.apps.board.domain.dto.BoardMasterDto;
import com.project.sioscms.apps.board.service.BoardMasterService;
import com.project.sioscms.apps.board.service.BoardService;
import com.project.sioscms.cms.management.board.service.BoardManagementService;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/cms/board")
@RequiredArgsConstructor
public class BoardManagementController {

    private final BoardMasterService boardMasterService;
    private final BoardManagementService boardManagementService;
    private final BoardService boardService;
    private final AttachFileService attachFileService;

    @RequestMapping("/master-list")
    public ModelAndView boardMasterList(BoardMasterDto.Request requestDto){
        ModelAndView mav = new ModelAndView("cms/board/boardMasterList");
        SiosPage<BoardMasterDto.Response> siosPage = boardMasterService.getBoardMasterList(requestDto);

        if(siosPage != null && !siosPage.isEmpty()){
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }

        //게시판 유형
        mav.addObject("boardTypeCodeList", boardManagementService.getBoardTypeCodeList());
        return mav;
    }

    @RequestMapping("/list")
    public ModelAndView boardList(BoardDto.Request requestDto){
        ModelAndView mav = new ModelAndView("cms/board/boardList");
        SiosPage<BoardDto.Response> siosPage = boardService.getBoardList(requestDto);

        if(siosPage != null && !siosPage.isEmpty()){
            mav.addObject("resultList", siosPage.getContents());
            mav.addObject("pageInfo", siosPage.getPageInfo());
        }

        return mav;
    }

    @RequestMapping("/view/{boardId}")
    public ModelAndView boardView(@PathVariable Long boardId){
        ModelAndView mav = new ModelAndView("cms/board/boardView");
        mav.addObject("result", boardService.getBoard(boardId));
        return mav;
    }

    @RequestMapping("/regist")
    public ModelAndView boardRegist(BoardDto.Request requestDto){
        ModelAndView mav = new ModelAndView("cms/board/boardRegist");
        mav.addObject("boardMasterId", requestDto.getBoardMasterId());
        if(requestDto.getId() != null){
            mav.addObject("result", boardService.getBoard(requestDto.getId()));
        }
        return mav;
    }

    @RequestMapping("/save")
    public ModelAndView boardSave(BoardDto.Request requsetDto, @RequestPart List<MultipartFile> files){
        //첨부파일을 등록하여 attachFileGroupId를 requestDto에 set하여 게시판 저장으로 넘긴다.
        //최초 저장이기 때문에 attachFileGroup = null
        AttachFileGroupDto.Response attachFileGroupDto = attachFileService.multiUpload(files, null);

        if(attachFileGroupDto != null){
            requsetDto.setAttachFileGroupId(attachFileGroupDto.getId());
        }

        BoardDto.Response dto = boardService.saveBoard(requsetDto);

        RedirectView rv = new RedirectView("/cms/board/list?boardMasterId=" + requsetDto.getBoardMasterId());
        if (dto != null) {
            rv.addStaticAttribute("msg", "정상 처리되었습니다.");
        } else {
            rv.addStaticAttribute("msg", "처리 실패하였습니다.");
        }

        return new ModelAndView(rv);
    }

    @RequestMapping("/update")
    public ModelAndView boardUpdate(BoardDto.Request requsetDto, @RequestPart List<MultipartFile> files){
        //첨부파일을 등록하여 attachFileGroupId를 requestDto에 set하여 게시판 저장으로 넘긴다.
        AttachFileGroupDto.Response attachFileGroupDto = attachFileService.multiUpload(files, requsetDto.getAttachFileGroupId());

        //기존 첨부파일이 없었다면 등록, 있다면 파일 구성만 업데이트
        if(attachFileGroupDto != null && requsetDto.getAttachFileGroupId() == null){
            requsetDto.setAttachFileGroupId(attachFileGroupDto.getId());
        }

        BoardDto.Response dto = boardService.updateBoard(requsetDto);

        RedirectView rv = new RedirectView("/cms/board/list?boardMasterId=" + requsetDto.getBoardMasterId());
        if (dto != null) {
            rv.addStaticAttribute("msg", "정상 처리되었습니다.");
        } else {
            rv.addStaticAttribute("msg", "처리 실패하였습니다.");
        }

        return new ModelAndView(rv);
    }

}
