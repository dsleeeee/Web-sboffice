package kr.co.solbipos.sale.status.pos.dayPeriod.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.pos.dayPeriod.service.PosDayPeriodService;
import kr.co.solbipos.sale.status.pos.dayPeriod.service.PosDayPeriodVO;

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
@RequestMapping("/sale/status/pos")
public class PosDayPeriodController {
    private final SessionService sessionService;
    private final PosDayPeriodService posDayPeriodService;

    @Autowired
    public PosDayPeriodController(SessionService sessionService, PosDayPeriodService posDayPeriodService) {
        this.sessionService = sessionService;
        this.posDayPeriodService = posDayPeriodService;
    }


    /**
     * 코너별매출 일자별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDaylVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/pos/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayPeriodList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosDayPeriodVO posDayPeriodVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = posDayPeriodService.getPosDayPeriodList(posDayPeriodVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, posDayPeriodVO);
    }


    /**
     * 코너별매출 일자별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDaylVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/pos/dtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayPeriodDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosDayPeriodVO posDayPeriodVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = posDayPeriodService.getPosDayPeriodDtlList(posDayPeriodVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, posDayPeriodVO);
    }

    /**
     * 코너별매출 일자별 - 리스트 조회 (엑셀)
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDaylVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/pos/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayPeriodExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosDayPeriodVO posDayPeriodVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = posDayPeriodService.getPosDayPeriodExcelList(posDayPeriodVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, posDayPeriodVO);
    }


    /**
     * 코너별매출 일자별 - 상세 리스트 조회 (엑셀)
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDaylVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/pos/dtlExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosDayPeriodDtlExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, PosDayPeriodVO posDayPeriodVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = posDayPeriodService.getPosDayPeriodDtlExcelList(posDayPeriodVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, posDayPeriodVO);
    }
}
