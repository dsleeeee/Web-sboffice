package kr.co.solbipos.sale.status.prod.hour.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.hour.service.ProdHourService;
import kr.co.solbipos.sale.status.prod.hour.service.ProdHourVO;
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
 * @Class Name : ProdHourController.java
 * @Description : 매출관리 > 매출현황 > 상품별 > 시간대별 탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.08  김진      최초생성
 *
 * @author 솔비포스 
 * @since 2020.01.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/prod")
public class ProdHourController {
    private final SessionService sessionService;
    private final ProdHourService prodHourService;

    @Autowired
    public ProdHourController(SessionService sessionService, ProdHourService prodHourService) {
        this.sessionService = sessionService;
        this.prodHourService = prodHourService;
    }

    /**
     * 시간대별현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 01. 08.
     */
    @RequestMapping(value = "/hour/view.sb", method = RequestMethod.GET)
    public String prodHourView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/prod/hour/pordHour";
    }

    /**
     * 시간대별탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodHourVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 08.
     */
    @RequestMapping(value = "/hour/getProdHourList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdHourList(HttpServletRequest request, HttpServletResponse response, Model model, ProdHourVO prodHourVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodHourService.getProdHourList(prodHourVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodHourVO);
    }
    
    /**
     * 시간대별탭 - 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodHourVO
     * @return  String
     * @author  서재식
     * @since   2020. 04. 23.
     */
    @RequestMapping(value = "/hour/getProdHourExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdHourExcelList(HttpServletRequest request, HttpServletResponse response, Model model, ProdHourVO prodHourVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodHourService.getProdHourExcelList(prodHourVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodHourVO);
    }
    
}