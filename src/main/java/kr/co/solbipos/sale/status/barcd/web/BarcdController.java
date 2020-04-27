package kr.co.solbipos.sale.status.barcd.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.barcd.service.BarcdService;
import kr.co.solbipos.sale.status.barcd.service.BarcdVO;

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
@RequestMapping("/sale/status/barcd")
public class BarcdController {
    private final SessionService sessionService;
    private final BarcdService barcdService;

    @Autowired
    public BarcdController(SessionService sessionService, BarcdService barcdService) {
        this.sessionService = sessionService;
        this.barcdService = barcdService;
    }


    /**
     * 코너별매출 일자별 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/barcd/view.sb", method = RequestMethod.GET)
    public String BarcdView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/barcd/barcd";
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
    @RequestMapping(value = "/barcd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBarcdList(HttpServletRequest request, HttpServletResponse response,
        Model model, BarcdVO barcdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = barcdService.getBarcdList(barcdVO, sessionInfoVO);
//        model.addAttribute(list);
//        model.addAttribute(barcdVO);
//        ReturnUtil.returnListJson(Status.OK, list, barcdVO)
        return ReturnUtil.returnListJson(Status.OK, list, barcdVO);
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
    @RequestMapping(value = "/barcdDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBarcdDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, BarcdVO barcdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = barcdService.getBarcdDtlList(barcdVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, barcdVO);
    }
    
    /**
     * 코너별매출 일자별 - 엑셀 전체 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDaylVO
     * @return  String
     * @author  박정은
     * @since   2020. 04. 23.
     */
    @RequestMapping(value = "/barcd/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBarcdExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, BarcdVO barcdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = barcdService.getBarcdExcelList(barcdVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, barcdVO);
    }
    
    /**
     * 코너별매출 일자별 - 엑셀 전체 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDaylVO
     * @return  String
     * @author  박정은
     * @since   2020. 04. 23.
     */
    @RequestMapping(value = "/barcdDtl/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBarcdDtlExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, BarcdVO barcdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = barcdService.getBarcdDtlExcelList(barcdVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, barcdVO);
    }
}
