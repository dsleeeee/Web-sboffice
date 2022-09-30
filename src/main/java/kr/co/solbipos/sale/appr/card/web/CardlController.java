package kr.co.solbipos.sale.appr.card.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.appr.card.service.CardVO;
import kr.co.solbipos.sale.appr.card.service.CardService;
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
 * @Class Name : CardlController.java
 * @Description : 맘스터치 > 승인관리2 > 신용카드 승인 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.30  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/appr/card")
public class CardlController {

    private final SessionService sessionService;
    private final CardService cardService;

    /**
     * Constructor Injection
     */
    @Autowired
    public CardlController(SessionService sessionService, CardService cardService) {
        this.sessionService = sessionService;
        this.cardService = cardService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/card/list.sb", method = RequestMethod.GET)
    public String cardServiceView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/appr/card/card";
    }

    /**
     * 신용카드 승인 조회
     *
     * @param cardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022. 09. 30.
     */
    @RequestMapping(value = "/card/getCardList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardList(CardVO cardVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = cardService.getCardList(cardVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, cardVO);
    }


}