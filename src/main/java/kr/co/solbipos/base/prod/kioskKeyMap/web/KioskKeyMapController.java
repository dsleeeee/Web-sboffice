package kr.co.solbipos.base.prod.kioskKeyMap.web;

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
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapService;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import kr.co.solbipos.pos.confg.vermanage.service.VerInfoVO;
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
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : KioskKeyMapController.java
 * @Description : 기초관리 - 상품관리 - 키오스크키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.03  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 09.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/kioskKeyMap")
public class KioskKeyMapController {

    private final SessionService sessionService;
    private final KioskKeyMapService kioskKeyMapService;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public KioskKeyMapController(SessionService sessionService, KioskKeyMapService kioskKeyMapService, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.kioskKeyMapService = kioskKeyMapService;
        this.dayProdService = dayProdService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2020. 09. 04.
     */
    @RequestMapping(value = "/kioskKeyMap/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // KIOSK-매장수정여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("kioskKeyEnvstVal", "1");
        } else {
            model.addAttribute("kioskKeyEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1249"), "1"));
        }

        // 키오스크용 포스 조회
        List<DefaultMap<String>> kioskPosList = kioskKeyMapService.getKioskPosList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskPosList", convertToJson(kioskPosList));

        // 키오스크 키맵그룹 조회
        List<DefaultMap<String>> kioskTuClsTypeList = kioskKeyMapService.getKioskTuClsTypeList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskTuClsTypeList", convertToJson(kioskTuClsTypeList)  );

        // 키오스크 키맵그룹 사용여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("kioskKeyMapGrpFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1104"), "0"));
        }else{
            model.addAttribute("kioskKeyMapGrpFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1104") , "0"));
        }

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
            model.addAttribute("posLoginReconnect", request.getParameter("posLoginReconnect"));
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


        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));

            // 매장판매가관리본사강제수정
            model.addAttribute("coercionFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1113"), "0"));
        }else{
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));

            // 본사통제구분-판매가
            model.addAttribute("salePriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0045") , "1"));
            model.addAttribute("coercionFg", "0");
        }
        model.addAttribute("pageFg","0");

        return "base/prod/kioskKeyMap/kioskKeyMap";
    }


    /**
     * 페이지 이동 - 키오스크키맵관리(매장) 페이지
     *
     * @param   request
     * @param   response
     * @param   model
     * @author  김유승
     * @since   2025. 01. 10.
     */
    @RequestMapping(value = "/kioskKeyMapStore/view.sb", method = RequestMethod.GET)
    public String view2(HttpServletRequest request, HttpServletResponse response, Model model) {

        KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // KIOSK-매장수정여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("kioskKeyEnvstVal", "1");
        } else {
            model.addAttribute("kioskKeyEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1249"), "1"));
        }

        // 키오스크용 포스 조회
        List<DefaultMap<String>> kioskPosList = kioskKeyMapService.getKioskPosList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskPosList", convertToJson(kioskPosList));

        // 키오스크 키맵그룹 조회
        List<DefaultMap<String>> kioskTuClsTypeList = kioskKeyMapService.getKioskTuClsTypeList(kioskKeyMapVO, sessionInfoVO);
        model.addAttribute("kioskTuClsTypeList", convertToJson(kioskTuClsTypeList)  );

        // 키오스크 키맵그룹 사용여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("kioskKeyMapGrpFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1104"), "0"));
        }else{
            model.addAttribute("kioskKeyMapGrpFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1104") , "0"));
        }

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
            model.addAttribute("posLoginReconnect", request.getParameter("posLoginReconnect"));
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


        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));

            // 매장판매가관리본사강제수정
            model.addAttribute("coercionFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1113"), "0"));
        }else{
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));

            // 본사통제구분-판매가
            model.addAttribute("salePriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0045") , "1"));
            model.addAttribute("coercionFg", "0");
        }

        model.addAttribute("pageFg","1");

        return "base/prod/kioskKeyMap/kioskKeyMapStore";
    }

    /**
     * 키오스크 카테고리(분류) 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2020. 09. 04.
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskCategory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskCategory(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getKioskCategory(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 키오스크 카테고리(분류) 저장
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2020. 09. 04.
     */
    @RequestMapping(value = "/kioskKeyMap/saveKioskCategory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveKioskCategory(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveKioskCategory(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 키맵 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2020. 09. 07.
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskKeyMap.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskKeyMap(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getKioskKeyMap(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 키오스크 키맵 수정
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2020. 09. 10.
     */
    @RequestMapping(value = "/kioskKeyMap/updateKioskKeyMap.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updateKioskKeyMap(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.updateKioskKeyMap(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 상품 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @author  이다솜
     * @since   2020. 09. 07.
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskProdList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskKeyMapService.getKioskProdList(kioskKeyMapVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskKeyMapVO);
    }

    /**
     * 키오스크 키맵 등록
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2020. 09. 07.
     */
    @RequestMapping(value = "/kioskKeyMap/saveKioskKeyMap.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveKioskKeyMap(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveKioskKeyMap(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 키맵그룹 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 06. 04.
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskTuClsTypeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskTuClsTypeList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kioskKeyMapService.getKioskTuClsTypeList(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskKeyMapVO);
    }

    /**
     * 키오스크 키맵그룹 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.10.21
     */
    @RequestMapping(value = "/kioskKeyMap/getStoreModGrpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreModGrpList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kioskKeyMapService.getStoreModGrpList(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskKeyMapVO);
    }

    /**
     * 매장수정허용분류_저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param kioskKeyMapVOs KioskKeyMapVO
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2022.10.21
     */
    @RequestMapping(value = "/kioskKeyMap/saveStoreModGrp.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreModGrp(HttpServletRequest request, HttpServletResponse response,
                                  @RequestBody KioskKeyMapVO[] kioskKeyMapVOs, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveStoreModGrp(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * 키오스크 터치키그룹 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.11.08
     */
    @RequestMapping(value = "/kioskKeyMap/getClsTypeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getClsTypeList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kioskKeyMapService.getClsTypeList(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskKeyMapVO);
    }

    /**
     * 키오스크 터치키그룹 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param kioskKeyMapVOs KioskKeyMapVO
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2022.11.08
     */
    @RequestMapping(value = "/kioskKeyMap/saveClsType.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveClsType(HttpServletRequest request, HttpServletResponse response,
                                  @RequestBody KioskKeyMapVO[] kioskKeyMapVOs, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveClsType(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * 키오스크 키맵 신규그룹추가
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 06. 04.
     */
    @RequestMapping(value = "/kioskKeyMap/createKioskTuClsType.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result createKioskTuClsType(@RequestBody KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.createKioskTuClsType(kioskKeyMapVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 키맵 그룹복제(신규생성)
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 06. 07.
     */
    @RequestMapping(value = "/kioskKeyMap/copyKioskTuClsType.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyKioskTuClsType(@RequestBody KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.copyKioskTuClsType(kioskKeyMapVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 키맵 그룹복제(delete insert)
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2021.12.15
     */
    @RequestMapping(value = "/kioskKeyMap/copyStoreKioskTuClsType.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result copyStoreKioskTuClsType(@RequestBody KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.copyStoreKioskTuClsType(kioskKeyMapVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 키맵매장적용 - 매장리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.06.08
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskKeyMapService.getStoreList(kioskKeyMapVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskKeyMapVO);
    }

    /**
     * 키오스크 키맵매장적용
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 06. 08.
     */
    @RequestMapping(value = "/kioskKeyMap/saveKioskKeyMapStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveKioskKeyMapStore(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveKioskKeyMapStore(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 매장적용(매장/포장) - 매장 키오스크 포스 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.06.09
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getStoreKioskPosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreKioskPosList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskKeyMapService.getStoreKioskPosList(kioskKeyMapVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskKeyMapVO);
    }

    /**
     * 키오스크 매장적용(매장/포장) - 본사/매장 환경설정값 저장
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2021. 06. 09.
     */
    @RequestMapping(value = "/kioskKeyMap/saveHqStoreKioskPosEnv.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqStoreKioskPosEnv(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveHqStoreKioskPosEnv(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 매장적용(매장/포장) - 키오스크 환경설정 값 가져오기
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.06.09
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskEnv.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskEnv(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String sEnvstVal = kioskKeyMapService.getKioskEnv(kioskKeyMapVO, sessionInfoVO);

        return returnJson(Status.OK, sEnvstVal);
    }


    /**
     * 키오스크 추천메뉴 - 추천메뉴코드 가져오기
     *
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.08
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getRecmd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecmd(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getRecmd(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 키오스크 추천메뉴 - 추천메뉴코드 저장
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.09
     */
    @RequestMapping(value = "/kioskKeyMap/saveRecmd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRecmd(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveRecmd(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 추천메뉴 - 추천상품 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.09
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getRecmdProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecmdProd(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getRecmdProd(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 키오스크 추천메뉴 - 추천상품으로 등록할 상품
     *
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.09
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getRecmdProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecmdProdList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getRecmdProdList(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskKeyMapVO);
    }

    /**
     * 키오스크 추천메뉴 - 추천메뉴 저장(하위 왼쪽그리드)
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.09
     */
    @RequestMapping(value = "/kioskKeyMap/saveRecmdProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRecmdProd(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//        KioskKeyMapVO kioskKeyMapVO = new KioskKeyMapVO();
//        kioskKeyMapVO.setRecmdCd(kioskKeyMapVOs[0].getRecmdCd());
//        if(kioskKeyMapVOs[0].getPageFg() != null && kioskKeyMapVOs[0].getPageFg().equals("1")) {
//            kioskKeyMapVO.setPageFg("1");
//            kioskKeyMapVO.setStoreCd(kioskKeyMapVOs[0].getStoreCd());
//        }

        int result = kioskKeyMapService.deleteRecmdProd(kioskKeyMapVOs, sessionInfoVO);

//        int result = kioskKeyMapService.saveRecmdProd(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 추천메뉴 - 상품 > 추천메뉴 저장(하위 오른쪽 그리드)
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author 권지현
     * @since 2021.12.09
     */
    @RequestMapping(value = "/kioskKeyMap/addRecmdProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result addRecmdProd(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.addRecmdProd(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 추천메뉴 매장적용
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2022. 01. 18.
     */
    @RequestMapping(value = "/kioskKeyMap/saveRecmdStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRecmdStore(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveRecmdStore(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 카테고리(중분류) 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  김설아
     * @since   2022. 08. 17.
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskCategoryM.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskCategoryM(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getKioskCategoryM(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 키오스크 카테고리(중분류) 저장
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author  김설아
     * @since   2022. 08. 17.
     */
    @RequestMapping(value = "/kioskKeyMap/saveKioskCategoryM.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveKioskCategoryM(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveKioskCategoryM(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키맵그룹에 KIOSK중분류사용 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 08. 17.
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskKeyMapGroupTuMClsFg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskKeyMapGroupTuMClsFg(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getKioskKeyMapGroupTuMClsFg(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskKeyMapVO);
    }

    /**
     * 키오스크키맵 매장복사 - 매장 키오스크 포스 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2023.03.24
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/getStoreCopyKioskPosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreCopyKioskPosList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = kioskKeyMapService.getStoreCopyKioskPosList(kioskKeyMapVO, sessionInfoVO);

        return returnListJson(Status.OK, list, kioskKeyMapVO);
    }

    /**
     * 키오스크키맵 매장복사 - 기준매장 키맵그룹 및 키맵정보를 적용매장에 복사
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2023.03.24
     */
    @RequestMapping(value = "/kioskKeyMap/saveKioskKeyMapStoreCopy.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveKioskKeyMapStoreCopy(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.saveKioskKeyMapStoreCopy(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크 키맵 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2023.04.27
     */
    @RequestMapping(value = "/kioskKeyMap/getTuKeyList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTuKeyList(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getTuKeyList(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 본사판매가관리 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2023.08.30
     */
    @RequestMapping(value = "/kioskKeyMap/getHqSalePrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSalePrice(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getHqSalePrice(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskKeyMapVO);
    }

    /**
     * 매장판매가관리 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2023.08.30
     */
    @RequestMapping(value = "/kioskKeyMap/getStoreSalePrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSalePrice(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getStoreSalePrice(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskKeyMapVO);
    }

    /**
     * 판매가관리 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2023.08.30
     */
    @RequestMapping(value = "/kioskKeyMap/getSalePrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePrice(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = kioskKeyMapService.getSalePrice(kioskKeyMapVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, kioskKeyMapVO);
    }

    /**
     * 키오스크키맵 팝업 - 삭제
     *
     * @param kioskKeyMapVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2024. 06. 05.
     */
    @RequestMapping(value = "/kioskKeyMap/getKioskKeyDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKioskKeyDelete(@RequestBody KioskKeyMapVO[] kioskKeyMapVOs, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = kioskKeyMapService.getKioskKeyDelete(kioskKeyMapVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 키오스크키맵관리(매장) - 조회
     *
     * @param kioskKeyMapVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/kioskKeyMap/kioskKeyMapSelectStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result kioskKeyMapSelectStore(KioskKeyMapVO kioskKeyMapVO, HttpServletRequest request,
                          HttpServletResponse response, Model model) {

        Map<String, Object> result = new DefaultMap<>();

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        sessionInfoVO.setStoreCd(kioskKeyMapVO.getStoreCd());
        sessionInfoVO.setOrgnFg(OrgnFg.STORE);

        // KIOSK-매장수정여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result.put("kioskKeyEnvstVal", "1");
        } else {
            result.put("kioskKeyEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1249"), "1"));
        }

        // 키오스크용 포스 조회
        List<DefaultMap<String>> kioskPosList = kioskKeyMapService.getKioskPosList(kioskKeyMapVO, sessionInfoVO);
        result.put("kioskPosList", kioskPosList);


        // 키오스크 키맵그룹 조회
//        List<DefaultMap<String>> kioskTuClsTypeList = kioskKeyMapService.getKioskTuClsTypeList(kioskKeyMapVO, sessionInfoVO);
//        result.put("kioskTuClsTypeList", kioskTuClsTypeList);

        // 키오스크 키맵그룹 사용여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result.put("kioskKeyMapGrpFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1104"), "0"));
        }else{
            result.put("kioskKeyMapGrpFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1104") , "0"));
        }

        // 브랜드사용여부
        result.put("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        result.put("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
            result.put("posLoginReconnect", request.getParameter("posLoginReconnect"));
        }

        /** 맘스터치 */
        // [1250 맘스터치] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            result.put("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            result.put("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
        }

//        // 사용자별 코드별 공통코드 콤보박스 조회
//        // 팀별
//        List momsTeamComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "151");
//        String momsTeamComboListAll = "";
//        if (momsTeamComboList.isEmpty()) {
//            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m = new HashMap<>();
//            m.put("name", "전체");
//            m.put("value", "");
//            list.add(m);
//            momsTeamComboListAll = convertToJson(list);
//        } else {
//            momsTeamComboListAll = cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.N);
//        }
//        result.put("momsTeamComboList", momsTeamComboListAll);
//        // AC점포별
//        List momsAcShopComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "152");
//        String momsAcShopComboListAll = "";
//        if (momsAcShopComboList.isEmpty()) {
//            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m = new HashMap<>();
//            m.put("name", "전체");
//            m.put("value", "");
//            list.add(m);
//            momsAcShopComboListAll = convertToJson(list);
//        } else {
//            momsAcShopComboListAll = cmmCodeUtil.assmblObj(momsAcShopComboList, "name", "value", UseYn.N);
//        }
//        result.put("momsAcShopComboList", momsAcShopComboListAll);
//        // 지역구분
//        List momsAreaFgComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "153");
//        String momsAreaFgComboListAll = "";
//        if (momsAreaFgComboList.isEmpty()) {
//            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m = new HashMap<>();
//            m.put("name", "전체");
//            m.put("value", "");
//            list.add(m);
//            momsAreaFgComboListAll = convertToJson(list);
//        } else {
//            momsAreaFgComboListAll = cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.N);
//        }
//        result.put("momsAreaFgComboList", momsAreaFgComboListAll);
//        // 상권
//        List momsCommercialComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "154");
//        String momsCommercialComboListAll = "";
//        if (momsCommercialComboList.isEmpty()) {
//            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m = new HashMap<>();
//            m.put("name", "전체");
//            m.put("value", "");
//            list.add(m);
//            momsCommercialComboListAll = convertToJson(list);
//        } else {
//            momsCommercialComboListAll = cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.N);
//        }
//        result.put("momsCommercialComboList", momsCommercialComboListAll);
//        // 점포유형
//        List momsShopTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "155");
//        String momsShopTypeComboListAll = "";
//        if (momsShopTypeComboList.isEmpty()) {
//            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m = new HashMap<>();
//            m.put("name", "전체");
//            m.put("value", "");
//            list.add(m);
//            momsShopTypeComboListAll = convertToJson(list);
//        } else {
//            momsShopTypeComboListAll = cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.N);
//        }
//        result.put("momsShopTypeComboList", momsShopTypeComboListAll);
//        // 매장관리타입
//        List momsStoreManageTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "156");
//        String momsStoreManageTypeComboListAll = "";
//        if (momsStoreManageTypeComboList.isEmpty()) {
//            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m = new HashMap<>();
//            m.put("name", "전체");
//            m.put("value", "");
//            list.add(m);
//            momsStoreManageTypeComboListAll = convertToJson(list);
//        } else {
//            momsStoreManageTypeComboListAll = cmmCodeUtil.assmblObj(momsStoreManageTypeComboList, "name", "value", UseYn.N);
//        }
//        result.put("momsStoreManageTypeComboList", momsStoreManageTypeComboListAll);
//
//        // 사용자별 그룹 콤보박스 조회
//        // 그룹
//        List branchCdComboList = dayProdService.getUserBranchComboList(sessionInfoVO);
//        String branchCdComboListAll = "";
//        if (branchCdComboList.isEmpty()) {
//            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m = new HashMap<>();
//            m.put("name", "전체");
//            m.put("value", "");
//            list.add(m);
//            branchCdComboListAll = convertToJson(list);
//        } else {
//            branchCdComboListAll = cmmCodeUtil.assmblObj(branchCdComboList, "name", "value", UseYn.N);
//        }
//        result.put("branchCdComboList", branchCdComboListAll);
//        // 매장그룹
//        List momsStoreFg01ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "167");
//        String momsStoreFg01ComboListAll = "";
//        if (momsStoreFg01ComboList.isEmpty()) {
//            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m = new HashMap<>();
//            m.put("name", "전체");
//            m.put("value", "");
//            list.add(m);
//            momsStoreFg01ComboListAll = convertToJson(list);
//        } else {
//            momsStoreFg01ComboListAll = cmmCodeUtil.assmblObj(momsStoreFg01ComboList, "name", "value", UseYn.N);
//        }
//        result.put("momsStoreFg01ComboList", momsStoreFg01ComboListAll);
//        // 매장그룹2
//        List momsStoreFg02ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "169");
//        String momsStoreFg02ComboListAll = "";
//        if (momsStoreFg02ComboList.isEmpty()) {
//            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m = new HashMap<>();
//            m.put("name", "전체");
//            m.put("value", "");
//            list.add(m);
//            momsStoreFg02ComboListAll = convertToJson(list);
//        } else {
//            momsStoreFg02ComboListAll = cmmCodeUtil.assmblObj(momsStoreFg02ComboList, "name", "value", UseYn.N);
//        }
//        result.put("momsStoreFg02ComboList", momsStoreFg02ComboListAll);
//        // 매장그룹3
//        List momsStoreFg03ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "170");
//        String momsStoreFg03ComboListAll = "";
//        if (momsStoreFg03ComboList.isEmpty()) {
//            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m = new HashMap<>();
//            m.put("name", "전체");
//            m.put("value", "");
//            list.add(m);
//            momsStoreFg03ComboListAll = convertToJson(list);
//        } else {
//            momsStoreFg03ComboListAll = cmmCodeUtil.assmblObj(momsStoreFg03ComboList, "name", "value", UseYn.N);
//        }
//        result.put("momsStoreFg03ComboList", momsStoreFg03ComboListAll);
//        // 매장그룹4
//        List momsStoreFg04ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "171");
//        String momsStoreFg04ComboListAll = "";
//        if (momsStoreFg04ComboList.isEmpty()) {
//            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m = new HashMap<>();
//            m.put("name", "전체");
//            m.put("value", "");
//            list.add(m);
//            momsStoreFg04ComboListAll = convertToJson(list);
//        } else {
//            momsStoreFg04ComboListAll = cmmCodeUtil.assmblObj(momsStoreFg04ComboList, "name", "value", UseYn.N);
//        }
//        result.put("momsStoreFg04ComboList", momsStoreFg04ComboListAll);
//        // 매장그룹5
//        List momsStoreFg05ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "172");
//        String momsStoreFg05ComboListAll = "";
//        if (momsStoreFg05ComboList.isEmpty()) {
//            List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> m = new HashMap<>();
//            m.put("name", "전체");
//            m.put("value", "");
//            list.add(m);
//            momsStoreFg05ComboListAll = convertToJson(list);
//        } else {
//            momsStoreFg05ComboListAll = cmmCodeUtil.assmblObj(momsStoreFg05ComboList, "name", "value", UseYn.N);
//        }
//        result.put("momsStoreFg05ComboList", momsStoreFg05ComboListAll);
        /** //맘스터치 */


        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 내점/배달/포장 가격관리 사용여부
            result.put("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));

            // 매장판매가관리본사강제수정
            result.put("coercionFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1113"), "0"));
        }else{
            // 내점/배달/포장 가격관리 사용여부
            result.put("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));

            // 본사통제구분-판매가
            result.put("salePriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0045") , "1"));
            result.put("coercionFg", "0");
        }

        return returnJson(Status.OK, result);
    }
}
