package kr.co.solbipos.sale.prod.prodRankStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import kr.co.solbipos.sale.prod.prodRankStore.service.ProdRankStoreService;
import kr.co.solbipos.sale.prod.prodRankStore.service.ProdRankStoreVO;
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
 * @Class Name : ProdRankStoreController.java
 * @Description : 맘스터치 > 상품매출분석 > 상품판매순위(점포)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.05  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/prod/prodRankStore")
public class ProdRankStoreController {

    private final SessionService sessionService;
    private final ProdRankStoreService prodRankStoreService;
    private final StoreTypeService storeTypeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdRankStoreController(SessionService sessionService, ProdRankStoreService prodRankStoreService, StoreTypeService storeTypeService) {
        this.sessionService = sessionService;
        this.prodRankStoreService = prodRankStoreService;
        this.storeTypeService = storeTypeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodRankStore/list.sb", method = RequestMethod.GET)
    public String prodRankMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("hqBrandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        return "sale/prod/prodRankStore/prodRankStore";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRankStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.10.05
     */
    @RequestMapping(value = "/prodRankStore/getProdRankStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRankStoreList(HttpServletRequest request, HttpServletResponse response, Model model, ProdRankStoreVO prodRankStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = prodRankStoreService.getProdRankStoreList(prodRankStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodRankStoreVO);
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodRankStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.10.05
     */
    @RequestMapping(value = "/prodRankStore/getProdRankStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRankStoreExcelList(HttpServletRequest request, HttpServletResponse response, Model model, ProdRankStoreVO prodRankStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = prodRankStoreService.getProdRankStoreExcelList(prodRankStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodRankStoreVO);
    }

}