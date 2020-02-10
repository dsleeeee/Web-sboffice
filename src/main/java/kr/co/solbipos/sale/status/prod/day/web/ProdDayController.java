package kr.co.solbipos.sale.status.prod.day.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.day.service.ProdDayService;
import kr.co.solbipos.sale.status.prod.day.service.ProdDayVO;
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
 * @Class Name :  ProdDayController.java
 * @Description : 매출관리 > 매출현황 > 일자별 상세현황
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
public class ProdDayController {
    private final SessionService sessionService;
    private final ProdDayService prodDayService;

    @Autowired
    public ProdDayController(SessionService sessionService, ProdDayService prodClassService) {
        this.sessionService = sessionService;
        this.prodDayService = prodClassService;
    }


    /**
     * 일자별상품현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 01. 08.
     */
    @RequestMapping(value = "/day/view.sb", method = RequestMethod.GET)
    public String prodCalssView(HttpServletRequest request, HttpServletResponse response, Model model) {
    	return "sale/status/prod/day/prodDay";
    }


    /**
     * 상품별 매출 -일자별상품 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   prodDayVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 15.
     */
    @RequestMapping(value = "/day/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdDayList(HttpServletRequest request, HttpServletResponse response, Model model, ProdDayVO prodDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodDayService.getProdDayList(prodDayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodDayVO);
    }
}
