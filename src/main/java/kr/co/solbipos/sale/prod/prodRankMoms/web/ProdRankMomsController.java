package kr.co.solbipos.sale.prod.prodRankMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import kr.co.solbipos.sale.prod.prodRankMoms.service.ProdRankMomsService;
import kr.co.solbipos.sale.prod.prodRankMoms.service.ProdRankMomsVO;
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
 * @Class Name : ProdRankController.java
 * @Description : 맘스터치 > 상품매출분석 > 상품별 매출 순위
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.30  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/prod/prodRankMoms")
public class ProdRankMomsController {

    private final SessionService sessionService;
    private final ProdRankMomsService prodRankMomsService;
    private final StoreTypeService storeTypeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdRankMomsController(SessionService sessionService, ProdRankMomsService prodRankMomsService, StoreTypeService storeTypeService) {
        this.sessionService = sessionService;
        this.prodRankMomsService = prodRankMomsService;
        this.storeTypeService = storeTypeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodRankMoms/list.sb", method = RequestMethod.GET)
    public String prodRankMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("hqBrandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        return "sale/prod/prodRankMoms/prodRankMoms";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRankMomsVO
     * @return  String
     * @author  권지현
     * @since   2022.09.30
     */
    @RequestMapping(value = "/prodRankMoms/getProdRankList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRankList(HttpServletRequest request, HttpServletResponse response, Model model, ProdRankMomsVO prodRankMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodRankMomsService.getProdRankList(prodRankMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodRankMomsVO);
    }

    /**
     * 차트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRankMomsVO
     * @return  String
     * @author  권지현
     * @since   2022.09.30
     */
    @RequestMapping(value = "/prodRankMoms/getProdRankChartList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRankChartList(HttpServletRequest request, HttpServletResponse response,
                                       Model model, ProdRankMomsVO prodRankMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodRankMomsService.getProdRankChartList(prodRankMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodRankMomsVO);
    }

    /**
     * 엑셀 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRankMomsVO
     * @return  String
     * @author  권지현
     * @since   2022.09.30
     */
    @RequestMapping(value = "/prodRankMoms/getProdRankExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRankExcelList(HttpServletRequest request, HttpServletResponse response,
                                       Model model, ProdRankMomsVO prodRankMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodRankMomsService.getProdRankExcelList(prodRankMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodRankMomsVO);
    }
}