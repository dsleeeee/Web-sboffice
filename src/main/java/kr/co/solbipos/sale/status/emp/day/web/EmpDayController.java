package kr.co.solbipos.sale.status.emp.day.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.emp.day.service.EmpDayService;
import kr.co.solbipos.sale.status.emp.day.service.EmpDayVO;
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
 * @Description : 매출관리 > 매출현황 > 판매자일자별 상세현황
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
public class EmpDayController {
    private final SessionService sessionService;
    private final EmpDayService empDayService;

    @Autowired
    public EmpDayController(SessionService sessionService, EmpDayService empDayService) {
        this.sessionService = sessionService;
        this.empDayService = empDayService;
    }


    /**
     * 판매자일자별현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 01. 22.
     */
    @RequestMapping(value = "/day/view.sb", method = RequestMethod.GET)
    public String empDayView(HttpServletRequest request, HttpServletResponse response, Model model) {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
   	
        return "sale/status/emp/empSale";
    }


    /**
     * 판매자별 매출 -일자별 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   empDayVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 22.
     */
    @RequestMapping(value = "/day/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpDayList(HttpServletRequest request, HttpServletResponse response, Model model, EmpDayVO empDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = empDayService.getEmpDayList(empDayVO, sessionInfoVO);
        
        List<DefaultMap<String>> empColList = empDayService.getEmpMebList(empDayVO, sessionInfoVO);
        
        // 판매자 코드를 , 로 연결하는 문자열 생성
        String empCol = "";
        for(int i=0; i < empColList.size(); i++) {
            empCol += (empCol.equals("") ? "" : ",") + empColList.get(i).getStr("payCd");
        }
        model.addAttribute("empColList", empColList);
        model.addAttribute("empCol", empCol);

        return ReturnUtil.returnListJson(Status.OK, list, empDayVO);
    }
    
    /**
     * 판매자별 매출 -일자별 리스트(엑셀) 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   empDayVO
     * @return  String
     * @author  박지선
     * @since   2020. 04. 22.
     */
    @RequestMapping(value = "/day/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpDayExcelList(HttpServletRequest request, HttpServletResponse response, Model model, EmpDayVO empDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = empDayService.getEmpDayExcelList(empDayVO, sessionInfoVO);
        
        List<DefaultMap<String>> empColList = empDayService.getEmpMebList(empDayVO, sessionInfoVO);
        
        // 판매자 코드를 , 로 연결하는 문자열 생성
        String empCol = "";
        for(int i=0; i < empColList.size(); i++) {
            empCol += (empCol.equals("") ? "" : ",") + empColList.get(i).getStr("payCd");
        }
        model.addAttribute("empColList", empColList);
        model.addAttribute("empCol", empCol);

        return ReturnUtil.returnListJson(Status.OK, list, empDayVO);
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
    @RequestMapping(value = "/day/empList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpMebList(HttpServletRequest request, HttpServletResponse response, Model model, EmpDayVO empDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = empDayService.getEmpMebList(empDayVO, sessionInfoVO);
                
        return ReturnUtil.returnListJson(Status.OK, result, empDayVO);
    }
}
