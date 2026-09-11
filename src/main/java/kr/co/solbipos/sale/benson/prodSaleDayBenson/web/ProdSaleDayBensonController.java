package kr.co.solbipos.sale.benson.prodSaleDayBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.prodSaleDayBenson.service.ProdSaleDayBensonService;
import kr.co.solbipos.sale.benson.prodSaleDayBenson.service.ProdSaleDayBensonVO;
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
 * @Class Name : ProdSaleDayBensonController.java
 * @Description : 벤슨 > 간소화화면 > 상품매출일별
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
@RequestMapping("/sale/benson/prodSaleDayBenson")
public class ProdSaleDayBensonController {

    private final SessionService sessionService;
    private final ProdSaleDayBensonService prodSaleDayBensonService;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleDayBensonController(SessionService sessionService, ProdSaleDayBensonService prodSaleDayBensonService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.prodSaleDayBensonService = prodSaleDayBensonService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodSaleDayBenson/list.sb", method = RequestMethod.GET)
    public String prodSaleDayBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        return "sale/benson/prodSaleDayBenson/prodSaleDayBenson";
    }

    /**
     * 상품매출일별 - 조회
     *
     * @param prodSaleDayBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/prodSaleDayBenson/getProdSaleDayBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDayBensonList(ProdSaleDayBensonVO prodSaleDayBensonVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleDayBensonService.getProdSaleDayBensonList(prodSaleDayBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleDayBensonVO);
    }

    /**
     * 상품매출일별 - 엑셀다운로드 조회
     *
     * @param prodSaleDayBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/prodSaleDayBenson/getProdSaleDayBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDayBensonExcelList(ProdSaleDayBensonVO prodSaleDayBensonVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleDayBensonService.getProdSaleDayBensonExcelList(prodSaleDayBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleDayBensonVO);
    }
}
