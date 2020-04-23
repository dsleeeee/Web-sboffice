package kr.co.solbipos.sale.status.rtnStatus.day.web;

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
import kr.co.solbipos.sale.status.rtnStatus.day.service.RtnStatusDayService;
import kr.co.solbipos.sale.status.rtnStatus.day.service.RtnStatusDayVO;

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
@RequestMapping("/sale/status/rtnStatus")
public class RtnStatusDayController {
    private final SessionService sessionService;
    private final RtnStatusDayService rtnStatusDayService;

    @Autowired
    public RtnStatusDayController(SessionService sessionService, RtnStatusDayService rtnStatusDayService) {
        this.sessionService = sessionService;
        this.rtnStatusDayService = rtnStatusDayService;
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
    @RequestMapping(value = "/rtnStatus/view.sb", method = RequestMethod.GET)
    public String rtnstatusView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/rtnStatus/rtnStatusSale";
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
    @RequestMapping(value = "/day/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnstatusDayList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnStatusDayVO rtnStatusDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = rtnStatusDayService.getRtnStatusDayList(rtnStatusDayVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, rtnStatusDayVO);
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
    @RequestMapping(value = "/dayDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnstatusDayDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnStatusDayVO rtnStatusDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = rtnStatusDayService.getRtnStatusDayDtlList(rtnStatusDayVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, rtnStatusDayVO);
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
    @RequestMapping(value = "/posDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnstatusPosDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnStatusDayVO rtnStatusDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = rtnStatusDayService.getRtnStatusPosDtlList(rtnStatusDayVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, rtnStatusDayVO);
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
    @RequestMapping(value = "/prod/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnstatusProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnStatusDayVO rtnStatusDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = rtnStatusDayService.getRtnStatusProdList(rtnStatusDayVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, rtnStatusDayVO);
    }
    
    /**
     * 코너별매출 일자별 - 엑셀 전체 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDaylVO
     * @return  String
     * @author  박정은
     * @since   2020. 04. 22.
     */
    @RequestMapping(value = "/day/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnstatusDayExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnStatusDayVO rtnStatusDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = rtnStatusDayService.getRtnstatusDayExcelList(rtnStatusDayVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, rtnStatusDayVO);
    }
    
    /**
     * 코너별매출 일자별 - 엑셀 전체 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDaylVO
     * @return  String
     * @author  박정은
     * @since   2020. 04. 22.
     */
    @RequestMapping(value = "/dayDtl/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnstatusDayDtlExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnStatusDayVO rtnStatusDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = rtnStatusDayService.getRtnstatusDayDtlExcelList(rtnStatusDayVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, rtnStatusDayVO);
    }
    
    /**
     * 코너별매출 일자별 - 엑셀 전체 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDaylVO
     * @return  String
     * @author  박정은
     * @since   2020. 04. 22.
     */
    @RequestMapping(value = "/posDtl/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnstatusPosDtlExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnStatusDayVO rtnStatusDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = rtnStatusDayService.getRtnStatusPosDtlExcelList(rtnStatusDayVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, rtnStatusDayVO);
    }
    
    /**
     * 상품별 반품현황 - 엑셀 전체 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDaylVO
     * @return  String
     * @author  박정은
     * @since   2020. 04. 22.
     */
    @RequestMapping(value = "/prod/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRtnstatusProdExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, RtnStatusDayVO rtnStatusDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        
        List<DefaultMap<String>> list = rtnStatusDayService.getRtnStatusProdExcelList(rtnStatusDayVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, rtnStatusDayVO);
    }
}
