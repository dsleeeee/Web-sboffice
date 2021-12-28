package kr.co.solbipos.base.promotion.storeSelfPromotion.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.promotion.promotion.service.PromotionService;
import kr.co.solbipos.base.promotion.storeSelfPromotion.service.StoreSelfPromotionService;
import kr.co.solbipos.base.promotion.storeSelfPromotion.service.StoreSelfPromotionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreSelfPromotionController.java
 * @Description : 기초관리 - 프로모션관리 - 매장자체프로모션현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.07  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .09. 07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/promotion/storeSelfPromotion")
public class StoreSelfPromotionController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final StoreSelfPromotionService storeSelfPromotionService;
    private final PromotionService promotionService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public StoreSelfPromotionController(SessionService sessionService, StoreSelfPromotionService storeSelfPromotionService, PromotionService promotionService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.storeSelfPromotionService = storeSelfPromotionService;
        this.promotionService = promotionService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     * @param request
     * @param model
     * @return
     * @author  이다솜
     * @since   2021.09.07
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 프로모션 특이사항 환경변수 값(본사의 환경변수 1095 사용)
        String promotionEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1095"));
        model.addAttribute("promotionEnvstVal", promotionEnvstVal);

        // 프로모션 종류 조회(콤보박스용)
        model.addAttribute("promotionTypeList", convertToJson(promotionService.getPromotionTypeList()));

        return "base/promotion/storeSelfPromotion/storeSelfPromotion";
    }

    /**
     * 매장자체프로모션현황 - 매장자체 프로모션 조회
     * @param storeSelfPromotionVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021.09.07
     */
    @RequestMapping(value = "/getStoreSelfPromotionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSelfPromotionList(StoreSelfPromotionVO storeSelfPromotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeSelfPromotionService.getStoreSelfPromotionList(storeSelfPromotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

    /**
     * 매장자체프로모션현황 - 매장자체 프로모션 상세 조회
     * @param storeSelfPromotionVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021.09.08
     */
    @RequestMapping(value = "/getStoreSelfPromotionDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSelfPromotionDtl(StoreSelfPromotionVO storeSelfPromotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = storeSelfPromotionService.getStoreSelfPromotionDtl(storeSelfPromotionVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장자체프로모션현황 - 적용상품 리스트 조회
     * @param storeSelfPromotionVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021.09.08
     */
    @RequestMapping(value = "/getStoreSelfPromotionProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSelfPromotionProdList(StoreSelfPromotionVO storeSelfPromotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeSelfPromotionService.getStoreSelfPromotionProdList(storeSelfPromotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeSelfPromotionVO);
    }

    /**
     * 매장자체프로모션현황 - 혜택상품 리스트 조회
     * @param storeSelfPromotionVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  이다솜
     * @since   2021.09.08
     */
    @RequestMapping(value = "/getStoreSelfPromotionPresentList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSelfPromotionPresentList(StoreSelfPromotionVO storeSelfPromotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeSelfPromotionService.getStoreSelfPromotionPresentList(storeSelfPromotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, storeSelfPromotionVO);
    }
}
