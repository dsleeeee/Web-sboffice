package kr.co.solbipos.sale.status.corner.dayPeriod.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.corner.dayPeriod.service.CornerDayPeriodService;
import kr.co.solbipos.sale.status.corner.dayPeriod.service.CornerDayPeriodVO;

/**
 * @Class Name : TodayBillSaleDtlController.java
 * @Description : 매출관리 > 매출현황 > 영수증별매출상세현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.02.15  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2019.02.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/corner")
public class CornerDayPeriodController {
    private final SessionService sessionService;
    private final CornerDayPeriodService cornerDayPeriodService;

    @Autowired
    public CornerDayPeriodController(SessionService sessionService, CornerDayPeriodService cornerDayPeriodService) {
        this.sessionService = sessionService;
        this.cornerDayPeriodService = cornerDayPeriodService;
    }


    /**
     * 코너별매출 설정기간별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDayPeriodVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/corner/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCornerDayPeriodList(HttpServletRequest request, HttpServletResponse response,
        Model model, CornerDayPeriodVO cornerDayPeriodVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = cornerDayPeriodService.getCornerDayPeriodList(cornerDayPeriodVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, cornerDayPeriodVO);
    }
    
    /**
     * 코너별매출 설정기간별 - 리스트 상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDayPeriodVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/corner/dtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCornerDayPeriodDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, CornerDayPeriodVO cornerDayPeriodVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = cornerDayPeriodService.getCornerDayPeriodDtlList(cornerDayPeriodVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, cornerDayPeriodVO);
    }
}
