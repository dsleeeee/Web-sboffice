package kr.co.solbipos.sale.card.cardCreditStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.card.cardCreditStatus.service.CardCreditStatusService;
import kr.co.solbipos.sale.card.cardCreditStatus.service.CardCreditStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : CardCreditStatusController.java
 * @Description : 광운대 > 신용카드입금관리 > 신용카드입금현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.16  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.09.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/card/cardCreditStatus")
public class CardCreditStatusController {

    private final SessionService sessionService;
    private final CardCreditStatusService cardCreditStatusService;

    /**
     * Constructor Injection
     */
    @Autowired
    public CardCreditStatusController(SessionService sessionService, CardCreditStatusService cardCreditStatusService) {
        this.sessionService = sessionService;
        this.cardCreditStatusService = cardCreditStatusService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/cardCreditStatus/list.sb", method = RequestMethod.GET)
    public String cardCreditStatusView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/card/cardCreditStatus/cardCreditStatus";
    }

    /**
     * 신용카드입금현황 - 조회
     *
     * @param cardCreditStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 09. 16.
     */
    @RequestMapping(value = "/cardCreditStatus/getCardCreditStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardCreditStatusList(CardCreditStatusVO cardCreditStatusVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = cardCreditStatusService.getCardCreditStatusList(cardCreditStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, cardCreditStatusVO);
    }
}