package kr.co.solbipos.base.prod.storeSideMenu.web;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskDisplay.service.KioskDisplayService;
import kr.co.solbipos.base.prod.kioskDisplay.service.KioskDisplayVO;
import kr.co.solbipos.base.prod.prod.service.ProdService;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.ProdAuthEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuManageVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuService;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyClassVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyService;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyStyleVO;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
* @Class Name : storeSideMenuController.java
* @Description : 기초관리 > 상품관리 > 매장구성세트상품
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2021.05.26  권지현      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 권지현
* @since 2021. 05.26
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/base/prod/storeSideMenu")
public class StoreSideMenuController {

    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;
    private final TouchKeyService touchkeyService;
    private final ProdService prodService;
    private final SideMenuService sideMenuService;
    private final DayProdService dayProdService;
    private final KioskDisplayService kioskDisplayService;
    private final CmmCodeUtil cmmCodeUtil;

    public StoreSideMenuController(SessionService sessionService, CmmEnvUtil cmmEnvUtil, TouchKeyService touchkeyService, ProdService prodService, SideMenuService sideMenuService, DayProdService dayProdService, KioskDisplayService kioskDisplayService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.touchkeyService = touchkeyService;
        this.prodService = prodService;
        this.sideMenuService = sideMenuService;
        this.dayProdService = dayProdService;
        this.kioskDisplayService = kioskDisplayService;
        this.cmmCodeUtil = cmmCodeUtil;
    }


    /**
     * 매장구성세트상품 화면 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response,
            Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        /**  상품정보관리  */
        // 상품생성설정
        ProdAuthEnvFg prodAuthEnvstVal = ProdAuthEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0042"));

        // 상품코드 채번방식
        ProdNoEnvFg prodNoEnvFg;
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0028"));
        }else{
            prodNoEnvFg = ProdNoEnvFg.getEnum(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0028"));
        }

        model.addAttribute("prodAuthEnvstVal", prodAuthEnvstVal);
        model.addAttribute("prodNoEnvFg", prodNoEnvFg);

        // 내점/배달/포장 가격관리 사용여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));
        }else{
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));
        }

        // 본사
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if(CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0043"),"0").equals("0")){ // 0043 본사신규상품 매장생성기준
                model.addAttribute("kitchenprintLink", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1110") , "0")); // 1110상품생성시주방프린터연결여부
            }
        } else {
            model.addAttribute("kitchenprintLink", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1110") , "0")); // 1110상품생성시주방프린터연결여부
        }

        // (상품관리)브랜드사용여부
//        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
//        }else{
//            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1114") , "0"));
//        }

        // 매장상품제한구분 사용여부(매장 세트구성상품 등록시 사용, 매장에서 사용하지만 본사환경설정값으로 여부파악)
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) { // 본사는 해당옵션의 제약X
            model.addAttribute("storeProdUseFg", "0");
        } else {
            model.addAttribute("storeProdUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1100"), "0"));
        }


        // 브랜드 리스트 조회(선택 콤보박스용)
        ProdVO prodVO = new ProdVO();
        model.addAttribute("brandList", convertToJson(prodService.getBrandList(prodVO, sessionInfoVO)));

        // 매장별 브랜드 콤보박스 조회(사용자 상관없이 전체 브랜드 표시)
        KioskDisplayVO kioskDisplayVO = new KioskDisplayVO();
        model.addAttribute("userHqStoreBrandCdComboList", convertToJson(kioskDisplayService.getUserBrandComboListAll(kioskDisplayVO, sessionInfoVO)));

        // 사용자별 브랜드 콤보박스 조회(조회용)
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        // 코너 리스트 조회(선택 콤보박스용)
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            List cornerList = prodService.getCornerList(prodVO, sessionInfoVO);
            model.addAttribute("cornerList", cornerList.isEmpty() ? CmmUtil.comboListAll2("기본코너","00") : cmmCodeUtil.assmblObj(cornerList, "name", "value", UseYn.N));
        }else {
            model.addAttribute("cornerList", CmmUtil.comboListAll2("기본코너","00"));
        }

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
            System.out.println("momsEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
            System.out.println("momsEnvstVal : " + CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
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

        /** 판매터치키등록  */
        TouchKeyVO params = new TouchKeyVO();
        params.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        params.setStoreCd(sessionInfoVO.getStoreCd());
        params.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        TouchKeyClassVO touchKeyClassVO = new TouchKeyClassVO();
        touchKeyClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        touchKeyClassVO.setStoreCd(sessionInfoVO.getStoreCd());
        touchKeyClassVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        touchKeyClassVO.setPageNo(1);

        TouchKeyStyleVO touchKeyStyleVO = new TouchKeyStyleVO();
        touchKeyStyleVO.setStyleCd("");

        // 본사or매장의 터치키 환경 설정 값을 조회해서 셋팅
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("maxClassRow", cmmEnvUtil.getHqEnvst(sessionInfoVO, "0041"));
        } else {
            model.addAttribute("maxClassRow", cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1041"));
        }

        // 터치키 관련 권한정보 가져오기 : 2019-08-08 이다솜
        String envstCd = "0017";
        String touchKeyEnvstVal = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, envstCd));
        model.addAttribute("touchKeyEnvstVal", touchKeyEnvstVal);

        // 터치키 그룹 가져오기
        List<DefaultMap<String>> touchKeyGrpList = touchkeyService.getTouchKeyGrp(params, sessionInfoVO);
        model.addAttribute("touchKeyGrp", convertToJson(touchKeyGrpList));
        
        // 터치메뉴 폰트크기 환경설정값 조회
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("fontSizeEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1236"), "15"));
        } else {
            model.addAttribute("fontSizeEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1236"), "15"));
        }

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

        // 사용자별 브랜드 조회(콤보박스용)
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        return "base/prod/storeSideMenu/storeSideMenu";
    }

}
