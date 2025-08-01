package kr.co.solbipos.base.prod.sidemenu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.sidemenu.service.*;
import kr.co.solbipos.base.store.storeType.service.StoreTypeService;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : SideMenuController.java
 * @Description : 기초관리 > 상품관리 > 사이드메뉴
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.14  노현수      최초생성
 * @ 2019.08.23  이다솜      사이드메뉴관리 화면 호출 시 상품관리권한 가져와 본사인지 매장인지 판단하여 등록/수정/삭제 버튼 막기
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/sideMenu")
public class SideMenuController {

    private final SideMenuService sideMenuService;
    private final StoreTypeService storeTypeService;
    private final DayProdService dayProdService;
    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public SideMenuController(SideMenuService sideMenuService, StoreTypeService storeTypeService, DayProdService dayProdService, SessionService sessionService, CmmEnvUtil cmmEnvUtil, CmmCodeUtil cmmCodeUtil) {
        this.sideMenuService = sideMenuService;
        this.storeTypeService = storeTypeService;
        this.dayProdService = dayProdService;
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 사이드메뉴 - 페이지 이동
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return String
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String templateView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 상품등록 본사 통제여부
//        ProdEnvFg prodEnvstVal = ProdEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0020"));

//        model.addAttribute("prodEnvstVal", prodEnvstVal);

        // (상품관리)브랜드사용여부
//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
//        }else{
//            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1114") , "0"));
//        }

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("brandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        // 속성, 선택메뉴조회(콤보박스용)
        SideMenuManageVO sideMenuManageVO = new SideMenuManageVO();
        model.addAttribute("sdattrClassList", convertToJson(sideMenuService.getSideMenuAttrClassCombo(sideMenuManageVO, sessionInfoVO)));
        model.addAttribute("sdselGrpList", convertToJson(sideMenuService.getSideMenuSdselGrpCdCombo(sideMenuManageVO, sessionInfoVO)));

        // [1014 포스프로그램구분] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("posVerEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1014"), "1"));
            System.out.println("posVerEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1014"), "1"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("posVerEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1014"), "1"));
            System.out.println("posVerEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1014"), "1"));
        }

        // [1261 필수선택사용여부] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("requireYnEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1261"), "0"));
            System.out.println("requireYnEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1261"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("requireYnEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1261"), "0"));
            System.out.println("requireYnEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1261"), "0"));
        }


        /** 맘스터치 */
        // [1250 맘스터치] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
        }

        // 사용자별 코드별 공통코드 콤보박스 조회
        // 팀별
        List momsTeamComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "151");
        String momsTeamComboListAll = "";
        if (momsTeamComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsTeamComboListAll = convertToJson(list);
        } else {
            momsTeamComboListAll = cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsTeamComboList", momsTeamComboListAll);
        // AC점포별
        List momsAcShopComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "152");
        String momsAcShopComboListAll = "";
        if (momsAcShopComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsAcShopComboListAll = convertToJson(list);
        } else {
            momsAcShopComboListAll = cmmCodeUtil.assmblObj(momsAcShopComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsAcShopComboList", momsAcShopComboListAll);
        // 지역구분
        List momsAreaFgComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "153");
        String momsAreaFgComboListAll = "";
        if (momsAreaFgComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsAreaFgComboListAll = convertToJson(list);
        } else {
            momsAreaFgComboListAll = cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsAreaFgComboList", momsAreaFgComboListAll);
        // 상권
        List momsCommercialComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "154");
        String momsCommercialComboListAll = "";
        if (momsCommercialComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsCommercialComboListAll = convertToJson(list);
        } else {
            momsCommercialComboListAll = cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsCommercialComboList", momsCommercialComboListAll);
        // 점포유형
        List momsShopTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "155");
        String momsShopTypeComboListAll = "";
        if (momsShopTypeComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsShopTypeComboListAll = convertToJson(list);
        } else {
            momsShopTypeComboListAll = cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsShopTypeComboList", momsShopTypeComboListAll);
        // 매장관리타입
        List momsStoreManageTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "156");
        String momsStoreManageTypeComboListAll = "";
        if (momsStoreManageTypeComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsStoreManageTypeComboListAll = convertToJson(list);
        } else {
            momsStoreManageTypeComboListAll = cmmCodeUtil.assmblObj(momsStoreManageTypeComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsStoreManageTypeComboList", momsStoreManageTypeComboListAll);

        // 사용자별 그룹 콤보박스 조회
        // 그룹
        List branchCdComboList = dayProdService.getUserBranchComboList(sessionInfoVO);
        String branchCdComboListAll = "";
        if (branchCdComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            branchCdComboListAll = convertToJson(list);
        } else {
            branchCdComboListAll = cmmCodeUtil.assmblObj(branchCdComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("branchCdComboList", branchCdComboListAll);
        // 매장그룹
        List momsStoreFg01ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "167");
        String momsStoreFg01ComboListAll = "";
        if (momsStoreFg01ComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsStoreFg01ComboListAll = convertToJson(list);
        } else {
            momsStoreFg01ComboListAll = cmmCodeUtil.assmblObj(momsStoreFg01ComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsStoreFg01ComboList", momsStoreFg01ComboListAll);
        // 매장그룹2
        List momsStoreFg02ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "169");
        String momsStoreFg02ComboListAll = "";
        if (momsStoreFg02ComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsStoreFg02ComboListAll = convertToJson(list);
        } else {
            momsStoreFg02ComboListAll = cmmCodeUtil.assmblObj(momsStoreFg02ComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsStoreFg02ComboList", momsStoreFg02ComboListAll);
        // 매장그룹3
        List momsStoreFg03ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "170");
        String momsStoreFg03ComboListAll = "";
        if (momsStoreFg03ComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsStoreFg03ComboListAll = convertToJson(list);
        } else {
            momsStoreFg03ComboListAll = cmmCodeUtil.assmblObj(momsStoreFg03ComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsStoreFg03ComboList", momsStoreFg03ComboListAll);
        // 매장그룹4
        List momsStoreFg04ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "171");
        String momsStoreFg04ComboListAll = "";
        if (momsStoreFg04ComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsStoreFg04ComboListAll = convertToJson(list);
        } else {
            momsStoreFg04ComboListAll = cmmCodeUtil.assmblObj(momsStoreFg04ComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsStoreFg04ComboList", momsStoreFg04ComboListAll);
        // 매장그룹5
        List momsStoreFg05ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "172");
        String momsStoreFg05ComboListAll = "";
        if (momsStoreFg05ComboList.isEmpty()) {
            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m = new HashMap<>();
            m.put("name", "전체");
            m.put("value", "");
            list.add(m);
            momsStoreFg05ComboListAll = convertToJson(list);
        } else {
            momsStoreFg05ComboListAll = cmmCodeUtil.assmblObj(momsStoreFg05ComboList, "name", "value", UseYn.N);
        }
        model.addAttribute("momsStoreFg05ComboList", momsStoreFg05ComboListAll);
        /** //맘스터치 */


        return "base/prod/sideMenu/sideMenu";
    }

    /**
     * 사이드메뉴-속성 - 속성분류 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuAttrClassVO SideMenuAttrClassVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/attrClass/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAttrClassList(HttpServletRequest request, HttpServletResponse response,
        SideMenuAttrClassVO sideMenuAttrClassVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 속성분류 목록 조회
        List<DefaultMap<String>> list = sideMenuService.getAttrClassList(sideMenuAttrClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, sideMenuAttrClassVO);
    }

    /**
     * 사이드메뉴-속성 - 속성분류 목록 저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuAttrClassVOs SideMenuAttrClassVO[]
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/attrClass/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAttrClassList(@RequestBody SideMenuAttrClassVO[] sideMenuAttrClassVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = sideMenuService.saveAttrClassList(sideMenuAttrClassVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사이드메뉴-속성 - 속성 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuAttrCdVO SideMenuAttrCdVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/attrCd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAttrCdList(HttpServletRequest request, HttpServletResponse response,
        SideMenuAttrCdVO sideMenuAttrCdVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 속성분류 목록 조회
        List<DefaultMap<String>> list = sideMenuService.getAttrCdList(sideMenuAttrCdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, sideMenuAttrCdVO);
    }

    /**
     * 사이드메뉴-속성 - 속성 목록 저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuAttrCdVOs SideMenuAttrCdVO[]
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/attrCd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAttrCdList(@RequestBody SideMenuAttrCdVO[] sideMenuAttrCdVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = sideMenuService.saveAttrCdList(sideMenuAttrCdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사이드메뉴-선택메뉴 - 선택그룹 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuSelGroupVO SideMenuSelGroupVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/menuGrp/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuGrpList(HttpServletRequest request, HttpServletResponse response,
        SideMenuSelGroupVO sideMenuSelGroupVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 선택그룹 목록 조회
        List<DefaultMap<String>> list = sideMenuService.getMenuGrpList(sideMenuSelGroupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, sideMenuSelGroupVO);
    }

    /**
     * 사이드메뉴-선택메뉴 - 선택그룹 목록 저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuSelGroupVOs SideMenuSelGroupVO[]
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/menuGrp/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMenuGrpList(@RequestBody SideMenuSelGroupVO[] sideMenuSelGroupVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = sideMenuService.saveMenuGrpList(sideMenuSelGroupVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사이드메뉴-선택메뉴 - 선택분류 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuSelClassVO SideMenuSelClassVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/menuClass/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuClassList(HttpServletRequest request, HttpServletResponse response,
        SideMenuSelClassVO sideMenuSelClassVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 선택그룹 목록 조회
        List<DefaultMap<String>> list = sideMenuService.getMenuClassList(sideMenuSelClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, sideMenuSelClassVO);
    }

    /**
     * 사이드메뉴-선택메뉴 - 선택분류 목록 저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuSelClassVOs SideMenuSelClassVO[]
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/menuClass/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMenuClassList(@RequestBody SideMenuSelClassVO[] sideMenuSelClassVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = sideMenuService.saveMenuClassList(sideMenuSelClassVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사이드메뉴-선택메뉴 - 선택할 상품 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuSelProdVO SideMenuSelProdVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/menuProd/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(HttpServletRequest request, HttpServletResponse response,
        SideMenuSelProdVO sideMenuSelProdVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 선택그룹 목록 조회
        List<DefaultMap<String>> list = sideMenuService.getProdList(sideMenuSelProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, sideMenuSelProdVO);
    }

    /**
     * 사이드메뉴-선택메뉴 - 선택상품 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuSelProdVO SideMenuSelProdVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/menuProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuProdList(HttpServletRequest request, HttpServletResponse response,
        SideMenuSelProdVO sideMenuSelProdVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 선택그룹 목록 조회
        List<DefaultMap<String>> list = sideMenuService.getMenuProdList(sideMenuSelProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, sideMenuSelProdVO);
    }

    /**
     * 사이드메뉴-선택메뉴 - 선택상품 목록 저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuSelProdVOs SideMenuSelProdVO[]
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/menuProd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMenuProdList(@RequestBody SideMenuSelProdVO[] sideMenuSelProdVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = sideMenuService.saveMenuProdList(sideMenuSelProdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사이드메뉴-사이드메뉴관리탭 상품 목록 조회
     *
     * @param sideMenuManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2021. 12. 21.
     */
    @RequestMapping(value = "/menuProd/getSideMenuManageProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuManageProdList(SideMenuManageVO sideMenuManageVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuService.getSideMenuManageProdList(sideMenuManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuManageVO);
    }

    /**
     * 사이드메뉴-사이드메뉴관리탭 상품정보일괄변경 저장(사이드메뉴여부, 속성, 선택메뉴)
     *
     * @param sideMenuManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2021. 12. 21.
     */
    @RequestMapping(value = "/menuProd/saveSideMenuManageProdBatch.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSideMenuManageProdBatch(@RequestBody SideMenuManageVO[] sideMenuManageVOs, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuService.saveSideMenuManageProdBatch(sideMenuManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사이드메뉴-선택메뉴 탭-선택분류복사 팝업 - 저장
     *
     * @param sideMenuSelClassVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 04. 03.
     */
    @RequestMapping(value = "/menuClass/getSdselClassCopySave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselClassCopySave(@RequestBody SideMenuSelClassVO[] sideMenuSelClassVOs, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuService.getSdselClassCopySave(sideMenuSelClassVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 속성 조회(콤보박스용)
     * @param sideMenuManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getSideMenuAttrClassCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuAttrClassCombo(SideMenuManageVO sideMenuManageVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = sideMenuService.getSideMenuAttrClassCombo(sideMenuManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, sideMenuManageVO);
    }

    /**
     * 선택메뉴 조회(콤보박스용)
     * @param sideMenuManageVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getSideMenuSdselGrpCdCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuSdselGrpCdCombo(SideMenuManageVO sideMenuManageVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = sideMenuService.getSideMenuSdselGrpCdCombo(sideMenuManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, sideMenuManageVO);
    }

    /**
     * 선택분류 적용매장등록 팝업 - 선택분류 조회
     *
     * @param sideMenuSelClassVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 05. 31.
     */
    @RequestMapping(value = "/sdselClassRegStore/getSdselClassCodeComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselClassCodeComboList(SideMenuSelClassVO sideMenuSelClassVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuService.getSdselClassCodeComboList(sideMenuSelClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuSelClassVO);
    }

    /**
     * 선택분류 적용매장등록 팝업 - 적용매장 조회
     *
     * @param sideMenuSelClassVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 05. 31.
     */
    @RequestMapping(value = "/sdselClassRegStore/getSdselClassRegStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselClassRegStoreList(SideMenuSelClassVO sideMenuSelClassVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuService.getSdselClassRegStoreList(sideMenuSelClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuSelClassVO);
    }

    /**
     * 선택분류 적용매장등록 팝업 - 미적용매장 조회
     *
     * @param sideMenuSelClassVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 05. 31.
     */
    @RequestMapping(value = "/sdselClassRegStore/getSdselClassNoRegStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselClassNoRegStoreList(SideMenuSelClassVO sideMenuSelClassVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuService.getSdselClassNoRegStoreList(sideMenuSelClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuSelClassVO);
    }

    /**
     * 선택분류 적용매장등록 팝업 - 저장
     *
     * @param sideMenuSelClassVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 05. 31.
     */
    @RequestMapping(value = "/sdselClassRegStore/getSdselClassRegStoreSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselClassRegStoreSave(@RequestBody SideMenuSelClassVO[] sideMenuSelClassVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuService.getSdselClassRegStoreSave(sideMenuSelClassVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 선택상품 적용매장등록 팝업 - 선택상품 조회
     *
     * @param sideMenuSelProdVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 05. 31.
     */
    @RequestMapping(value = "/sdselProdRegStore/getSdselProdCodeComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselProdCodeComboList(SideMenuSelProdVO sideMenuSelProdVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuService.getSdselProdCodeComboList(sideMenuSelProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuSelProdVO);
    }

    /**
     * 선택상품 적용매장등록 팝업 - 적용매장 조회
     *
     * @param sideMenuSelProdVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 05. 31.
     */
    @RequestMapping(value = "/sdselProdRegStore/getSdselProdRegStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselProdRegStoreList(SideMenuSelProdVO sideMenuSelProdVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuService.getSdselProdRegStoreList(sideMenuSelProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuSelProdVO);
    }

    /**
     * 선택상품 적용매장등록 팝업 - 미적용매장 조회
     *
     * @param sideMenuSelProdVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 05. 31.
     */
    @RequestMapping(value = "/sdselProdRegStore/getSdselProdNoRegStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselProdNoRegStoreList(SideMenuSelProdVO sideMenuSelProdVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuService.getSdselProdNoRegStoreList(sideMenuSelProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuSelProdVO);
    }

    /**
     * 선택상품 적용매장등록 팝업 - 저장
     *
     * @param sideMenuSelProdVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 05. 31.
     */
    @RequestMapping(value = "/sdselProdRegStore/getSdselProdRegStoreSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselProdRegStoreSave(@RequestBody SideMenuSelProdVO[] sideMenuSelProdVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuService.getSdselProdRegStoreSave(sideMenuSelProdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 선택분류 적용매장 전체 삭제
     *
     * @param sideMenuSelClassVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 16.
     */
    @RequestMapping(value = "/menuClass/getSdselClassRegStoreDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselClassRegStoreDeleteAll(@RequestBody SideMenuSelClassVO[] sideMenuSelClassVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuService.getSdselClassRegStoreDeleteAll(sideMenuSelClassVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 선택상품 적용매장 전체 삭제
     *
     * @param sideMenuSelProdVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 16.
     */
    @RequestMapping(value = "/menuProd/getSdselProdRegStoreDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselProdRegStoreDeleteAll(@RequestBody SideMenuSelProdVO[] sideMenuSelProdVOs, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuService.getSdselProdRegStoreDeleteAll(sideMenuSelProdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }


    /**
     * 상품정보 저장 전 체크 - 선택한 선택메뉴코드가 세트('C')이면서, 나(현재 선택한 상품)를 가진 세트가 있는지 확인
     * @param sideMenuManageVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2023. 11. 17.
     */
    @RequestMapping(value = "menuProd/getSideMenuChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuChk(@RequestBody SideMenuManageVO[] sideMenuManageVOs, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = sideMenuService.getSideMenuChk(sideMenuManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 선택상품 저장 전, 선택그룹에 이미 등록된 상품인지 확인
     * @param sideMenuSelProdVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024. 10. 17.
     */
    @RequestMapping(value = "/chkMenuProdUse.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result chkMenuProdUse(SideMenuSelProdVO sideMenuSelProdVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuService.chkMenuProdUse(sideMenuSelProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 사이드메뉴-선택메뉴 탭-선택상품복사 팝업 - 저장
     *
     * @param sideMenuSelProdVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2025. 07. 14.
     */
    @RequestMapping(value = "/menuClass/getSdselProdCopySave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselProdCopySave(@RequestBody SideMenuSelProdVO[] sideMenuSelProdVOs, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuService.getSdselProdCopySave(sideMenuSelProdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
