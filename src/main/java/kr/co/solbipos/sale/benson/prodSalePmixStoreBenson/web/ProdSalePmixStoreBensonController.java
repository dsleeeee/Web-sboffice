package kr.co.solbipos.sale.benson.prodSalePmixStoreBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.prodSalePmixStoreBenson.service.ProdSalePmixStoreBensonService;
import kr.co.solbipos.sale.benson.prodSalePmixStoreBenson.service.ProdSalePmixStoreBensonVO;
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
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : ProdSalePmixStoreBensonController.java
 * @Description : 벤슨 > 간소화화면 > 상품매출(P.MIX 매장)
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
@RequestMapping("/sale/benson/prodSalePmixStoreBenson")
public class ProdSalePmixStoreBensonController {

    private final SessionService sessionService;
    private final ProdSalePmixStoreBensonService prodSalePmixStoreBensonService;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSalePmixStoreBensonController(SessionService sessionService, ProdSalePmixStoreBensonService prodSalePmixStoreBensonService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.prodSalePmixStoreBensonService = prodSalePmixStoreBensonService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodSalePmixStoreBenson/list.sb", method = RequestMethod.GET)
    public String prodSalePmixStoreBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        return "sale/benson/prodSalePmixStoreBenson/prodSalePmixStoreBenson";
    }

    /**
     * 상품매출(P.MIX 매장) - 조회
     *
     * @param prodSalePmixStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/prodSalePmixStoreBenson/getProdSalePmixStoreBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSalePmixStoreBensonList(ProdSalePmixStoreBensonVO prodSalePmixStoreBensonVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSalePmixStoreBensonService.getProdSalePmixStoreBensonList(prodSalePmixStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSalePmixStoreBensonVO);
    }

    /**
     * 상품매출(P.MIX 매장) - 엑셀다운로드 조회
     *
     * @param prodSalePmixStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/prodSalePmixStoreBenson/getProdSalePmixStoreBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSalePmixStoreBensonExcelList(ProdSalePmixStoreBensonVO prodSalePmixStoreBensonVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSalePmixStoreBensonService.getProdSalePmixStoreBensonExcelList(prodSalePmixStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSalePmixStoreBensonVO);
    }

    /**
     * 상품매출(P.MIX 매장) - 분할 엑셀다운로드 조회
     *
     * @param prodSalePmixStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/prodSalePmixStoreBenson/getProdSalePmixStoreBensonExcelDivisionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSalePmixStoreBensonExcelDivisionList(ProdSalePmixStoreBensonVO prodSalePmixStoreBensonVO, HttpServletRequest request,
                                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSalePmixStoreBensonService.getProdSalePmixStoreBensonExcelDivisionList(prodSalePmixStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSalePmixStoreBensonVO);
    }

    /**
     * 기간선택 두 날짜 사이 모든날짜 구하기
     * @param request
     * @param response
     * @param model
     * @param prodSalePmixStoreBensonVO
     * @return
     */
    @RequestMapping(value = "/prodSalePmixStoreBenson/getDateDiff.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDateDiff(HttpServletRequest request, HttpServletResponse response, Model model, ProdSalePmixStoreBensonVO prodSalePmixStoreBensonVO) {

        List<HashMap<String, String>> list = prodSalePmixStoreBensonService.getDateDiff(prodSalePmixStoreBensonVO);

        return ReturnUtil.returnListJson(Status.OK, list);
    }
}
