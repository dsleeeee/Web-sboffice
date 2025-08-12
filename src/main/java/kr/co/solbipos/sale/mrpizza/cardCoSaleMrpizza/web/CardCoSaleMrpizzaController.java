package kr.co.solbipos.sale.mrpizza.cardCoSaleMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.mrpizza.cardCoSaleMrpizza.service.CardCoSaleMrpizzaService;
import kr.co.solbipos.sale.mrpizza.cardCoSaleMrpizza.service.CardCoSaleMrpizzaVO;
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
 * @Class Name : CardCoSaleMrpizzaController.java
 * @Description : 미스터피자 > 마케팅조회 > 카드사별매출
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
@RequestMapping("/sale/mrpizza/cardCoSaleMrpizza")
public class CardCoSaleMrpizzaController {

    private final SessionService sessionService;
    private final CardCoSaleMrpizzaService cardCoSaleMrpizzaService;
    private final DayService dayService;

    /**
     * Constructor Injection
     */
    @Autowired
    public CardCoSaleMrpizzaController(SessionService sessionService, CardCoSaleMrpizzaService cardCoSaleMrpizzaService, DayService dayService) {
        this.sessionService = sessionService;
        this.cardCoSaleMrpizzaService = cardCoSaleMrpizzaService;
        this.dayService = dayService;
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

        return "sale/mrpizza/cardCoSaleMrpizza/cardCoSaleMrpizza";
    }

    /**
     * 카드사별매출 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param cardCoSaleMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.08.07
     */
    @RequestMapping(value = "/getCardCoSaleMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardCoSaleMrpizzaList(HttpServletRequest request, HttpServletResponse response, Model model, CardCoSaleMrpizzaVO cardCoSaleMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = cardCoSaleMrpizzaService.getCardCoSaleMrpizzaList(cardCoSaleMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, cardCoSaleMrpizzaVO);
    }
}
