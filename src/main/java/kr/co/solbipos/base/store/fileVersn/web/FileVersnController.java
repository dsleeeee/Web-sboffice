package kr.co.solbipos.base.store.fileVersn.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.fileVersn.service.FileVersnService;
import kr.co.solbipos.base.store.fileVersn.service.FileVersnVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @Class Name : FileVersnController.java
 * @Description : 맘스터치 > 기타관리 > 점포별 파일 버전현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.17  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/store/fileVersn")
public class FileVersnController {

    private final SessionService sessionService;
    private final FileVersnService fileVersnService;

    /**
     * Constructor Injection
     */
    @Autowired
    public FileVersnController(SessionService sessionService, FileVersnService fileVersnService) {
        this.sessionService = sessionService;
        this.fileVersnService = fileVersnService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/fileVersn/list.sb", method = RequestMethod.GET)
    public String fileVersnView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/store/fileVersn/fileVersn";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   fileVersnVO
     * @return  String
     * @author  권지현
     * @since   2022.10.17
     */
    @RequestMapping(value = "/fileVersn/getFileVersnList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFileVersnList(HttpServletRequest request, HttpServletResponse response, Model model, FileVersnVO fileVersnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = fileVersnService.getFileVersnList(fileVersnVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, fileVersnVO);
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   fileVersnVO
     * @return  String
     * @author  권지현
     * @since   2022.10.17
     */
    @RequestMapping(value = "/fileVersn/getFileVersnExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getFileVersnExcelList(HttpServletRequest request, HttpServletResponse response, Model model, FileVersnVO fileVersnVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = fileVersnService.getFileVersnExcelList(fileVersnVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, fileVersnVO);
    }

}