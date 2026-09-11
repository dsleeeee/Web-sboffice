package kr.co.solbipos.sale.benson.storePayMonthBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.storePayMonthBenson.service.StorePayMonthBensonService;
import kr.co.solbipos.sale.benson.storePayMonthBenson.service.StorePayMonthBensonVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
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
 * @Class Name : StorePayMonthBensonController.java
 * @Description : 벤슨 > 결제수단매출 > 매장-월별결제수단매출
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
@RequestMapping("/sale/benson/storePayMonthBenson")
public class StorePayMonthBensonController {

    private final SessionService sessionService;
    private final StorePayMonthBensonService storePayMonthBensonService;
    private final DayService dayService;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StorePayMonthBensonController(SessionService sessionService, StorePayMonthBensonService storePayMonthBensonService, DayService dayService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.storePayMonthBensonService = storePayMonthBensonService;
        this.dayService = dayService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/storePayMonthBenson/list.sb", method = RequestMethod.GET)
    public String storePayMonthBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        StoreChannelVO storeChannelVO = new StoreChannelVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = dayService.getPayColList(dayVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        return "sale/benson/storePayMonthBenson/storePayMonthBenson";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storePayMonthBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/storePayMonthBenson/getStorePayMonthBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStorePayMonthBensonList(HttpServletRequest request, HttpServletResponse response, Model model, StorePayMonthBensonVO storePayMonthBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storePayMonthBensonService.getStorePayMonthBensonList(storePayMonthBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storePayMonthBensonVO);
    }

    /**
     * 엑셀조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storePayMonthBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/storePayMonthBenson/getStorePayMonthBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStorePayMonthBensonExcelList(HttpServletRequest request, HttpServletResponse response, Model model, StorePayMonthBensonVO storePayMonthBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storePayMonthBensonService.getStorePayMonthBensonExcelList(storePayMonthBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storePayMonthBensonVO);
    }

}
