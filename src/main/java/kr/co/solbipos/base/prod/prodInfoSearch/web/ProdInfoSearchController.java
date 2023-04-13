package kr.co.solbipos.base.prod.prodInfoSearch.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapService;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.prod.prodInfoSearch.service.ProdInfoSearchService;
import kr.co.solbipos.base.prod.prodInfoSearch.service.ProdInfoSearchVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyService;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
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
    private final TouchKeyService touchkeyService;
    private final KioskKeyMapService kioskKeyMapService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public ProdInfoSearchController(SessionService sessionService, ProdInfoSearchService prodInfoSearchService, DayProdService dayProdService, TouchKeyService touchkeyService, KioskKeyMapService kioskKeyMapService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.prodInfoSearchService = prodInfoSearchService;
        this.dayProdService = dayProdService;
        this.touchkeyService = touchkeyService;
        this.kioskKeyMapService = kioskKeyMapService;
        this.cmmEnvUtil = cmmEnvUtil;
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

        /** 맘스터치 */
        // [1250 맘스터치] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
        }

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        // 터치키 그룹 가져오기
        TouchKeyVO params = new TouchKeyVO();
        params.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        params.setStoreCd(sessionInfoVO.getStoreCd());
        params.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        List<DefaultMap<String>> touchKeyGrpList = touchkeyService.getTouchKeyGrp(params, sessionInfoVO);
        model.addAttribute("touchKeyGrp", convertToJson(touchKeyGrpList));

        KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
        // 키오스크용 포스 조회
        List<DefaultMap<String>> kioskPosList = kioskKeyMapService.getKioskPosList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskPosList", convertToJson(kioskPosList));
        // 키오스크 키맵그룹 조회
        List<DefaultMap<String>> kioskTuClsTypeList = kioskKeyMapService.getKioskTuClsTypeList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskTuClsTypeList", convertToJson(kioskTuClsTypeList)  );

        return "base/prod/prodInfoSearch/prodInfoSearchTab";
    }


    /**
     * 상품정보 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodInfo/getProdInfo2List.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfo2List(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getProdInfo2List(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 상품정보 엑셀 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/prodInfo/getProdInfo2ExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfo2ExcelList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getProdInfo2ExcelList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
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

    /**
     * 판매터치키 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/touchKey/getTouchKeyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTouchKeyList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getTouchKeyList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 판매터치키 엑셀 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/touchKey/getTouchKeyExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTouchKeyExcelList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getTouchKeyExcelList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 키오스크키맵 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskKeyMapList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskKeyMapList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getKioskKeyMapList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

    /**
     * 키오스크키맵 엑셀 조회
     *
     * @param prodInfoSearchVO HttpServletRequest
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskKeyMapExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskKeyMapExcelList(ProdInfoSearchVO prodInfoSearchVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = prodInfoSearchService.getKioskKeyMapExcelList(prodInfoSearchVO, sessionInfoVO);

        return returnListJson(Status.OK, list, prodInfoSearchVO);
    }

}
