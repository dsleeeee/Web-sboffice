package kr.co.solbipos.sale.moms.prodSaleMonthStoreMoms.web;

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
import kr.co.solbipos.sale.moms.prodSaleMonthStoreMoms.service.ProdSaleMonthStoreMomsService;
import kr.co.solbipos.sale.moms.prodSaleMonthStoreMoms.service.ProdSaleMonthStoreMomsVO;
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
 * @Class Name : ProdSaleMonthStoreMomsController.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출월별(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.12.20  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.12.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/moms/prodSaleMonthStoreMoms")
public class ProdSaleMonthStoreMomsController {

    private final SessionService sessionService;
    private final ProdSaleMonthStoreMomsService prodSaleMonthStoreMomsService;
    private final CmmCodeUtil cmmCodeUtil;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;


    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleMonthStoreMomsController(SessionService sessionService, ProdSaleMonthStoreMomsService prodSaleMonthStoreMomsService, CmmCodeUtil cmmCodeUtil, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.prodSaleMonthStoreMomsService = prodSaleMonthStoreMomsService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.dayProdService = dayProdService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param   request
     * @param   response
     * @param   model
     */
    @RequestMapping(value = "/prodSaleMonthStoreMoms/list.sb", method = RequestMethod.GET)
    public String prodSaleMonthStoreMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "sale/moms/prodSaleMonthStoreMoms/prodSaleMonthStoreMoms";
    }

    /**
     * 상품매출월별(매장) - 조회
     *
     * @param   prodSaleMonthStoreMomsVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 12. 20.
     */
    @RequestMapping(value = "/prodSaleMonthStoreMoms/getProdSaleMonthStoreMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleMonthStoreMomsList(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleMonthStoreMomsService.getProdSaleMonthStoreMomsList(prodSaleMonthStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleMonthStoreMomsVO);
    }
    /**
     * 상품매출월별(매장) - 엑셀다운로드 조회
     *
     * @param   prodSaleMonthStoreMomsVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 12. 20.
     */
    @RequestMapping(value = "/prodSaleMonthStoreMoms/getProdSaleMonthStoreMomsExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleMonthStoreMomsExcelList(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleMonthStoreMomsService.getProdSaleMonthStoreMomsExcelList(prodSaleMonthStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleMonthStoreMomsVO);
    }

    /**
     * 상품매출월별(매장) - 분할 엑셀다운로드 조회
     *
     * @param   prodSaleMonthStoreMomsVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 12. 20.
     */
    @RequestMapping(value = "/prodSaleMonthStoreMoms/getProdSaleMonthStoreMomsExcelDivisionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleMonthStoreMomsExcelDivisionList(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, HttpServletRequest request,
                                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleMonthStoreMomsService.getProdSaleMonthStoreMomsExcelDivisionList(prodSaleMonthStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleMonthStoreMomsVO);
    }

    /**
     * 상품매출월별(매장) - 분할 엑셀다운로드 사용자 제한 체크
     *
     * @param   prodSaleMonthStoreMomsVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 12. 20.
     */
    @RequestMapping(value = "/prodSaleMonthStoreMoms/getDivisionExcelDownloadUserIdChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadUserIdChk(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, HttpServletRequest request,
                                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodSaleMonthStoreMomsService.getDivisionExcelDownloadUserIdChk(prodSaleMonthStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 상품매출월별(매장) - 엑셀다운로드 진행 사용자 저장 insert
     *
     * @param   prodSaleMonthStoreMomsVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 12. 20.
     */
    @RequestMapping(value = "/prodSaleMonthStoreMoms/getDivisionExcelDownloadSaveInsert.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadSaveInsert(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, HttpServletRequest request,
                                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = prodSaleMonthStoreMomsService.getDivisionExcelDownloadSaveInsert(prodSaleMonthStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 상품매출월별(매장) - 엑셀다운로드 진행 사용자 현재 인원수 체크
     *
     * @param   prodSaleMonthStoreMomsVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 12. 20.
     */
    @RequestMapping(value = "/prodSaleMonthStoreMoms/getDivisionExcelDownloadCntChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadCntChk(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = prodSaleMonthStoreMomsService.getDivisionExcelDownloadCntChk(prodSaleMonthStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 상품매출월별(매장) - 엑셀다운로드 진행 사용자 저장 update
     *
     * @param   prodSaleMonthStoreMomsVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 12. 20.
     */
    @RequestMapping(value = "/prodSaleMonthStoreMoms/getDivisionExcelDownloadSaveUpdate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadSaveUpdate(ProdSaleMonthStoreMomsVO prodSaleMonthStoreMomsVO, HttpServletRequest request,
                                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodSaleMonthStoreMomsService.getDivisionExcelDownloadSaveUpdate(prodSaleMonthStoreMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
}