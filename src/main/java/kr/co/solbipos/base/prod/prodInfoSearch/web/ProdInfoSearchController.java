package kr.co.solbipos.base.prod.prodInfoSearch.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.prodInfoSearch.service.ProdInfoSearchService;
import kr.co.solbipos.base.prod.prodInfoSearch.service.ProdInfoSearchVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : ProdInfoSearchController.java
 * @Description : 기초관리 - 상품관리 - 상품정보조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.23  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.12.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/prodInfoSearch")
public class ProdInfoSearchController {

    private final SessionService sessionService;
    private final ProdInfoSearchService prodInfoSearchService;
    private final DayProdService dayProdService;

    /** Constructor Injection */
    @Autowired
    public ProdInfoSearchController(SessionService sessionService, ProdInfoSearchService prodInfoSearchService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.prodInfoSearchService = prodInfoSearchService;
        this.dayProdService = dayProdService;
    }

    /**
     * 상품조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */

    @RequestMapping(value = "/prodInfoSearch/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        return "base/prod/prodInfoSearch/prodInfoSearchTab";
    }

    /**
     * 상품분류 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodClass/getProdClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getProdClassList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 상품분류 엑셀 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodClass/getProdClassExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassExcelList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getProdClassExcelList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 사이드-속성 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/sideAttr/getSideAttrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideAttrList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getSideAttrList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 사이드-속성 엑셀조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/sideAttr/getSideAttrExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideAttrExcelList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getSideAttrExcelList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 사이드-선택메뉴 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/sideMenu/getSideMenuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getSideMenuList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 사이드-선택메뉴 엑셀 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/sideMenu/getSideMenuExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuExcelList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getSideMenuExcelList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 옵션 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/option/getOptionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOptionList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getOptionList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 옵션 엑셀 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/option/getOptionExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOptionExcelList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getOptionExcelList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 상품-속성/선택메뉴/옵션 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodInfo/getProdInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfoList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getProdInfoList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 상품-속성/선택메뉴/옵션 엑셀 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodInfo/getProdInfoExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfoExcelList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getProdInfoExcelList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 원산지 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/orgplce/getOrgplceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrgplceList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getOrgplceList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 원산지 엑셀 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/orgplce/getOrgplceExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrgplceExcelList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getOrgplceExcelList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 알레르기 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/allergy/getAllergyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllergyList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getAllergyList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 알레르기 엑셀 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/allergy/getAllergyExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAllergyExcelList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getAllergyExcelList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

}
