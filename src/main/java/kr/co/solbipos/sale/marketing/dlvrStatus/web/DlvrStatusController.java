package kr.co.solbipos.sale.marketing.dlvrStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.marketing.dlvrStatus.service.DlvrStatusService;
import kr.co.solbipos.sale.marketing.dlvrStatus.service.DlvrStatusVO;
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
 * @Class Name  : DlvrStatusController.java
 * @Description : 미스터피자 > 마케팅조회 > 배달비
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/marketing/dlvrStatus")
public class DlvrStatusController {

    private final SessionService sessionService;
    private final DlvrStatusService dlvrStatusService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DlvrStatusController(SessionService sessionService, DlvrStatusService dlvrStatusService) {
        this.sessionService = sessionService;
        this.dlvrStatusService = dlvrStatusService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dlvrStatus/list.sb", method = RequestMethod.GET)
    public String dlvrStatusView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/marketing/dlvrStatus/dlvrStatus";
    }

    /**
     * 배달비 - 조회
     *
     * @param   dlvrStatusVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 07. 25.
     */
    @RequestMapping(value = "/dlvrStatus/getDlvrStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrStatusList(DlvrStatusVO dlvrStatusVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dlvrStatusService.getDlvrStatusList(dlvrStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dlvrStatusVO);
    }
}
