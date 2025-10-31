package kr.co.solbipos.kookmin.generalInfo.dailySaleReport.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.generalInfo.dailySaleReport.service.DailySaleReportService;
import kr.co.solbipos.kookmin.generalInfo.dailySaleReport.service.DailySaleReportVO;
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
 * @Class Name  : DailySaleReportController.java
 * @Description : 국민대 > 총괄정보 > 일일매출보고서
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.23  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.10.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/kookmin/generalInfo/dailySaleReport")
public class DailySaleReportController {

    private final SessionService sessionService;
    private final DailySaleReportService dailySaleReportService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DailySaleReportController(SessionService sessionService, DailySaleReportService dailySaleReportService) {
        this.sessionService = sessionService;
        this.dailySaleReportService = dailySaleReportService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dailySaleReport/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/generalInfo/dailySaleReport/dailySaleReport";
    }

    /**
     * 일일매출보고서 리스트 조회
     * @param dailySaleReportVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2025. 10. 23.
     */
    @RequestMapping(value = "/dailySaleReport/getDailySaleReportList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDailySaleReportList(DailySaleReportVO dailySaleReportVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dailySaleReportService.getDailySaleReportList(dailySaleReportVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dailySaleReportVO);
    }
}
