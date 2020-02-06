package kr.co.solbipos.sale.anals.monthly.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.monthly.service.SaleAnalsMonthlyService;
import kr.co.solbipos.sale.anals.monthly.service.SaleAnalsMonthlyVO;
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
 * @Class Name : SaleAnalsMonthlyController.java
 * @Description : 판매 > 분석 > 월력판매분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.16  이승규      최초생성
 *
 * @author 엠투엠글로벌 이승규
 * @since 2020. 01.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/anals/monthly")
public class SaleAnalsMonthlyController {
    private final SessionService sessionService;
    private final SaleAnalsMonthlyService SaleAnalsMonthlyService;

    @Autowired
    public SaleAnalsMonthlyController(SessionService sessionService, SaleAnalsMonthlyService SaleAnalsMonthlyService) {
        this.sessionService = sessionService;
        this.SaleAnalsMonthlyService = SaleAnalsMonthlyService;
    }

    /**
     * 세트재고조정 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 11. 13.
     */
    @RequestMapping(value = "/monthly/list.sb", method = RequestMethod.GET)
    public String SaleAnalsMonthlyView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/anals/monthly/saleAnalsMonthly";
    }


    /**
     * 세트재고조정 - 세트재고조정 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   SaleAnalsMonthlyVO
     * @return  String
     * @author  안동관
     * @since   2018. 11. 13.
     */
    @RequestMapping(value = "/SaleAnalsMonthly/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleAnalsMonthlyList(HttpServletRequest request, HttpServletResponse response,
        Model model, SaleAnalsMonthlyVO SaleAnalsMonthlyVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = SaleAnalsMonthlyService.getSaleAnalsMonthlyList(SaleAnalsMonthlyVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, SaleAnalsMonthlyVO);
    }

}
