package kr.co.solbipos.sale.status.periodSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.periodSale.service.PeriodSaleService;
import kr.co.solbipos.sale.status.periodSale.service.PeriodSaleVO;
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
 * @Class Name : PeriodSaleController.java
 * @Description : 매출관리 > 매출현황2 > 기간매출상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.01  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.07.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/status/periodSale")
public class PeriodSaleController {

    private final SessionService sessionService;
    private final PeriodSaleService periodSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PeriodSaleController(SessionService sessionService, PeriodSaleService periodSaleService) {
        this.sessionService = sessionService;
        this.periodSaleService = periodSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/status/periodSale/periodSale";
    }

    /**
     * 기간매출상세 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param periodSaleVO
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPeriodSaleList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, PeriodSaleVO periodSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = periodSaleService.getPeriodSaleList(periodSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, periodSaleVO);
    }
}
