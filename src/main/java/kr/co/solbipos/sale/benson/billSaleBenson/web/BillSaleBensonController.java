package kr.co.solbipos.sale.benson.billSaleBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.billSaleBenson.service.BillSaleBensonService;
import kr.co.solbipos.sale.benson.billSaleBenson.service.BillSaleBensonVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
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
 * @Class Name : BillSaleBensonController.java
 * @Description : 벤슨 > 간소화화면 > 영수건별매출
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
@RequestMapping("/sale/benson/billSaleBenson")
public class BillSaleBensonController {

    private final SessionService sessionService;
    private final BillSaleBensonService billSaleBensonService;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public BillSaleBensonController(SessionService sessionService, BillSaleBensonService billSaleBensonService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.billSaleBensonService = billSaleBensonService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/billSaleBenson/list.sb", method = RequestMethod.GET)
    public String billSaleBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        return "sale/benson/billSaleBenson/billSaleBenson";
    }

    /**
     * 영수건별매출 - 조회
     *
     * @param billSaleBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/billSaleBenson/getBillSaleBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillSaleBensonList(BillSaleBensonVO billSaleBensonVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = billSaleBensonService.getBillSaleBensonList(billSaleBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, billSaleBensonVO);
    }

    /**
     * 영수건별매출 - 엑셀다운로드 조회
     *
     * @param billSaleBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/billSaleBenson/getBillSaleBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillSaleBensonExcelList(BillSaleBensonVO billSaleBensonVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = billSaleBensonService.getBillSaleBensonExcelList(billSaleBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, billSaleBensonVO);
    }

    /**
     * 영수건별매출 - 분할 엑셀다운로드 조회
     *
     * @param billSaleBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/billSaleBenson/getBillSaleBensonExcelDivisionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillSaleBensonExcelDivisionList(BillSaleBensonVO billSaleBensonVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = billSaleBensonService.getBillSaleBensonExcelDivisionList(billSaleBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, billSaleBensonVO);
    }
}
