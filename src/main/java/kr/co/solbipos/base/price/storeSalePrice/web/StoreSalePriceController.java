package kr.co.solbipos.base.price.storeSalePrice.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.storeSalePrice.service.StoreSalePriceService;
import kr.co.solbipos.base.price.storeSalePrice.service.StoreSalePriceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : StoreSalePriceController.java
 * @Description : 기초관리 - 가격관리 - 매장판매가현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.10  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/storeSalePrice")
public class StoreSalePriceController {

    private final SessionService sessionService;
    private final StoreSalePriceService storeSalePriceService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public StoreSalePriceController(SessionService sessionService, StoreSalePriceService storeSalePriceService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.storeSalePriceService = storeSalePriceService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 매장 판매가 현황 화면
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author  권지현
     * @since   2021.09.10
     */
    @RequestMapping(value = "/storeSalePrice/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 내점/배달/포장 가격관리 사용여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));
        }else{
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));
        }

        return "base/price/storeSalePrice/storeSalePrice";
    }

    /***
     * 조회
     * @param storeSalePriceVO
     * @param request
     * @return
     * @author  권지현
     * @since   2021.09.10
     */
    @RequestMapping(value = "/storeSalePrice/getSalePriceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceList(StoreSalePriceVO storeSalePriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = storeSalePriceService.getSalePriceList(storeSalePriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, storeSalePriceVO);
    }

    /***
     * 엑셀다운로드
     * @param storeSalePriceVO
     * @param request
     * @return
     * @author  권지현
     * @since   2021.09.13
     */
    @RequestMapping(value = "/storeSalePrice/getSalePriceExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceExcelList(StoreSalePriceVO storeSalePriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = storeSalePriceService.getSalePriceExcelList(storeSalePriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, storeSalePriceVO);
    }

}
