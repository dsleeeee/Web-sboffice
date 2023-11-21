package kr.co.solbipos.base.prod.sideMenuStore.web;

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
import kr.co.solbipos.base.prod.sideMenuStore.service.SideMenuStoreService;
import kr.co.solbipos.base.prod.sideMenuStore.service.SideMenuStoreVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : SideMenuStoreController.java
 * @Description : 기초관리 > 상품관리2 > 매장별사이드관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.06.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/sideMenuStore")
public class SideMenuStoreController {

    private final SessionService sessionService;
    private final SideMenuStoreService sideMenuStoreService;
    private final CmmEnvUtil cmmEnvUtil;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public SideMenuStoreController(SessionService sessionService, SideMenuStoreService sideMenuStoreService, CmmEnvUtil cmmEnvUtil, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.sideMenuStoreService = sideMenuStoreService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/sideMenuStore/list.sb", method = RequestMethod.GET)
    public String sideMenuStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);


        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
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
        /** //맘스터치 */

        return "base/prod/sideMenuStore/sideMenuStoreTab";
    }

    /**
     * 선택분류(매장별) 탭 - 조회
     *
     * @param sideMenuStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 07.
     */
    @RequestMapping(value = "/sideMenuClassStore/getSideMenuClassStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuClassStoreList(SideMenuStoreVO sideMenuStoreVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuStoreService.getSideMenuClassStoreList(sideMenuStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuStoreVO);
    }

    /**
     * 선택분류(매장별) 탭 - 저장
     *
     * @param sideMenuStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 07.
     */
    @RequestMapping(value = "/sideMenuClassStore/getSideMenuClassStoreSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuClassStoreSave(@RequestBody SideMenuStoreVO[] sideMenuStoreVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuStoreService.getSideMenuClassStoreSave(sideMenuStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 선택분류(선택분류별) 탭 - 조회
     *
     * @param sideMenuStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 07.
     */
    @RequestMapping(value = "/sideMenuClass/getSideMenuClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuClassList(SideMenuStoreVO sideMenuStoreVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuStoreService.getSideMenuClassList(sideMenuStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuStoreVO);
    }

    /**
     * 선택상품(매장별) 탭 - 조회
     *
     * @param sideMenuStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 07.
     */
    @RequestMapping(value = "/sideMenuProdStore/getSideMenuProdStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuProdStoreList(SideMenuStoreVO sideMenuStoreVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuStoreService.getSideMenuProdStoreList(sideMenuStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuStoreVO);
    }

    /**
     * 선택상품(매장별) 탭 - 저장
     *
     * @param sideMenuStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 07.
     */
    @RequestMapping(value = "/sideMenuProdStore/getSideMenuProdStoreSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuProdStoreSave(@RequestBody SideMenuStoreVO[] sideMenuStoreVOs, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuStoreService.getSideMenuProdStoreSave(sideMenuStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 선택상품(선택상품별) 탭 - 조회
     *
     * @param sideMenuStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 07.
     */
    @RequestMapping(value = "/sideMenuProd/getSideMenuProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuProdList(SideMenuStoreVO sideMenuStoreVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuStoreService.getSideMenuProdList(sideMenuStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuStoreVO);
    }

    /**
     * 선택분류(적용매장) 탭 - 조회
     *
     * @param sideMenuStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 20.
     */
    @RequestMapping(value = "/sideMenuClassRegStore/getSideMenuClassRegStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuClassRegStoreList(SideMenuStoreVO sideMenuStoreVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuStoreService.getSideMenuClassRegStoreList(sideMenuStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuStoreVO);
    }

    /**
     * 선택분류(적용매장) 탭 - 저장
     *
     * @param sideMenuStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 20.
     */
    @RequestMapping(value = "/sideMenuClassRegStore/getSideMenuClassRegStoreSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuClassRegStoreSave(@RequestBody SideMenuStoreVO[] sideMenuStoreVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuStoreService.getSideMenuClassRegStoreSave(sideMenuStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 선택분류(적용매장) 탭 - 선택분류 적용매장 전체 삭제
     *
     * @param sideMenuStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 20.
     */
    @RequestMapping(value = "/sideMenuClassRegStore/getSideMenuClassRegStoreDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuClassRegStoreDeleteAll(@RequestBody SideMenuStoreVO[] sideMenuStoreVOs, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuStoreService.getSideMenuClassRegStoreDeleteAll(sideMenuStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 선택상품(적용매장) 탭 - 조회
     *
     * @param sideMenuStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 20.
     */
    @RequestMapping(value = "/sideMenuProdRegStore/getSideMenuProdRegStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuProdRegStoreList(SideMenuStoreVO sideMenuStoreVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sideMenuStoreService.getSideMenuProdRegStoreList(sideMenuStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sideMenuStoreVO);
    }

    /**
     * 선택상품(적용매장) 탭 - 저장
     *
     * @param sideMenuStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 20.
     */
    @RequestMapping(value = "/sideMenuProdRegStore/getSideMenuProdRegStoreSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuProdRegStoreSave(@RequestBody SideMenuStoreVO[] sideMenuStoreVOs, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuStoreService.getSideMenuProdRegStoreSave(sideMenuStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 선택상품(적용매장) 탭 - 선택상품 적용매장 전체 삭제
     *
     * @param sideMenuStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 06. 20.
     */
    @RequestMapping(value = "/sideMenuProdRegStore/getSideMenuProdRegStoreDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSideMenuProdRegStoreDeleteAll(@RequestBody SideMenuStoreVO[] sideMenuStoreVOs, HttpServletRequest request,
                                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sideMenuStoreService.getSideMenuProdRegStoreDeleteAll(sideMenuStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}