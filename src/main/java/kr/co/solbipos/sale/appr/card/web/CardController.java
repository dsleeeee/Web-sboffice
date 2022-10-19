package kr.co.solbipos.sale.appr.card.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
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

import static kr.co.common.utils.spring.StringUtil.convertToJson;


/**
 * @Class Name : CardController.java
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
public class CardController {

    private final SessionService sessionService;
    private final CardService cardService;
    private final StoreTypeService storeTypeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public CardController(SessionService sessionService, CardService cardService, StoreTypeService storeTypeService) {
        this.sessionService = sessionService;
        this.cardService = cardService;
        this.storeTypeService = storeTypeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/card/list.sb", method = RequestMethod.GET)
    public String cardView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("hqBrandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

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


    /**
     * 신용카드 승인 조회_엑셀
     *
     * @param cardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022. 09. 30.
     */
    @RequestMapping(value = "/card/getCardExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardExcelList(CardVO cardVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = cardService.getCardExcelList(cardVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, cardVO);
    }


}