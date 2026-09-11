package kr.co.solbipos.sale.benson.prodSalePmixBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.prodSalePmixBenson.service.ProdSalePmixBensonService;
import kr.co.solbipos.sale.benson.prodSalePmixBenson.service.ProdSalePmixBensonVO;
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
 * @Class Name : ProdSalePmixBensonController.java
 * @Description : 벤슨 > 간소화화면 > 상품매출(P.MIX)
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
@RequestMapping("/sale/benson/prodSalePmixBenson")
public class ProdSalePmixBensonController {

    private final SessionService sessionService;
    private final ProdSalePmixBensonService prodSalePmixBensonService;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSalePmixBensonController(SessionService sessionService, ProdSalePmixBensonService prodSalePmixBensonService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.prodSalePmixBensonService = prodSalePmixBensonService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodSalePmixBenson/list.sb", method = RequestMethod.GET)
    public String prodSalePmixBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        return "sale/benson/prodSalePmixBenson/prodSalePmixBenson";
    }

    /**
     * 상품매출(P.MIX) - 조회
     *
     * @param prodSalePmixBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/prodSalePmixBenson/getProdSalePmixBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSalePmixBensonList(ProdSalePmixBensonVO prodSalePmixBensonVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSalePmixBensonService.getProdSalePmixBensonList(prodSalePmixBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSalePmixBensonVO);
    }

    /**
     * 상품매출(P.MIX) - 엑셀다운로드 조회
     *
     * @param prodSalePmixBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/prodSalePmixBenson/getProdSalePmixBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSalePmixBensonExcelList(ProdSalePmixBensonVO prodSalePmixBensonVO, HttpServletRequest request,
                                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSalePmixBensonService.getProdSalePmixBensonExcelList(prodSalePmixBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSalePmixBensonVO);
    }

    /**
     * 기간선택 두 날짜 사이 모든날짜 구하기
     * @param request
     * @param response
     * @param model
     * @param prodSalePmixBensonVO
     * @return
     */
    @RequestMapping(value = "/prodSalePmixBenson/getDateDiff.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDateDiff(HttpServletRequest request, HttpServletResponse response, Model model, ProdSalePmixBensonVO prodSalePmixBensonVO) {

        List<HashMap<String, String>> list = prodSalePmixBensonService.getDateDiff(prodSalePmixBensonVO);

        return ReturnUtil.returnListJson(Status.OK, list);
    }
}
