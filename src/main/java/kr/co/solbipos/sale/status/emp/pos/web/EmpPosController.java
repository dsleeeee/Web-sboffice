package kr.co.solbipos.sale.status.emp.pos.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.emp.pos.service.EmpPosService;
import kr.co.solbipos.sale.status.emp.pos.service.EmpPosVO;

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
 * @Description : 매출관리 > 매출현황 > 판매자포스별 상세현황
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
public class EmpPosController {
    private final SessionService sessionService;
    private final EmpPosService empPosService;

    @Autowired
    public EmpPosController(SessionService sessionService, EmpPosService empPosService) {
        this.sessionService = sessionService;
        this.empPosService = empPosService;
    }


    /**
     * 판매자포스별별현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 01. 22.
     */
    @RequestMapping(value = "/pos/view.sb", method = RequestMethod.GET)
    public String empPosView(HttpServletRequest request, HttpServletResponse response, Model model) {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
   	
        return "sale/status/emp/pos/empPos";
    }


    /**
     * 판매자별 매출 - 포스별 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   empPosVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 22.
     */
    @RequestMapping(value = "/pos/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpPosList(HttpServletRequest request, HttpServletResponse response, Model model, EmpPosVO empPosVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = empPosService.getEmpPosList(empPosVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, empPosVO);
    }
    
    /**
     * 판매자별 매출 - 포스별 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   empPosVO
     * @return  String
     * @author  박지선
     * @since   2020. 04. 23.
     */
    @RequestMapping(value = "/pos/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpPosExcelList(HttpServletRequest request, HttpServletResponse response, Model model, EmpPosVO empPosVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = empPosService.getEmpPosExcelList(empPosVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, empPosVO);
    }
    
    /**
     * 판매자별 매출 -판매자 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   empPosVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 22.
     */
    @RequestMapping(value = "/pos/empList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpMebList(HttpServletRequest request, HttpServletResponse response, Model model, EmpPosVO empPosVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = empPosService.getEmpMebList(empPosVO, sessionInfoVO);
                
        return ReturnUtil.returnListJson(Status.OK, result, empPosVO);
    }
}
