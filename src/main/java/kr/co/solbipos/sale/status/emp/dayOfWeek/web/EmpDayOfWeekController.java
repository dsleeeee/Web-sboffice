package kr.co.solbipos.sale.status.emp.dayOfWeek.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.emp.dayOfWeek.service.EmpDayOfWeekService;
import kr.co.solbipos.sale.status.emp.dayOfWeek.service.EmpDayOfWeekVO;

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
 * @Class Name : EmpDayOfWeekController.java
 * @Description : 매출관리 > 매출현황 > 판매자요일별 상세현황
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
public class EmpDayOfWeekController {
    private final SessionService sessionService;
    private final EmpDayOfWeekService empDayOfWeekService;

    @Autowired
    public EmpDayOfWeekController(SessionService sessionService, EmpDayOfWeekService empDayOfWeekService) {
        this.sessionService = sessionService;
        this.empDayOfWeekService = empDayOfWeekService;
    }


    /**
     * 판매자요일별현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 01. 22.
     */
    @RequestMapping(value = "/dayOfWeek/view.sb", method = RequestMethod.GET)
    public String empDayView(HttpServletRequest request, HttpServletResponse response, Model model) {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
   	
        return "sale/status/emp/dayOfWeek/empDayOfWeek";
    }


    /**
     * 판매자별 매출 - 요일별 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   empDayOfWeekVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 22.
     */
    @RequestMapping(value = "/dayOfWeek/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpDayOfWeekList(HttpServletRequest request, HttpServletResponse response, Model model, EmpDayOfWeekVO empDayOfWeekVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = empDayOfWeekService.getEmpDayOfWeekList(empDayOfWeekVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, empDayOfWeekVO);
    }
    
}
