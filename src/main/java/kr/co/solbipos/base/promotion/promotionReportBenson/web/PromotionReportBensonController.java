package kr.co.solbipos.base.promotion.promotionReportBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.promotion.promotionReportBenson.service.PromotionReportBensonService;
import kr.co.solbipos.base.promotion.promotionReportBenson.service.PromotionReportBensonVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelService;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
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
 * @Class Name : PromotionReportBensonController.java
 * @Description : 벤슨 > 프로모션관리 > 프로모션정산
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.10.13  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/promotion/promotionReportBenson")
public class PromotionReportBensonController {

    private final SessionService sessionService;
    private final PromotionReportBensonService promotionReportBensonService;
    private final DayService dayService;
    private final StoreChannelService storeChannelService;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PromotionReportBensonController(SessionService sessionService, PromotionReportBensonService promotionReportBensonService, DayService dayService, StoreChannelService storeChannelService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.promotionReportBensonService = promotionReportBensonService;
        this.dayService = dayService;
        this.storeChannelService = storeChannelService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/promotionReportBenson/list.sb", method = RequestMethod.GET)
    public String promotionReportBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        StoreChannelVO storeChannelVO = new StoreChannelVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        return "base/promotion/promotionReportBenson/promotionReportBenson";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   promotionReportBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.10.13
     */
    @RequestMapping(value = "/promotionReportBenson/getPromotionReportBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionReportBensonList(HttpServletRequest request, HttpServletResponse response, Model model, PromotionReportBensonVO promotionReportBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = promotionReportBensonService.getPromotionReportBensonList(promotionReportBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, promotionReportBensonVO);
    }

    /**
     * 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   promotionReportBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.10.13
     */
    @RequestMapping(value = "/promotionReportBenson/getPromotionReportBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionReportBensonExcelList(HttpServletRequest request, HttpServletResponse response, Model model, PromotionReportBensonVO promotionReportBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = promotionReportBensonService.getPromotionReportBensonExcelList(promotionReportBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, promotionReportBensonVO);
    }

    /**
     * 프로모션정산 상세 조회
     * @param request
     * @param response
     * @param model
     * @param promotionReportBensonVO
     * @return
     * @author  김유승
     * @since   2026.10.13
     */
    @RequestMapping(value = "/promotionReportBenson/getPromotionReportBensonDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPromotionReportBensonDtlList(HttpServletRequest request, HttpServletResponse response, Model model, PromotionReportBensonVO promotionReportBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = promotionReportBensonService.getPromotionReportBensonDtlList(promotionReportBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, promotionReportBensonVO);
    }

}
