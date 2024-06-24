package kr.co.solbipos.base.promotion.artiseePromotion.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.promotion.artiseePromotion.service.ArtiseePromotionService;
import kr.co.solbipos.base.promotion.artiseePromotion.service.ArtiseePromotionVO;
import kr.co.solbipos.base.promotion.promotion.service.PromotionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : ArtiseePromotionController.java
 * @Description : 기초관리 - 프로모션관리 - 아티제전용프로모션
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.06.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.06.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/promotion/artiseePromotion")
public class ArtiseePromotionController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;
    private final ArtiseePromotionService artiseePromotionService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ArtiseePromotionController(SessionService sessionService, CmmEnvUtil cmmEnvUtil, ArtiseePromotionService artiseePromotionService) {

        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.artiseePromotionService = artiseePromotionService;
    }

    /**
     * 아티제전용프로모션 화면이동
     *
     * @param model
     * @return
     * @author 이다솜
     * @since 2024.06.13
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 진행중인 프로모션 수정여부 환경변수 값(본사 또는 매장의 환경변수 1097 사용)
        String modPromotionEnvstVal = "";
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            modPromotionEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1097"));
        }

        model.addAttribute("modPromotionEnvstVal", modPromotionEnvstVal);

        return "base/promotion/artiseePromotion/artiseePromotion";
    }

    /**
     * 아티제전용프로모션 리스트 조회
     *
     * @param model
     * @return
     * @author 이다솜
     * @since 2024.06.13
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result gerPromotionList(ArtiseePromotionVO artiseePromotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = artiseePromotionService.getPromotionList(artiseePromotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, artiseePromotionVO);
    }

    /**
     * 아티제전용프로모션 등록/수정
     *
     * @param model
     * @return
     * @author 이다솜
     * @since 2024.06.13
     */
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotion(@RequestBody ArtiseePromotionVO artiseePromotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = artiseePromotionService.savePromotion(artiseePromotionVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 아티제전용프로모션 상세 조회
     *
     * @return
     * @author 이다솜
     * @since 2024.06.13
     */
    @RequestMapping(value = "/detail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionDetail(ArtiseePromotionVO artiseePromotionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = artiseePromotionService.getPromotionDetail(artiseePromotionVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 아티제전용프로모션 적용상품 리스트 조회
     *
     * @return
     * @author 이다솜
     * @since 2024.06.13
     */
    @RequestMapping(value = "/getPromotionProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionProdList(ArtiseePromotionVO artiseePromotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = artiseePromotionService.getPromotionProdList(artiseePromotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, artiseePromotionVO);
    }

    /**
     * 아티제전용프로모션 적용상품 선택팝업 상품리스트 조회
     * @param artiseePromotionVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2024.06.13
     */
    @RequestMapping(value = "/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(ArtiseePromotionVO artiseePromotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = artiseePromotionService.getProdList(artiseePromotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, artiseePromotionVO);
    }

    /**
     * 아티제전용프로모션 적용분류 선택팝업 분류리스트 조회
     * @param artiseePromotionVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2024.06.13
     */
    @RequestMapping(value = "/getClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getClassList(ArtiseePromotionVO artiseePromotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = artiseePromotionService.getClassList(artiseePromotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, artiseePromotionVO);
    }

    /**
     * 아티제전용프로모션 적용상품, 적용분류 선택팝업 상품추가/수정/삭제
     *
     * @param artiseePromotionVOs
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2024.06.13
     * @return
     */
    @RequestMapping(value = "/savePromotionProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotionProd(@RequestBody ArtiseePromotionVO[] artiseePromotionVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = artiseePromotionService.savePromotionProd(artiseePromotionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 아티제전용프로모션 적용매장 리스트 조회
     * @param artiseePromotionVO
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2024.06.13
     */
    @RequestMapping(value = "/getPromotionStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionStoreList(ArtiseePromotionVO artiseePromotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = artiseePromotionService.getPromotionStoreList(artiseePromotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, artiseePromotionVO);
    }

    /**
     * 아티제전용프로모션 적용매장 선택팝업 매장리스트 조회
     * @param artiseePromotionVO
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2024.06.13
     */
    @RequestMapping(value = "/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(ArtiseePromotionVO artiseePromotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = artiseePromotionService.getStoreList(artiseePromotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, artiseePromotionVO);
    }

    /**
     * 아티제전용프로모션 적용매장 선택팝업 전매장적용
     *
     * @param artiseePromotionVO
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2024.06.13
     * @return
     */
    @RequestMapping(value = "/insertPromotionStoreAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result insertPromotionStoreAll(@RequestBody ArtiseePromotionVO artiseePromotionVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result= artiseePromotionService.insertPromotionStoreAll(artiseePromotionVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 아티제전용프로모션 적용매장 선택팝업 매장추가/삭제
     *
     * @param artiseePromotionVOs
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2024.06.13
     * @return
     */
    @RequestMapping(value = "/savePromotionStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotionStore(@RequestBody ArtiseePromotionVO[] artiseePromotionVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = artiseePromotionService.savePromotionStore(artiseePromotionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
