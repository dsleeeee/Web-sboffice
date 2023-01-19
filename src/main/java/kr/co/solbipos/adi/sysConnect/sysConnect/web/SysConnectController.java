package kr.co.solbipos.adi.sysConnect.sysConnect.web;

import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Class Name : SysConnectController.java
 * @Description : 부가서비스 > 매입정산 > 매입정산시스템접속
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.21  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sysConnect/sysConnect")
public class SysConnectController {

    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;

    public SysConnectController(SessionService sessionService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("envst1260", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1260"), ""));
        } else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            model.addAttribute("envst1260", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1260"), ""));
        }

        return "adi/sysConnect/sysConnect/sysConnect";
    }


}