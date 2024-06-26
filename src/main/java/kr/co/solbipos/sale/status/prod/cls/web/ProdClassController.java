package kr.co.solbipos.sale.status.prod.cls.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.status.prod.cls.service.ProdClassService;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgService;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgVO;
import kr.co.solbipos.sale.status.prod.cls.service.ProdClassVO;
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
 * @Class Name : ProdClassController.java
 * @Description : 매출관리 > 매출현황 > 상품별 > 분류별상품 탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.08  김진      최초생성
 *
 * @author 솔비포스 
 * @since 2019.02.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/prod")
public class ProdClassController {
    private final SessionService sessionService;
    private final ProdClassService prodClassService;
    private final ProdPayFgService prodPayFgService;
    private final DayService dayService;

    @Autowired
    public ProdClassController(SessionService sessionService, ProdClassService prodClassService, ProdPayFgService prodPayFgService, DayService dayService) {
        this.sessionService = sessionService;
        this.prodClassService = prodClassService;
        this.prodPayFgService = prodPayFgService;
        this.dayService = dayService;
    }

    /**
     * 분류별상품현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 01. 08.
     */
    @RequestMapping(value = "/class/view.sb", method = RequestMethod.GET)
    public String prodCalssView(HttpServletRequest request, HttpServletResponse response, Model model) {
    	ProdPayFgVO prodPayFgVO = new ProdPayFgVO();
    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 결제수단 조회
        List<DefaultMap<String>> payColList = prodPayFgService.getPayColList(prodPayFgVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        // 시간대 조회
        List<DefaultMap<String>> timeSlotColList = dayService.getTimeSlotList(sessionInfoVO);
        // 시간대를 , 로 연결하는 문자열 생성
        String timeSlotCol = "";
        for(int i=0; i < timeSlotColList.size(); i++) {
            timeSlotCol += (timeSlotCol.equals("") ? "" : ",") + timeSlotColList.get(i).getStr("value");
        }

        model.addAttribute("timeSlotColList", timeSlotColList);
        model.addAttribute("timeSlotCol", timeSlotCol);

        return "sale/status/prod/prodSale";
    }

    /**
     * 분류별상품탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodClassVO
     * @return  String
     * @author  안동관
     * @since   2019. 02. 15.
     */
    @RequestMapping(value = "/class/getProdClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdCalssList(HttpServletRequest request, HttpServletResponse response, Model model, ProdClassVO prodClassVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodClassService.getProdClassList(prodClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodClassVO);
    }
    
    /**
     * 분류별상품탭 - 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodClassVO
     * @return  String
     * @author  서재식
     * @since   2020. 04. 20.
     */
    
    @RequestMapping(value = "/class/getProdClassExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdCalssExcelList(HttpServletRequest request, HttpServletResponse response, Model model, ProdClassVO prodClassVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodClassService.getProdClassExcelList(prodClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodClassVO);
    }
}