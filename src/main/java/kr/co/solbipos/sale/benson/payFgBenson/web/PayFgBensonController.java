package kr.co.solbipos.sale.benson.payFgBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.payFgBenson.service.PayFgBensonService;
import kr.co.solbipos.sale.benson.payFgBenson.service.PayFgBensonVO;
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
 * @Class Name : PayFgBensonController.java
 * @Description : 벤슨 > 결제수단별 매출 > 결제수단별 일 매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/benson/payFgBenson")
public class PayFgBensonController {

    private final SessionService sessionService;
    private final PayFgBensonService payFgBensonService;
    private final DayProdService dayProdService;
    private final DayService dayService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PayFgBensonController(SessionService sessionService, PayFgBensonService payFgBensonService, DayProdService dayProdService, DayService dayService) {
        this.sessionService = sessionService;
        this.payFgBensonService = payFgBensonService;
        this.dayProdService = dayProdService;
        this.dayService = dayService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/payFgBenson/list.sb", method = RequestMethod.GET)
    public String payFgBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        StoreChannelVO storeChannelVO = new StoreChannelVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        // 결제수단 조회(현금영수증 포함)
        List<DefaultMap<String>> payColList = dayService.getPayColList(dayVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        return "sale/benson/payFgBenson/payFgBenson";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payFgBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.10
     */
    @RequestMapping(value = "/payFgBenson/getPayFgBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayFgBensonList(HttpServletRequest request, HttpServletResponse response, Model model, PayFgBensonVO payFgBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = payFgBensonService.getPayFgBensonList(payFgBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payFgBensonVO);
    }

    /**
     * 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payFgBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.10
     */
    @RequestMapping(value = "/payFgBenson/getPayFgBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayFgBensonExcelList(HttpServletRequest request, HttpServletResponse response, Model model, PayFgBensonVO payFgBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = payFgBensonService.getPayFgBensonExcelList(payFgBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payFgBensonVO);
    }

}
