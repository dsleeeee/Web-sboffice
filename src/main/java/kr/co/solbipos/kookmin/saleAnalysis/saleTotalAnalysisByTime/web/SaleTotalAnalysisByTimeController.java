package kr.co.solbipos.kookmin.saleAnalysis.saleTotalAnalysisByTime.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.saleAnalysis.saleTotalAnalysisByTime.service.SaleTotalAnalysisByTimeService;
import kr.co.solbipos.kookmin.saleAnalysis.saleTotalAnalysisByTime.service.SaleTotalAnalysisByTimeVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name  : SaleTotalAnalysisByTimeController.java
 * @Description : 국민대 > 매출분석 > 시간대 매출합계분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.13  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.13
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/kookmin/saleAnalysis/saleTotalAnalysisByTime")
public class SaleTotalAnalysisByTimeController {

    private final SaleTotalAnalysisByTimeService saleTotalAnalysisByTimeService;
    private final SessionService sessionService;

    /**
     * Constructor Injection
     */
    public SaleTotalAnalysisByTimeController(SaleTotalAnalysisByTimeService saleTotalAnalysisByTimeService, SessionService sessionService) {
        this.saleTotalAnalysisByTimeService = saleTotalAnalysisByTimeService;
        this.sessionService = sessionService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/saleTotalAnalysisByTime/view.sb", method = RequestMethod.GET)
    public String saleTotalAnalysisByTime(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/saleAnalysis/saleTotalAnalysisByTime/saleTotalAnalysisByTime";
    }

    /**
     * 시간대 매출합계분석 조회
     *
     * @param   saleTotalAnalysisByTimeVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 13.
     */
    @RequestMapping(value = "/saleTotalAnalysisByTime/getSaleTotalAnalysisByTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleTotalAnalysisByTimeList(SaleTotalAnalysisByTimeVO saleTotalAnalysisByTimeVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleTotalAnalysisByTimeService.getSaleTotalAnalysisByTimeList(saleTotalAnalysisByTimeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleTotalAnalysisByTimeVO);
    }
}
