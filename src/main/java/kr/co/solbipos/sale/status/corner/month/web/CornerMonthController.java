package kr.co.solbipos.sale.status.corner.month.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.corner.month.service.CornerMonthService;
import kr.co.solbipos.sale.status.corner.month.service.CornerMonthVO;

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
 * @Class Name : CornerMonthController.java
 * @Description : 매출관리 > 매출현황 > 코너별매출 > 월별 탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.13  조동훤      최초생성
 *
 * @author 조동훤
 * @since 2020.01.13
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/corner")
public class CornerMonthController {
    private final SessionService sessionService;
    private final CornerMonthService cornerMonthService;

    @Autowired
    public CornerMonthController(SessionService sessionService, CornerMonthService cornerMonthService) {
        this.sessionService = sessionService;
        this.cornerMonthService = cornerMonthService;
    }


    /**
     * 코너별매출 일자별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerMonthVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/month/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCornerMonthList(HttpServletRequest request, HttpServletResponse response,
        Model model, CornerMonthVO cornerMonthVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = cornerMonthService.getCornerMonthList(cornerMonthVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, cornerMonthVO);
    }
    
    /**
     * 코너별매출 일자별 - 엑셀 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerMonthVO
     * @return  String
     * @author  조동훤
     * @since   2020. 04. 21.
     */
    @RequestMapping(value = "/month/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCornerMonthExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, CornerMonthVO cornerMonthVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = cornerMonthService.getCornerMonthExcelList(cornerMonthVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, cornerMonthVO);
    }
}
