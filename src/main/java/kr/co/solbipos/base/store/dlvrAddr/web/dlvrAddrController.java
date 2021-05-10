package kr.co.solbipos.base.store.dlvrAddr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.dlvrAddr.service.DlvrAddrService;
import kr.co.solbipos.base.store.dlvrAddr.service.DlvrAddrVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : dlvrAddrController.java
 * @Description : 기초관리 > 매장관리 > 배달권역관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.06  권지현      최초생성
 *
 *
 * @author 솔비포스 web개발팀 권지현
 * @since 2021.05.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/base/store/dlvrAddr")
public class dlvrAddrController {

    private final DlvrAddrService dlvrAddrService;
    private final SessionService sessionService;

    public dlvrAddrController(DlvrAddrService dlvrAddrService, SessionService sessionService) {
        this.dlvrAddrService = dlvrAddrService;
        this.sessionService = sessionService;
    }


    /**
     * 배달권역관리 조회 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        return "base/store/dlvrAddr/dlvrAddr";
    }

    /**
     *  등록 배달권역관리 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(DlvrAddrVO dlvrAddrVO, HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result =  dlvrAddrService.dlvrAddrList(dlvrAddrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dlvrAddrVO);
    }

    /**
     * 미등록 배달권역관리 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/codeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result codeList(DlvrAddrVO dlvrAddrVO, HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result =  dlvrAddrService.dlvrAddrCodeList(dlvrAddrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dlvrAddrVO);
    }

    /**
     * 배달권역 등록
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/addDlvrAddr.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result addDlvrAddr(@RequestBody DlvrAddrVO[] dlvrAddrVOs, HttpServletRequest request, HttpServletResponse response,
                              Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dlvrAddrService.addDlvrAddr(dlvrAddrVOs, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 배달권역 등록 삭제
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/delDlvrAddr.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result delDlvrAddr(@RequestBody DlvrAddrVO[] dlvrAddrVOs, HttpServletRequest request, HttpServletResponse response,
                              Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dlvrAddrService.delDlvrAddr(dlvrAddrVOs, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

}
