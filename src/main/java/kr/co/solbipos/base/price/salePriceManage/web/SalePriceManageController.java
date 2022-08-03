package kr.co.solbipos.base.price.salePriceManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.salePriceManage.service.SalePriceManageService;
import kr.co.solbipos.base.price.salePriceManage.service.SalePriceManageVO;
import kr.co.solbipos.base.prod.prod.service.enums.PriceEnvFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : SalePriceManageController.java
 * @Description : 기초관리 > 가격관리 > 판매가관리(매장용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.04.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/price/salePriceManage")
public class SalePriceManageController {

    private final SessionService sessionService;
    private final SalePriceManageService salePriceManageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public SalePriceManageController(SessionService sessionService, SalePriceManageService salePriceManageService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.salePriceManageService = salePriceManageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/salePriceManage/list.sb", method = RequestMethod.GET)
    public String salePriceManageView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));
        }else{
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));

            // 본사통제구분-판매가
            model.addAttribute("salePriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0045") , "1"));
        }

        return "base/price/salePriceManage/salePriceManage";
    }

    /**
     * 판매가관리 조회
     *
     * @param salePriceManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 13.
     */
    @RequestMapping(value = "/salePriceManage/getSalePriceManageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceManageList(SalePriceManageVO salePriceManageVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = salePriceManageService.getSalePriceManageList(salePriceManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, salePriceManageVO);
    }

    /**
     * 판매가관리 저장
     *
     * @param salePriceManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 14.
     */
    @RequestMapping(value = "/salePriceManage/getSalePriceManageSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceManageSave(@RequestBody SalePriceManageVO[] salePriceManageVOs, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceManageService.getSalePriceManageSave(salePriceManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}