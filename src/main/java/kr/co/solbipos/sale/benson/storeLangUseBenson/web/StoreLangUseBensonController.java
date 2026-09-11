package kr.co.solbipos.sale.benson.storeLangUseBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
import kr.co.solbipos.sale.benson.storeLangUseBenson.service.StoreLangUseBensonService;
import kr.co.solbipos.sale.benson.storeLangUseBenson.service.StoreLangUseBensonVO;
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
 * @Class Name : StoreLangUseBensonController.java
 * @Description : 벤슨 > 매장분석 > 다국어사용현황
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
@RequestMapping("/sale/benson/storeLangUseBenson")
public class StoreLangUseBensonController {

    private final SessionService sessionService;
    private final DayProdService dayProdService;
    private final StoreLangUseBensonService storeLangUseBensonService;


    @Autowired
    public StoreLangUseBensonController(SessionService sessionService, DayProdService dayProdService, StoreLangUseBensonService storeLangUseBensonService) {
        this.sessionService = sessionService;
        this.dayProdService = dayProdService;
        this.storeLangUseBensonService = storeLangUseBensonService;
    }

    /**
     * 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/storeLangUseBenson/view.sb", method = RequestMethod.GET)
    public String empMonthView(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        StoreChannelVO storeChannelVO = new StoreChannelVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        return "sale/benson/storeLangUseBenson/storeLangUseBenson";
    }

    /**
     * 다국어 사용현황 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeLangUseBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/storeLangUseBenson/getStoreLangUseBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreLangUseBensonList(HttpServletRequest request, HttpServletResponse response,
                             Model model, StoreLangUseBensonVO storeLangUseBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeLangUseBensonService.getStoreLangUseBensonList(storeLangUseBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeLangUseBensonVO);
    }
}
