package kr.co.solbipos.base.prod.basicSideMenu.web;

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
import kr.co.solbipos.base.prod.basicSideMenu.service.BasicSideMenuSelClassVO;
import kr.co.solbipos.base.prod.basicSideMenu.service.BasicSideMenuSelGroupVO;
import kr.co.solbipos.base.prod.basicSideMenu.service.BasicSideMenuSelProdVO;
import kr.co.solbipos.base.prod.basicSideMenu.service.BasicSideMenuService;
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
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : BasicSideMenuController.java
 * @Description : 기초관리 > 상품관리 > (기준)사이드메뉴
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.08.07  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2024.08.07
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/basicSideMenu")
public class BasicSideMenuController {

    private final BasicSideMenuService basicSideMenuService;
    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;
    private final StoreTypeService storeTypeService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public BasicSideMenuController(BasicSideMenuService basicSideMenuService, SessionService sessionService, CmmEnvUtil cmmEnvUtil, StoreTypeService storeTypeService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil){
        this.basicSideMenuService = basicSideMenuService;
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.storeTypeService = storeTypeService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String templateView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // (상품관리)브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));

        // 브랜드조회(콤보박스용)
        StoreTypeVO storeTypeVO = new StoreTypeVO();
        model.addAttribute("brandList", convertToJson(storeTypeService.getBrandList(storeTypeVO, sessionInfoVO)));

        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        // 속성, 선택메뉴조회(콤보박스용)
        //SideMenuManageVO sideMenuManageVO = new SideMenuManageVO();
        //model.addAttribute("sdattrClassList", convertToJson(sideMenuService.getSideMenuAttrClassCombo(sideMenuManageVO, sessionInfoVO)));
        //model.addAttribute("sdselGrpList", convertToJson(basicSideMenuService.getSideMenuSdselGrpCdCombo(sideMenuManageVO, sessionInfoVO)));

        // [1014 포스프로그램구분] 환경설정값 조회
        model.addAttribute("posVerEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1014"), "1"));

        // [1261 필수선택사용여부] 환경설정값 조회
        model.addAttribute("requireYnEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1261"), "0"));

        /** 맘스터치 */
        // [1250 맘스터치] 환경설정값 조회
        model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));

        // 사용자별 코드별 공통코드 콤보박스 조회
        // - 팀별
        List momsTeamComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "151");
        model.addAttribute("momsTeamComboList", momsTeamComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.N));

        // - AC점포별
        List momsAcShopComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "152");
        model.addAttribute("momsAcShopComboList", momsAcShopComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsAcShopComboList, "name", "value", UseYn.N));

        // - 지역구분
        List momsAreaFgComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "153");
        model.addAttribute("momsAreaFgComboList", momsAreaFgComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.N));

        // - 상권
        List momsCommercialComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "154");
        model.addAttribute("momsCommercialComboList", momsCommercialComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.N));

        // - 점포유형
        List momsShopTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "155");
        model.addAttribute("momsShopTypeComboList", momsShopTypeComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.N));

        // - 매장관리타입
        List momsStoreManageTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "156");
        model.addAttribute("momsStoreManageTypeComboList", momsStoreManageTypeComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreManageTypeComboList, "name", "value", UseYn.N));

        // - 사용자별 그룹 콤보박스 조회
        List branchCdComboList = dayProdService.getUserBranchComboList(sessionInfoVO);
        model.addAttribute("branchCdComboList", branchCdComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(branchCdComboList, "name", "value", UseYn.N));

        // - 매장그룹
        List momsStoreFg01ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "167");
        model.addAttribute("momsStoreFg01ComboList", momsStoreFg01ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg01ComboList, "name", "value", UseYn.N));

        // - 매장그룹2
        List momsStoreFg02ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "169");
        model.addAttribute("momsStoreFg02ComboList", momsStoreFg02ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg02ComboList, "name", "value", UseYn.N));

        // - 매장그룹3
        List momsStoreFg03ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "170");
        model.addAttribute("momsStoreFg03ComboList", momsStoreFg03ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg03ComboList, "name", "value", UseYn.N));

        // - 매장그룹4
        List momsStoreFg04ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "171");
        model.addAttribute("momsStoreFg04ComboList", momsStoreFg04ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg04ComboList, "name", "value", UseYn.N));

        // - 매장그룹5
        List momsStoreFg05ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "172");
        model.addAttribute("momsStoreFg05ComboList", momsStoreFg05ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg05ComboList, "name", "value", UseYn.N));


        return "base/prod/basicSideMenu/basicSideMenu";
    }

    /**
     * (기준)사이드메뉴 - 선택메뉴 - 선택그룹 목록 조회
     * @param request
     * @param response
     * @param basicSideMenuSelGroupVO
     * @param model
     * @return
     * @author 이다솜
     * @since 2024. 11. 14.
     */
    @RequestMapping(value = "/menuGrp/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuGrpList(HttpServletRequest request, HttpServletResponse response,
                                 BasicSideMenuSelGroupVO basicSideMenuSelGroupVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 선택그룹 목록 조회
        List<DefaultMap<String>> list = basicSideMenuService.getMenuGrpList(basicSideMenuSelGroupVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, basicSideMenuSelGroupVO);
    }

    /**
     * (기준)사이드메뉴 - 선택메뉴 - 선택그룹 저장
     * @param basicSideMenuSelGroupVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2024. 11. 14.
     */
    @RequestMapping(value = "/menuGrp/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMenuGrpList(@RequestBody BasicSideMenuSelGroupVO[] basicSideMenuSelGroupVOs, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = basicSideMenuService.saveMenuGrpList(basicSideMenuSelGroupVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * (기준)사이드메뉴 - 선택메뉴 - 선택분류 목록 조회
     * @param request
     * @param response
     * @param basicSideMenuSelClassVO
     * @param model
     * @return
     * @author 이다솜
     * @since 2024. 11. 14.
     */
    @RequestMapping(value = "/menuClass/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuClassList(HttpServletRequest request, HttpServletResponse response,
                                   BasicSideMenuSelClassVO basicSideMenuSelClassVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 선택그룹 목록 조회
        List<DefaultMap<String>> list = basicSideMenuService.getMenuClassList(basicSideMenuSelClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, basicSideMenuSelClassVO);
    }

    /**
     * (기준)사이드메뉴 - 선택메뉴 - 선택분류 저장
     * @param basicSideMenuSelClassVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2024. 11. 14.
     */
    @RequestMapping(value = "/menuClass/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMenuClassList(@RequestBody BasicSideMenuSelClassVO[] basicSideMenuSelClassVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = basicSideMenuService.saveMenuClassList(basicSideMenuSelClassVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * (기준)사이드메뉴 - 선택메뉴 - 선택상품 추가팝업 상품리스트 조회
     * @param request
     * @param response
     * @param basicSideMenuSelProdVO
     * @param model
     * @return
     * @author 이다솜
     * @since 2024. 11. 14.
     */
    @RequestMapping(value = "/menuProd/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(HttpServletRequest request, HttpServletResponse response,
                              BasicSideMenuSelProdVO basicSideMenuSelProdVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = basicSideMenuService.getProdList(basicSideMenuSelProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, basicSideMenuSelProdVO);
    }

    /**
     * (기준)사이드메뉴 - 선택메뉴 - 선택상품 목록 조회
     * @param request
     * @param response
     * @param basicSideMenuSelProdVO
     * @param model
     * @return
     * @author 이다솜
     * @since 2024. 11. 14.
     */
    @RequestMapping(value = "/menuProd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuProdList(HttpServletRequest request, HttpServletResponse response,
                                  BasicSideMenuSelProdVO basicSideMenuSelProdVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 선택그룹 목록 조회
        List<DefaultMap<String>> list = basicSideMenuService.getMenuProdList(basicSideMenuSelProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, basicSideMenuSelProdVO);
    }

    /**
     * (기준)사이드메뉴 - 선택메뉴 - 선택상품 저장
     * @param basicSideMenuSelProdVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2024. 11. 14.
     */
    @RequestMapping(value = "/menuProd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveMenuProdList(@RequestBody BasicSideMenuSelProdVO[] basicSideMenuSelProdVOs, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = basicSideMenuService.saveMenuProdList(basicSideMenuSelProdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * (기준)사이드메뉴 - 선택메뉴 -선택분류복사 팝업 저장
     * @param basicSideMenuSelClassVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author 이다솜
     * @since 2024. 11. 14.
     */
    @RequestMapping(value = "/menuClass/getSdselClassCopySave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselClassCopySave(@RequestBody BasicSideMenuSelClassVO[] basicSideMenuSelClassVOs, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0; //basicSideMenuService.getSdselClassCopySave(basicSideMenuSelClassVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }


}
