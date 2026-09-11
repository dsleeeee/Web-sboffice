package kr.co.solbipos.sale.benson.storeOpenCloseBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.storeOpenCloseBenson.service.StoreOpenCloseBensonService;
import kr.co.solbipos.sale.benson.storeOpenCloseBenson.service.StoreOpenCloseBensonVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreOpenCloseBensonController.java
 * @Description : (벤슨) 매장분석 > 매장 오픈/마감 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/benson/storeOpenCloseBenson")
public class StoreOpenCloseBensonController {

    private final SessionService sessionService;
    private final StoreOpenCloseBensonService storeOpenCloseBensonService;
    private final DayProdService dayProdService;

    public StoreOpenCloseBensonController(SessionService sessionService, StoreOpenCloseBensonService storeOpenCloseBensonService, DayProdService dayProdService){
        this.sessionService = sessionService;
        this.storeOpenCloseBensonService = storeOpenCloseBensonService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/storeOpenCloseBenson/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        return "sale/benson/storeOpenCloseBenson/storeOpenCloseBenson";
    }

    /**
     * 매장 오픈/마감 현황 - 일별 탭 조회
     * @param storeOpenCloseBensonVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/storeOpenCloseBenson/getStoreOpenCloseBensonDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOpenCloseBensonDayList(StoreOpenCloseBensonVO storeOpenCloseBensonVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeOpenCloseBensonService.getStoreOpenCloseBensonDayList(storeOpenCloseBensonVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }



    /**
     * 매장 오픈/마감 현황 - 일별 탭 상세 조회
     * @param storeOpenCloseBensonVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/storeOpenCloseBenson/getStoreOpenCloseBensonDayDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOpenCloseBensonDayDtlList(StoreOpenCloseBensonVO storeOpenCloseBensonVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeOpenCloseBensonService.getStoreOpenCloseBensonDayDtlList(storeOpenCloseBensonVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

    /**
     * 매장 오픈/마감 현황 - 월별 탭 조회
     * @param storeOpenCloseBensonVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/storeOpenCloseBenson/getStoreOpenCloseBensonMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOpenCloseBensonMonthList(StoreOpenCloseBensonVO storeOpenCloseBensonVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeOpenCloseBensonService.getStoreOpenCloseBensonMonthList(storeOpenCloseBensonVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

    /**
     * 매장 오픈/마감 현황 - 월별 탭 상세 조회
     * @param storeOpenCloseBensonVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/storeOpenCloseBenson/getStoreOpenCloseBensonMonthDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOpenCloseBensonMonthDtlList(StoreOpenCloseBensonVO storeOpenCloseBensonVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeOpenCloseBensonService.getStoreOpenCloseBensonMonthDtlList(storeOpenCloseBensonVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

}
