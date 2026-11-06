package kr.co.solbipos.sys.admin2.saleBillDataChk.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin2.saleBillDataChk.service.SaleBillDataChkService;
import kr.co.solbipos.sys.admin2.saleBillDataChk.service.SaleBillDataChkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : SaleBillDataChkController.java
 * @Description : 시스템관리 > 관리자기능2 > 매출자료검증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.11.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.11.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/admin2/saleBillDataChk")
public class SaleBillDataChkController {

    private final SessionService sessionService;
    private final SaleBillDataChkService saleBillDataChkService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleBillDataChkController(SessionService sessionService, SaleBillDataChkService saleBillDataChkService) {
        this.sessionService = sessionService;
        this.saleBillDataChkService = saleBillDataChkService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/saleBillDataChk/view.sb", method = RequestMethod.GET)
    public String saleBillDataChkView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/admin2/saleBillDataChk/saleBillDataChk";
    }

    /**
     * 매출자료검증 - 영수증 1건 정합성 체크
     *
     * @param saleBillDataChkVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 11. 03.
     */
    @RequestMapping(value = "/saleBillDataChk/getSaleBillDataChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleBillDataChk(SaleBillDataChkVO saleBillDataChkVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결과를 Map 으로 주면 ResultHandlerAspect 가 "조회 데이터 없음" 메시지를 붙이므로 VO 로 반환
        SaleBillDataChkVO result = saleBillDataChkService.getSaleBillDataChk(saleBillDataChkVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
