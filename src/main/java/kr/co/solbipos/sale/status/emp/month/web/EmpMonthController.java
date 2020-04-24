package kr.co.solbipos.sale.status.emp.month.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.emp.day.service.EmpDayVO;
import kr.co.solbipos.sale.status.emp.month.service.EmpMonthService;
import kr.co.solbipos.sale.status.emp.month.service.EmpMonthVO;

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
 * @Class Name : EmpMonthController.java
 * @Description : 매출관리 > 매출현황 > 판매자월별 상세현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.22  김진      최초생성
 *
 * @author 솔비포스 
 * @since 2020.01.22
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/emp")
public class EmpMonthController {
    private final SessionService sessionService;
    private final EmpMonthService empMonthService;

    @Autowired
    public EmpMonthController(SessionService sessionService, EmpMonthService empMonthService) {
        this.sessionService = sessionService;
        this.empMonthService = empMonthService;
    }


    /**
     * 판매자월별현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 01. 22.
     */
    @RequestMapping(value = "/month/view.sb", method = RequestMethod.GET)
    public String empMonthView(HttpServletRequest request, HttpServletResponse response, Model model) {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
   	
        return "sale/status/emp/month/empMonth";
    }


    /**
     * 판매자별 매출 - 월별 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   empMonthVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 22.
     */
    @RequestMapping(value = "/month/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpMonthList(HttpServletRequest request, HttpServletResponse response, Model model, EmpMonthVO empMonthVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = empMonthService.getEmpMonthList(empMonthVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, empMonthVO);
    }
    
    /**
     * 판매자별 매출 - 월별 리스트(엑셀) 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   empMonthVO
     * @return  String
     * @author  박지선
     * @since   2020. 04. 22.
     */
    @RequestMapping(value = "/month/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpMonthExcelList(HttpServletRequest request, HttpServletResponse response, Model model, EmpMonthVO empMonthVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = empMonthService.getEmpMonthExcelList(empMonthVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, empMonthVO);
    }
    
    /**
     * 판매자별 매출 -판매자 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   empDayVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 22.
     */
    @RequestMapping(value = "/month/empList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpMebList(HttpServletRequest request, HttpServletResponse response, Model model, EmpMonthVO empMonthVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = empMonthService.getEmpMebList(empMonthVO, sessionInfoVO);
                
        return ReturnUtil.returnListJson(Status.OK, result, empMonthVO);
    }
}
