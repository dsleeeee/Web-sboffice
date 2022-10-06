package kr.co.solbipos.sale.prod.prodStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.prodStore.service.ProdStoreService;
import kr.co.solbipos.sale.prod.prodStore.service.ProdStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @Class Name : ProdStoreController.java
 * @Description : 맘스터치 > 상품매출분석 > 상품별 점포매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/prod/prodStore")
public class ProdStoreController {

    private final SessionService sessionService;
    private final ProdStoreService prodStoreService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdStoreController(SessionService sessionService, ProdStoreService prodStoreService) {
        this.sessionService = sessionService;
        this.prodStoreService = prodStoreService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodStore/list.sb", method = RequestMethod.GET)
    public String prodRankMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/prod/prodStore/prodStore";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.10.06
     */
    @RequestMapping(value = "/prodStore/getProdStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdStoreList(HttpServletRequest request, HttpServletResponse response, Model model, ProdStoreVO prodStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = prodStoreService.getProdStoreList(prodStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodStoreVO);
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodStoreVO
     * @return  String
     * @author  권지현
     * @since   2022.10.06
     */
    @RequestMapping(value = "/prodStore/getProdStoreExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdStoreExcelList(HttpServletRequest request, HttpServletResponse response, Model model, ProdStoreVO prodStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = prodStoreService.getProdStoreExcelList(prodStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodStoreVO);
    }

}