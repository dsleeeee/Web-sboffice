package kr.co.solbipos.sale.status.cupReturnStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.status.cupReturnStatus.service.CupReturnStatusService;
import kr.co.solbipos.sale.status.cupReturnStatus.service.CupReturnStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : CupReturnStatusController.java
 * @Description : 맘스터치 > 매출분석2 > 컵보증금회수현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.17  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.07.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/status/cupReturnStatus")
public class CupReturnStatusController {

    private final SessionService sessionService;
    private final CmmCodeUtil cmmCodeUtil;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;
    private final CupReturnStatusService cupReturnStatusService;

    /**
     * Constructor Injection
     */
    @Autowired
    public CupReturnStatusController(SessionService sessionService, CmmCodeUtil cmmCodeUtil, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil, CupReturnStatusService cupReturnStatusService) {
        this.sessionService = sessionService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.dayProdService = dayProdService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.cupReturnStatusService = cupReturnStatusService;
    }


    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/cupReturnStatus/list.sb", method = RequestMethod.GET)
    public String cupReturnStatusView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/cupReturnStatus/cupReturnStatus";
    }

    /**
     * 컵보증금회수현황 - 조회
     *
     * @param   cupReturnStatusVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 07. 17.
     */
    @RequestMapping(value = "/cupReturnStatus/getCupReturnStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCupReturnStatusList(CupReturnStatusVO cupReturnStatusVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = cupReturnStatusService.getCupReturnStatusList(cupReturnStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, cupReturnStatusVO);
    }
}
