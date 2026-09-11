package kr.co.solbipos.sale.benson.storeSoldOutHistoryBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.benson.storeSoldOutHistoryBenson.service.StoreSoldOutHistoryBensonService;
import kr.co.solbipos.sale.benson.storeSoldOutHistoryBenson.service.StoreSoldOutHistoryBensonVO;
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
 * @Class Name  : StoreSoldOutHistoryBensonController.java
 * @Description : 벤슨 > 매장분석 > 매장품절현황
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
@RequestMapping("/sale/benson/storeSoldOutHistoryBenson")
public class StoreSoldOutHistoryBensonController {

    private final SessionService sessionService;
    private final DayProdService dayProdService;
    private final StoreSoldOutHistoryBensonService storeSoldOutHistoryBensonService;

    /**
     *  Constructor Injection
     */
    @Autowired
    public StoreSoldOutHistoryBensonController(SessionService sessionService, DayProdService dayProdService, StoreSoldOutHistoryBensonService storeSoldOutHistoryBensonService) {
        this.sessionService = sessionService;
        this.dayProdService = dayProdService;
        this.storeSoldOutHistoryBensonService = storeSoldOutHistoryBensonService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/storeSoldOutHistoryBenson/view.sb", method = RequestMethod.GET)
    public String dayProdStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        return "sale/benson/storeSoldOutHistoryBenson/storeSoldOutHistoryBenson";
    }

    /**
     * 매장품절현황 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeSoldOutHistoryBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/storeSoldOutHistoryBenson/getSearchSoldOutHistory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchSoldOutHistory(HttpServletRequest request, HttpServletResponse response,
                                          Model model, StoreSoldOutHistoryBensonVO storeSoldOutHistoryBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeSoldOutHistoryBensonService.getSearchSoldOutHistory(storeSoldOutHistoryBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeSoldOutHistoryBensonVO);
    }
    /**
     * 매장품절현황 - 엑셀조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeSoldOutHistoryBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/storeSoldOutHistoryBenson/getStoreSoldOutHistoryBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSoldOutHistoryBensonExcelList(HttpServletRequest request, HttpServletResponse response,
                                           Model model, StoreSoldOutHistoryBensonVO storeSoldOutHistoryBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeSoldOutHistoryBensonService.getStoreSoldOutHistoryBensonExcelList(storeSoldOutHistoryBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeSoldOutHistoryBensonVO);
    }
}
