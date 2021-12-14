package kr.co.solbipos.base.promotion.promotion.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.promotion.promotion.service.PromotionService;
import kr.co.solbipos.base.promotion.promotion.service.PromotionVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
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
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : PromotionController.java
 * @Description : 기초관리 - 프로모션관리 - 프로모션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .04. 13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/promotion/promotion")
public class PromotionController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final PromotionService promotionService;
    private final StoreTypeService storeTypeService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public PromotionController(SessionService sessionService, PromotionService promotionService, StoreTypeService storeTypeService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.promotionService = promotionService;
        this.storeTypeService = storeTypeService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 프로모션관리 화면이동
     * @param model
     * @author 이다솜
     * @since 2021.04.13
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 프로모션 특이사항 환경변수 값(본사의 환경변수 1095 사용)
        String promotionEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1095"));
        model.addAttribute("promotionEnvstVal", promotionEnvstVal);

        // 진행중인 프로모션 수정여부 환경변수 값(본사 또는 매장의 환경변수 1097 사용)
        String modPromotionEnvstVal = "";
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            modPromotionEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1097"));
        }else{
            modPromotionEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1097"));
        }

        model.addAttribute("modPromotionEnvstVal", modPromotionEnvstVal);


        StoreTypeVO storeTypeVO = new StoreTypeVO();

        // 브랜드조회(콤보박스용)
        model.addAttribute("brandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        // 매장타입조회(콤보박스용)
        model.addAttribute("storeTypeList", convertToJson(storeTypeService.getStoreTypeCombo(storeTypeVO, sessionInfoVO)));

        // 메뉴그룹조회(콤보박스용)
        model.addAttribute("storeGroupList", convertToJson(storeTypeService.getStoreGroupCombo(storeTypeVO, sessionInfoVO)));

        // 프로모션 종류 조회(콤보박스용)
        model.addAttribute("promotionTypeList", convertToJson(promotionService.getPromotionTypeList()));

        return "base/promotion/promotion/promotion";
    }

    /**
     * 프로모션관리 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.13
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result gerPromotionList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getPromotionList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 등록/수정
     * @param promotionVO
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.13
     * @return
     */
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotion(@RequestBody PromotionVO promotionVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = promotionService.savePromotion(promotionVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 프로모션 상세 조회
     * @param promotionVO
     * @param request
     * @author 이다솜
     * @since 2021.04.21
     * @return
     */
    @RequestMapping(value = "/detail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionDetail(PromotionVO promotionVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = promotionService.getPromotionDetail(promotionVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 프로모션 적용상품 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.22
     * @return
     */
    @RequestMapping(value = "/getPromotionProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionProdList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getPromotionProdList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 적용상품, 혜택상품 선택팝업 상품리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.22
     * @return
     */
    @RequestMapping(value = "/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getProdList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 적용상품 선택팝업 분류리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.23
     * @return
     */
    @RequestMapping(value = "/getClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getClassList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getClassList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 적용상품 선택팝업 상품추가/수정/삭제
     *
     * @param promotionVOs
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.22
     * @return
     */
    @RequestMapping(value = "/savePromotionProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotionProd(@RequestBody PromotionVO[] promotionVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = promotionService.savePromotionProd(promotionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 프로모션 적용매장 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.23
     * @return
     */
    @RequestMapping(value = "/getPromotionStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionStoreList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getPromotionStoreList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 적용매장 선택팝업 매장리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.23
     * @return
     */
    @RequestMapping(value = "/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getStoreList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 적용매장 선택팝업 전매장적용
     *
     * @param promotionVO
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.23
     * @return
     */
    @RequestMapping(value = "/insertPromotionStoreAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result insertPromotionStoreAll(@RequestBody PromotionVO promotionVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result= promotionService.insertPromotionStoreAll(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 프로모션 적용매장 선택팝업 매장추가/삭제
     *
     * @param promotionVOs
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.23
     * @return
     */
    @RequestMapping(value = "/savePromotionStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotionStore(@RequestBody PromotionVO[] promotionVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = promotionService.savePromotionStore(promotionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 프로모션 혜택상품 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.26
     * @return
     */
    @RequestMapping(value = "/getPromotionPresentList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionPresentList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getPromotionPresentList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 혜택상품 선택팝업 상품리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.22
     * @return
     */
    @RequestMapping(value = "/getPresentProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPresentProdList(PromotionVO promotionVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = promotionService.getPresentProdList(promotionVO, sessionInfoVO);

        return returnListJson(Status.OK, list, promotionVO);
    }

    /**
     * 프로모션 혜택상품 선택팝업 상품추가/수정/삭제
     *
     * @param promotionVOs
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.04.26
     * @return
     */
    @RequestMapping(value = "/savePromotionPresent.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotionPresent(@RequestBody PromotionVO[] promotionVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = promotionService.savePromotionPresent(promotionVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 프로모션 종류 변경에 따른 필수값 저장
     * @param promotionVO
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.12.10
     * @return
     */
    @RequestMapping(value = "/savePromotionDefaultSet.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePromotionDefaultSet(@RequestBody PromotionVO promotionVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = promotionService.savePromotionDefaultSet(promotionVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
