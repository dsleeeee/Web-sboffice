package kr.co.solbipos.sale.anals.goal.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.dailyreport.service.DailyReportVO;
import kr.co.solbipos.sale.anals.goal.service.GoalService;
import kr.co.solbipos.sale.anals.goal.service.GoalVO;
import kr.co.solbipos.sale.anals.store.month.service.StoreMonthVO;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : SaleGoalController.java
 * @Description : 매출관리 > 매출분석 > 매출목표관리 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.25      김진      최초생성
 *
 * @author 솔비포스 
 * @since 2020.02.25
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/anals/goal")
public class GoalController {
    private final SessionService sessionService;
    private final GoalService goalService;

    @Autowired
    public GoalController(SessionService sessionService, GoalService goalService) {
        this.sessionService = sessionService;
        this.goalService = goalService;
    }


    /**
     * 매출목표관리 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 02. 25.
     */
    @RequestMapping(value = "/saleGoal/view.sb", method = RequestMethod.GET)
    public String empMonthView(HttpServletRequest request, HttpServletResponse response, Model model) {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
   	
        return "sale/anals/goal/goal";
    }

    
    /**
     * 매출목표관리 - 일자별 목표매출 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   saleGaolVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 29.
     */
    @RequestMapping(value = "/day/dayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleGoalDayColList(HttpServletRequest request, HttpServletResponse response, Model model, GoalVO goalVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
              
        List<DefaultMap<String>> list = goalService.getSaleGoalDayColList(goalVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, goalVO);
    }


    /**
     * 매출목표관리 - 일자별 목표매출 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   saleGaolVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 25.
     */
    @RequestMapping(value = "/day/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleGoalDayList(HttpServletRequest request, HttpServletResponse response, Model model, GoalVO goalVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = goalService.getSaleGoalDayList(goalVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, goalVO);
    }
    
    /**
     * 매출목표관리 - 월별 목표매출 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   saleGaolVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 25.
     */
    @RequestMapping(value = "/month/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleGoalMonthList(HttpServletRequest request, HttpServletResponse response, Model model, GoalVO goalVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = goalService.getSaleGoalMonthList(goalVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, goalVO);
    }
    
    /**
     * 매출목표관리 - 매장 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   saleGaolVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 25.
     */
    @RequestMapping(value = "/store/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleGoalStoreList(HttpServletRequest request, HttpServletResponse response, Model model, GoalVO goalVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = goalService.getSaleGoalStoreList(goalVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, goalVO);
    }
    
    /**
     * 매출목표관리 - 매출목표 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   saleGaolVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 25.
     */
    @RequestMapping(value = "/goal/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleGoalList(HttpServletRequest request, HttpServletResponse response, Model model, GoalVO goalVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = goalService.getSaleGoalList(goalVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, goalVO);
    }
    
    /**
     * 매출목표관리 - 매출목표 상세조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   saleGaolVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 25.
     */
    @RequestMapping(value = "/goal/detail1.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleGoalDtl1List(HttpServletRequest request, HttpServletResponse response, Model model, GoalVO goalVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = goalService.getSaleGoalDtl1List(goalVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, goalVO);
    }
    
    /**
     * 매출목표관리 - 매출목표 상세조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   saleGaolVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 25.
     */
    @RequestMapping(value = "/goal/detail2.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleGoalDtl2List(HttpServletRequest request, HttpServletResponse response, Model model, GoalVO goalVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = goalService.getSaleGoalDtl2List(goalVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, goalVO);
    }
    
    /**
     * 매출목표관리 - 매출목표 등록
     * @param   request
     * @param   response
     * @param   model
     * @param   saleGaolVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 25.
     */
    @RequestMapping(value = "/goal/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSaleGoalSave(HttpServletRequest request, @RequestBody GoalVO goalVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = goalService.saveSaleGoalSave(goalVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
    
    /**
     * 매출목표관리 - 매출목표 상세등록
     * @param   request
     * @param   response
     * @param   model
     * @param   saleGaolVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 25.
     */
    @RequestMapping(value = "/goalDeatil/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSaleGoalgoalDeatilSave(@RequestBody GoalVO[] goalVOs, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = goalService.saveSaleGoalgoalDeatilSave(goalVOs, sessionInfoVO);
    
        return returnJson(Status.OK, result);
    }
    
    /**
     * 매출목표관리 - 매출목표금액TOT 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   saleGaolVO
     * @return  String
     * @author  김진
     * @since   2020. 02. 25.
     */
    @RequestMapping(value = "/goalAmtTot/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSaleGoalAmtTotSave(HttpServletRequest request, @RequestBody GoalVO goalVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = goalService.saveSaleGoalAmtTotSave(goalVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
}
