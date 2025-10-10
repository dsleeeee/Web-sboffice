package kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByTime.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByTime.service.SaleAnalysisByTimeService;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByTime.service.SaleAnalysisByTimeVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * @Class Name  : SaleAnalysisByTimeController.java
 * @Description : 국민대 > 매출분석 > 시간대별 매출분석(요일별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.01  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.01
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/kookmin/saleAnalysis/saleAnalysisByTime")
public class SaleAnalysisByTimeController {

    private final SessionService sessionService;
    private final SaleAnalysisByTimeService saleAnalysisByTimeService;

    /**
     * Constructor Injection
     */
    public SaleAnalysisByTimeController(SessionService sessionService, SaleAnalysisByTimeService saleAnalysisByTimeService) {
        this.sessionService = sessionService;
        this.saleAnalysisByTimeService = saleAnalysisByTimeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/saleAnalysisByTime/view.sb", method = RequestMethod.GET)
    public String saleAnalysisByTime(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/saleAnalysis/saleAnalysisByTime/saleAnalysisByTime";
    }

    /**
     * 시간대별 매출분석 조회
     *
     * @param   saleAnalysisByTimeVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 10. 01.
     */
    @RequestMapping(value = "/saleAnalysisByTime/getSaleAnalysisByTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleAnalysisByTimeList(SaleAnalysisByTimeVO saleAnalysisByTimeVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleAnalysisByTimeService.getSaleAnalysisByTimeList(saleAnalysisByTimeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleAnalysisByTimeVO);
    }
}
