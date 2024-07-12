package kr.co.solbipos.pos.confg.verHq.web;

import kr.co.common.data.enums.UseYn;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.pos.confg.vermanage.service.VerManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Class Name : VerHqController.java
* @Description : 기초관리 > 매장관리 > 버전관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2021.04.06  권지현      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 권지현
* @since 2021. 04.06
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/pos/confg/verHq")
public class VerHqController {

    private final VerManageService verManageService;
    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public VerHqController(VerManageService verManageService, SessionService sessionService, CmmEnvUtil cmmEnvUtil, CmmCodeUtil cmmCodeUtil) {
        this.verManageService = verManageService;
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 버전관리 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // [1014 포스프로그램구분] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("posVerEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1014"), "1"));
            System.out.println("posVerEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1014"), "1"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("posVerEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1014"), "1"));
            System.out.println("posVerEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1014"), "1"));
        }

        // 프로그램 상세구분 코드 조회(콤보박스용)
        model.addAttribute("nmcodeCdList", cmmCodeUtil.assmblObj(verManageService.getNmcodeCdList(), "name", "value", UseYn.N));
        model.addAttribute("nmcodeCdListAll", cmmCodeUtil.assmblObj(verManageService.getNmcodeCdList(), "name", "value", UseYn.ALL));

        return "pos/confg/varhq/verHq";
    }

}
