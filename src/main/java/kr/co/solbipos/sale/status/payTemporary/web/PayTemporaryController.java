package kr.co.solbipos.sale.status.payTemporary.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.status.payTemporary.service.PayTemporaryService;
import kr.co.solbipos.sale.status.payTemporary.service.PayTemporaryVO;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlService;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlVO;
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
 * @Class Name : PayTemporaryController.java
 * @Description : 맘스터치 > 매출분석2 > 가승인-상품권결제차액
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.20  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/status/payTemporary")
public class PayTemporaryController {

    private final DayProdService dayProdService;
    private final SessionService sessionService;
    private final PayTemporaryService payTemporaryService;
    private final CmmCodeUtil cmmCodeUtil;
    private final TodayDtlService todayDtlService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PayTemporaryController(DayProdService dayProdService, SessionService sessionService, PayTemporaryService payTemporaryService, CmmCodeUtil cmmCodeUtil, TodayDtlService todayDtlService) {
        this.dayProdService = dayProdService;
        this.sessionService = sessionService;
        this.payTemporaryService = payTemporaryService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.todayDtlService = todayDtlService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/payTemporary/list.sb", method = RequestMethod.GET)
    public String payTemporaryView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

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


        // 전체 없는 콤보박스 데이터
        // 사용자별 코드별 공통코드 콤보박스 조회
        // 팀별
        if (momsTeamComboList.size() > 1) {
            momsTeamComboList.remove(0);
            String momsTeamComboListAll2 = cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.N);
            model.addAttribute("momsTeamComboList2", momsTeamComboListAll2);
        } else {
            model.addAttribute("momsTeamComboList2", momsTeamComboListAll);
        }
        // AC점포별
        if (momsAcShopComboList.size() > 1) {
            momsAcShopComboList.remove(0);
            String momsAcShopComboListAll2 = cmmCodeUtil.assmblObj(momsAcShopComboList, "name", "value", UseYn.N);
            model.addAttribute("momsAcShopComboList2", momsAcShopComboListAll2);
        } else {
            model.addAttribute("momsAcShopComboList2", momsAcShopComboListAll);
        }
        // 지역구분
        if (momsAreaFgComboList.size() > 1) {
            momsAreaFgComboList.remove(0);
            String momsAreaFgComboListAll2 = cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.N);
            model.addAttribute("momsAreaFgComboList2", momsAreaFgComboListAll2);
        } else {
            model.addAttribute("momsAreaFgComboList2", momsAreaFgComboListAll);
        }
        // 상권
        if(momsCommercialComboList.size() > 1) {
            momsCommercialComboList.remove(0);
            String momsCommercialComboListAll2 = cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.N);
            model.addAttribute("momsCommercialComboList2", momsCommercialComboListAll2);
            String momsCommercialComboListAll3 = cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.SELECT);
        } else {
            model.addAttribute("momsCommercialComboList2", momsCommercialComboListAll);
        }
        // 점포유형
        if(momsShopTypeComboList.size() > 1) {
            momsShopTypeComboList.remove(0);
            String momsShopTypeComboListAll2 = cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.N);
            model.addAttribute("momsShopTypeComboList2", momsShopTypeComboListAll2);
        } else {
            model.addAttribute("momsShopTypeComboList2", momsShopTypeComboListAll);
        }
        // 매장관리타입
        if(momsStoreManageTypeComboList.size() > 1) {
            momsStoreManageTypeComboList.remove(0);

            String momsStoreManageTypeComboListAll2 = cmmCodeUtil.assmblObj(momsStoreManageTypeComboList, "name", "value", UseYn.N);
            model.addAttribute("momsStoreManageTypeComboList2", momsStoreManageTypeComboListAll2);
        } else {
            model.addAttribute("momsStoreManageTypeComboList2", momsStoreManageTypeComboListAll);
        }
        // 그룹
        if(branchCdComboList.size() > 1) {
            branchCdComboList.remove(0);
            String branchCdComboListAll2 = cmmCodeUtil.assmblObj(branchCdComboList, "name", "value", UseYn.N);
            model.addAttribute("branchCdComboList2", branchCdComboListAll2);
        } else {
            model.addAttribute("branchCdComboList2", branchCdComboListAll);
        }


        TodayDtlVO todayDtlVO = new TodayDtlVO();

        // 결제수단 조회
        List<DefaultMap<String>> payColList = todayDtlService.getPayColList(todayDtlVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        // 할인구분 조회
        List<DefaultMap<String>> dcColList = todayDtlService.getDcColList(todayDtlVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String dcCol = "";
        for(int i=0; i < dcColList.size(); i++) {
            dcCol += (dcCol.equals("") ? "" : ",") + dcColList.get(i).getStr("dcCd");
        }
        model.addAttribute("dcColList", dcColList);
        model.addAttribute("dcCol", dcCol);

        // 객수 조회
        List<DefaultMap<String>> guestColList = todayDtlService.getGuestColList(todayDtlVO, sessionInfoVO);

        // 객수 코드를 , 로 연결하는 문자열 생성
        String guestCol = "";
        for(int i=0; i < guestColList.size(); i++) {
            guestCol += (guestCol.equals("") ? "" : ",") + guestColList.get(i).getStr("guestCd");
        }
        model.addAttribute("guestColList", guestColList);
        model.addAttribute("guestCol", guestCol);


        return "sale/status/payTemporary/payTemporaryTab";
    }

    /**
     * 가승인-상품권결제차액 탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payTemporaryVO
     * @return  String
     * @author  권지현
     * @since   2023.03.20
     */
    @RequestMapping(value = "/payTemporary/getPayTemporaryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayTemporaryList(HttpServletRequest request, HttpServletResponse response, Model model, PayTemporaryVO payTemporaryVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payTemporaryService.getPayTemporaryList(payTemporaryVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payTemporaryVO);
    }

    /**
     * 가승인-상품권결제차액 탭 - 엑셀 다운로드 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payTemporaryVO
     * @return  String
     * @author  권지현
     * @since   2023.03.20
     */
    @RequestMapping(value = "/payTemporary/getPayTemporaryExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayTemporaryExcelList(HttpServletRequest request, HttpServletResponse response, Model model, PayTemporaryVO payTemporaryVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payTemporaryService.getPayTemporaryExcelList(payTemporaryVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payTemporaryVO);
    }

    /**
     * 가승인-상품권결제차액 탭 - 팝업 상세 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payTemporaryVO
     * @return  String
     * @author  권지현
     * @since   2023.03.21
     */
    @RequestMapping(value = "/payTemporary/getPayTemporaryDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayTemporaryDtlList(HttpServletRequest request, HttpServletResponse response, Model model, PayTemporaryVO payTemporaryVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = payTemporaryService.getPayTemporaryDtlList(payTemporaryVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payTemporaryVO);
    }

    /**
     * 가승인-상품권결제차액 상세내역 탭 - 조회
     *
     * @param payTemporaryVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 05. 03.
     */
    @RequestMapping(value = "/payTemporaryGift/getPayTemporaryGiftList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayTemporaryGiftList(PayTemporaryVO payTemporaryVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = payTemporaryService.getPayTemporaryGiftList(payTemporaryVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, payTemporaryVO);
    }

    /**
     * 가승인-상품권결제차액 상세내역 탭 - 엑셀다운로드 조회
     *
     * @param payTemporaryVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 05. 03.
     */
    @RequestMapping(value = "/payTemporaryGift/getPayTemporaryGiftExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayTemporaryGiftExcelList(PayTemporaryVO payTemporaryVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = payTemporaryService.getPayTemporaryGiftExcelList(payTemporaryVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, payTemporaryVO);
    }
}