package kr.co.solbipos.sale.moms.prodSaleDayStoreMoms.web;

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
import kr.co.solbipos.sale.moms.prodSaleDayStoreMoms.service.ProdSaleDayStoreMomsService;
import kr.co.solbipos.sale.moms.prodSaleDayStoreMoms.service.ProdSaleDayStoreMomsVO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : ProdSaleDayStoreMomsController.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출일별(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/moms/prodSaleDayStoreMoms")
public class ProdSaleDayStoreMomsController {

    private final SessionService sessionService;
    private final ProdSaleDayStoreMomsService prodSaleDayStoreMomsService;
    private final CmmCodeUtil cmmCodeUtil;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleDayStoreMomsController(SessionService sessionService, ProdSaleDayStoreMomsService prodSaleDayStoreMomsService, CmmCodeUtil cmmCodeUtil, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.prodSaleDayStoreMomsService = prodSaleDayStoreMomsService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.dayProdService = dayProdService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodSaleDayStoreMoms/list.sb", method = RequestMethod.GET)
    public String prodSaleDayStoreMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

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
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

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

        return "sale/moms/prodSaleDayStoreMoms/prodSaleDayStoreMoms";
    }

    /**
     * 상품매출일별(매장) - 조회
     *
     * @param prodSaleDayStoreMomsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 12. 07.
     */
    @RequestMapping(value = "/prodSaleDayStoreMoms/getProdSaleDayStoreMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDayStoreMomsList(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleDayStoreMomsService.getProdSaleDayStoreMomsList(prodSaleDayStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleDayStoreMomsVO);
    }

    /**
     * 상품매출일별(매장) - 엑셀다운로드 조회
     *
     * @param prodSaleDayStoreMomsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 12. 07.
     */
    @RequestMapping(value = "/prodSaleDayStoreMoms/getProdSaleDayStoreMomsExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDayStoreMomsExcelList(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleDayStoreMomsService.getProdSaleDayStoreMomsExcelList(prodSaleDayStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleDayStoreMomsVO);
    }

    /**
     * 상품매출일별(매장) - 분할 엑셀다운로드 조회
     *
     * @param prodSaleDayStoreMomsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 01. 19.
     */
    @RequestMapping(value = "/prodSaleDayStoreMoms/getProdSaleDayStoreMomsExcelDivisionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDayStoreMomsExcelDivisionList(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleDayStoreMomsService.getProdSaleDayStoreMomsExcelDivisionList(prodSaleDayStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleDayStoreMomsVO);
    }

    /**
     * 상품매출일별(매장) - 분할 엑셀다운로드 사용자 제한 체크
     *
     * @param prodSaleDayStoreMomsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 01. 24.
     */
    @RequestMapping(value = "/prodSaleDayStoreMoms/getDivisionExcelDownloadUserIdChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadUserIdChk(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, HttpServletRequest request,
                                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodSaleDayStoreMomsService.getDivisionExcelDownloadUserIdChk(prodSaleDayStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 상품매출일별(매장) - 엑셀다운로드 기능 사용자 저장 insert
     *
     * @param prodSaleDayStoreMomsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 01. 24.
     */
    @RequestMapping(value = "/prodSaleDayStoreMoms/getDivisionExcelDownloadSaveInsert.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadSaveInsert(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, HttpServletRequest request,
                                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodSaleDayStoreMomsService.getDivisionExcelDownloadSaveInsert(prodSaleDayStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 상품매출일별(매장) - 엑셀다운로드 진행 사용자 현재 인원수 체크
     *
     * @param prodSaleDayStoreMomsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 01. 24.
     */
    @RequestMapping(value = "/prodSaleDayStoreMoms/getDivisionExcelDownloadCntChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadCntChk(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = prodSaleDayStoreMomsService.getDivisionExcelDownloadCntChk(prodSaleDayStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
}