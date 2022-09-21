package kr.co.solbipos.sale.card.cardCredit.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.card.cardCredit.service.CardCreditService;
import kr.co.solbipos.sale.card.cardCredit.service.CardCreditVO;
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
 * @Class Name : CardCreditController.java
 * @Description : 광운대 > 신용카드입금관리 > 신용카드입금관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.09.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/card/cardCredit")
public class CardCreditController {

    private final SessionService sessionService;
    private final CardCreditService cardCreditService;

    /**
     * Constructor Injection
     */
    @Autowired
    public CardCreditController(SessionService sessionService, CardCreditService cardCreditService) {
        this.sessionService = sessionService;
        this.cardCreditService = cardCreditService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/cardCredit/list.sb", method = RequestMethod.GET)
    public String cardCreditView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/card/cardCredit/cardCredit";
    }

    /**
     * 신용카드입금관리 - 조회
     *
     * @param cardCreditVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 09. 08.
     */
    @RequestMapping(value = "/cardCredit/getCardCreditList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardCreditList(CardCreditVO cardCreditVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = cardCreditService.getCardCreditList(cardCreditVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, cardCreditVO);
    }

    /**
     * 신용카드입금관리 - 저장
     *
     * @param cardCreditVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 09. 14.
     */
    @RequestMapping(value = "/cardCredit/getCardCreditSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardCreditSave(@RequestBody CardCreditVO[] cardCreditVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = cardCreditService.getCardCreditSave(cardCreditVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 신용카드입금관리 엑셀업로드 팝업 - 업로드시 임시테이블 저장
     *
     * @param cardCreditVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 09. 19.
     */
    @RequestMapping(value = "/cardCreditExcelUploadAdd/getCardCreditExcelUploadAddSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardCreditExcelUploadAddSave(@RequestBody CardCreditVO[] cardCreditVOs, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = cardCreditService.getCardCreditExcelUploadAddSave(cardCreditVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 신용카드입금관리 엑셀업로드 팝업 - 검증결과 전체 삭제
     *
     * @param cardCreditVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 09. 20.
     */
    @RequestMapping(value = "/cardCreditExcelUploadAdd/getCardCreditExcelUploadAddDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardCreditExcelUploadAddDeleteAll(@RequestBody CardCreditVO cardCreditVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = cardCreditService.getCardCreditExcelUploadAddDeleteAll(cardCreditVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 신용카드입금관리 엑셀업로드 팝업 - 업로드된 입금내역 저장
     *
     * @param cardCreditVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 09. 20.
     */
    @RequestMapping(value = "/cardCreditExcelUploadAdd/getCardCreditExcelUploadAddRealSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCardCreditExcelUploadAddRealSave(@RequestBody CardCreditVO cardCreditVO, HttpServletRequest request,
                                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = cardCreditService.getCardCreditExcelUploadAddRealSave(cardCreditVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}