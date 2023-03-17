package kr.co.solbipos.base.prod.soldOut.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.soldOut.service.SoldOutService;
import kr.co.solbipos.base.prod.soldOut.service.SoldOutVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : SoldOutController.java
 * @Description : 기초관리 - 상품관리 - 품절관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.02.25  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.02.25
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/soldOut")
public class SoldOutController {

    private final SessionService sessionService;
    private final SoldOutService soldOutService;
    private final ProdService prodService;
    private final CmmEnvUtil cmmEnvUtil;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;


    /** Constructor Injection */
    @Autowired
    public SoldOutController(SessionService sessionService, SoldOutService soldOutService, ProdService prodService, CmmEnvUtil cmmEnvUtil, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.soldOutService = soldOutService;
        this.prodService = prodService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 상품조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping(value = "soldOut/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 내점/배달/포장 가격관리 사용여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));
        }else{
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));
        }

        // (상품관리)브랜드사용여부
            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));

        // 매장상품제한구분 사용여부(매장 세트구성상품 등록시 사용, 매장에서 사용하지만 본사환경설정값으로 여부파악)
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            model.addAttribute("storeProdUseFg", "0");
        } else {
            model.addAttribute("storeProdUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1100"), "0"));
        }

        // 브랜드 리스트 조회(선택 콤보박스용)
        ProdVO prodVO = new ProdVO();
        model.addAttribute("brandList", convertToJson(prodService.getBrandList(prodVO, sessionInfoVO)));

        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        /** 맘스터치 */
        // [1250 맘스터치] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
        }
        /** //맘스터치 */

        return "base/prod/soldOut/soldOutTab";
    }

    /**
     * 상품조회
     *
     * @param soldOutVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "soldOut/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(SoldOutVO soldOutVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = soldOutService.getProdList(soldOutVO, sessionInfoVO);

        return returnListJson(Status.OK, list, soldOutVO);
    }

    /**
     * 상품상세조회
     * @param soldOutVO SoldOutVO
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "soldOut/detail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdDetail(SoldOutVO soldOutVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return returnJson(Status.OK, "list", soldOutService.getProdDetail(soldOutVO, sessionInfoVO));
    }

    /**
     * 상품 품절관리 - 품절여부 저장
     * @param soldOutVOs SoldOutVO[]
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "soldOut/getProdSoldOutSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSoldOutSave(@RequestBody SoldOutVO[] soldOutVOs, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = soldOutService.getProdSoldOutSave(soldOutVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사이드메뉴-선택그룹 - 선택그룹 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param soldOutVO SoldOutVO
     * @param model
     * @return Result
     */
    @RequestMapping(value = "/soldOut/getMenuGrpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuGrpList(HttpServletRequest request, HttpServletResponse response,
                                  SoldOutVO soldOutVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 선택그룹 목록 조회
        List<DefaultMap<String>> list = soldOutService.getMenuGrpList(soldOutVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, soldOutVO);
    }

    /**
     * 사이드메뉴-선택분류 - 선택분류 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param soldOutVO SoldOutVO
     * @param model
     * @return Result
     */
    @RequestMapping(value = "/soldOut/getMenuClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuClassList(HttpServletRequest request, HttpServletResponse response,
                                  SoldOutVO soldOutVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 선택분류 목록 조회
        List<DefaultMap<String>> list = soldOutService.getMenuClassList(soldOutVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, soldOutVO);
    }

    /**
     * 사이드메뉴-선택메뉴 - 선택상품 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param soldOutVO SoldOutVO
     * @param model
     * @return Result
     */
    @RequestMapping(value = "/soldOut/getMenuProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuProdList(HttpServletRequest request, HttpServletResponse response,
                                  SoldOutVO soldOutVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 선택그룹 목록 조회
        List<DefaultMap<String>> list = soldOutService.getMenuProdList(soldOutVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, soldOutVO);
    }


    /**
     * 상품 품절관리 - 품절여부 저장
     * @param soldOutVOs SoldOutVO[]
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "soldOut/getSideMenuSoldOutSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuSoldOutSave(@RequestBody SoldOutVO[] soldOutVOs, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = soldOutService.getSideMenuSoldOutSave(soldOutVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
