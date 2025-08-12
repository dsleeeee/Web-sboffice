package kr.co.solbipos.sale.mrpizza.billSaleMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.mrpizza.billSaleMrpizza.service.BillSaleMrpizzaService;
import kr.co.solbipos.sale.mrpizza.billSaleMrpizza.service.BillSaleMrpizzaVO;
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
 * @Class Name : BillSaleMrpizzaController.java
 * @Description : 미스터피자 > 마케팅조회 > 영수별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/mrpizza/billSaleMrpizza")
public class BillSaleMrpizzaController {

    private final SessionService sessionService;
    private final BillSaleMrpizzaService billSaleMrpizzaService;
    private final DayService dayService;

    /**
     * Constructor Injection
     */
    @Autowired
    public BillSaleMrpizzaController(SessionService sessionService, BillSaleMrpizzaService billSaleMrpizzaService, DayService dayService) {
        this.sessionService = sessionService;
        this.billSaleMrpizzaService = billSaleMrpizzaService;
        this.dayService = dayService;
    }

    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/mrpizza/billSaleMrpizza/billSaleMrpizza";
    }

    /**
     * 영수별매출 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param billSaleMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.08.07
     */
    @RequestMapping(value = "/getBillSaleMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillSaleMrpizzaList(HttpServletRequest request, HttpServletResponse response, Model model, BillSaleMrpizzaVO billSaleMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = billSaleMrpizzaService.getBillSaleMrpizzaList(billSaleMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, billSaleMrpizzaVO);
    }
}
