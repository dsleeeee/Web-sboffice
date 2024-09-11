package kr.co.solbipos.sale.month.monthPos2.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.month.monthPos2.service.MonthPos2Service;
import kr.co.solbipos.sale.month.monthPos2.service.MonthPos2VO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelService;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
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
 * @Class Name : MonthPos2Controller.java
 * @Description : 맘스터치 > 매출분석 > 월별매출현황(포스별-영수건수)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.07.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/month/monthPos2")
public class MonthPos2Controller {

    private final SessionService sessionService;
    private final MonthPos2Service monthPos2Service;
    private final DayService dayService;
    private final StoreChannelService storeChannelService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public MonthPos2Controller(SessionService sessionService, MonthPos2Service monthPos2Service, DayService dayService, StoreChannelService storeChannelService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.monthPos2Service = monthPos2Service;
        this.dayService = dayService;
        this.storeChannelService = storeChannelService;
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
    @RequestMapping(value = "/monthPos2/list.sb", method = RequestMethod.GET)
    public String monthPos2View(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        StoreChannelVO storeChannelVO = new StoreChannelVO();
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


        // 결제수단 조회(현금영수증 포함)
        List<DefaultMap<String>> payColList = dayService.getPayColAddList(dayVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");

            // 맘스터치 결제수단 '식권' -> '식권대장' 으로 변경표기 (20240909)
            if(sessionInfoVO.getHqOfficeCd().equals("DS021") || sessionInfoVO.getHqOfficeCd().equals("DS034") || sessionInfoVO.getHqOfficeCd().equals("H0393")){
                if(payColList.get(i).getStr("payCd").equals("14")){
                    payColList.get(i).put("payNm", "식권대장");
                }
            }
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        // 할인구분 조회
        List<DefaultMap<String>> dcColList = dayService.getDcColList(dayVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String dcCol = "";
        for(int i=0; i < dcColList.size(); i++) {
            dcCol += (dcCol.equals("") ? "" : ",") + dcColList.get(i).getStr("dcCd");
        }
        model.addAttribute("dcColList", dcColList);
        model.addAttribute("dcCol", dcCol);

        // 주문채널 구분자 조회
        List<DefaultMap<String>> dlvrInFgColList = storeChannelService.getDlvrInFgColList(storeChannelVO, sessionInfoVO);

        // 주문채널 코드를 , 로 연결하는 문자열 생성
        String dlvrInFgCol = "";
        for(int i=0; i < dlvrInFgColList.size(); i++) {
            dlvrInFgCol += (dlvrInFgCol.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFg");
        }
        model.addAttribute("dlvrInFgColList", dlvrInFgColList);
        model.addAttribute("dlvrInFgCol", dlvrInFgCol);

        // 모바일페이상세 조회(현금영수증 포함)
        List<DefaultMap<String>> mpayColList = dayService.getMpayColList(dayVO, sessionInfoVO);

        // 모바일페이상세 코드를 , 로 연결하는 문자열 생성
        String mpayCol = "";
        for(int i=0; i < mpayColList.size(); i++) {
            mpayCol += (mpayCol.equals("") ? "" : ",") + mpayColList.get(i).getStr("mpayCd");
        }
        model.addAttribute("mpayColList", mpayColList);
        model.addAttribute("mpayCol", mpayCol);

        return "sale/month/monthPos2/monthPos2";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   monthPos2VO
     * @return  String
     * @author  권지현
     * @since   2023.07.04
     */
    @RequestMapping(value = "/monthPos2/getMonthPos2List.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthPos2List(HttpServletRequest request, HttpServletResponse response, Model model, MonthPos2VO monthPos2VO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthPos2Service.getMonthPos2List(monthPos2VO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthPos2VO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   monthPos2VO
     * @return  String
     * @author  권지현
     * @since   2023.07.04
     */
    @RequestMapping(value = "/monthPos2/getMonthPos2ExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthPos2ExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MonthPos2VO monthPos2VO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthPos2Service.getMonthPos2ExcelList(monthPos2VO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthPos2VO);
    }

}