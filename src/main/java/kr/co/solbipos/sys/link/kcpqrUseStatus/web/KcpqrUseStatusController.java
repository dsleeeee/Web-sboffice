package kr.co.solbipos.sys.link.kcpqrUseStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.link.kcpqrUseStatus.service.KcpqrUseStatusService;
import kr.co.solbipos.sys.link.kcpqrUseStatus.service.KcpqrUseStatusVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name  : KcpqrUseStatusController.java
 * @Description : 시스템관리 > 연동 > KCPQR현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.22  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.22
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/link/kcpqrUseStatus")
public class KcpqrUseStatusController {

    private final SessionService sessionService;
    private final KcpqrUseStatusService kcpqrUseStatusService;

    public KcpqrUseStatusController(SessionService sessionService, KcpqrUseStatusService kcpqrUseStatusService) {
        this.sessionService = sessionService;
        this.kcpqrUseStatusService = kcpqrUseStatusService;
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
        return "sys/link/kcpqrUseStatus/kcpqrUseStatus";
    }

    /**
     * KCPQR현황 - 조회
     * @param   kcpqrUseStatusVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author  김유승
     * @since   2026.01.21
     */
    @RequestMapping(value = "/kcpqrUseStatus/getSearchKcpqrStatus.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchKcpqrStatus(KcpqrUseStatusVO kcpqrUseStatusVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kcpqrUseStatusService.getSearchKcpqrStatus(kcpqrUseStatusVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kcpqrUseStatusVO);
    }
}
