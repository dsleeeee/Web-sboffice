package kr.co.solbipos.base.prod.setProd.web;

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
import kr.co.solbipos.base.prod.setProd.service.SetProdService;
import kr.co.solbipos.base.prod.setProd.service.SetProdVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuAttrCdVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuAttrClassVO;
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
 * @Class Name : SetProdController.java
 * @Description : 기초관리 > 상품관리 > 세트메뉴구성(BBQ전용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.04  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.09.04
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/setProd")
public class SetProdController {

    private final SetProdService setProdService;
    private final StoreTypeService storeTypeService;
    private final DayProdService dayProdService;
    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public SetProdController(SetProdService setProdService, StoreTypeService storeTypeService, DayProdService dayProdService, SessionService sessionService, CmmEnvUtil cmmEnvUtil, CmmCodeUtil cmmCodeUtil) {
        this.setProdService = setProdService;
        this.storeTypeService = storeTypeService;
        this.dayProdService = dayProdService;
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return String
     * @author 권지현
     * @since 2023.09.04
     */
    @RequestMapping(value = "/setProd/view.sb", method = RequestMethod.GET)
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
        /** //맘스터치 */


        return "base/prod/setProd/setProd";
    }

    /**
     * 상품 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param setProdVO SetProdVO
     * @param model
     * @return Result
     * @author 권지현
     * @since 2023.09.05
     */
    @RequestMapping(value = "/setProd/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(HttpServletRequest request, HttpServletResponse response,
                              SetProdVO setProdVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 속성분류 목록 조회
        List<DefaultMap<String>> list = setProdService.getProdList(setProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, setProdVO);
    }

    /**
     * 상품 - 사이드상품여부 저장
    *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param setProdVOs SetProdVO[]
     * @param model Model
     * @return Result
     * @author 권지현
     * @since 2023.09.05
     */
    @RequestMapping(value = "/setProd/saveSideProdYn.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSideProdYn(@RequestBody SetProdVO[] setProdVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = setProdService.saveSideProdYn(setProdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 상품 선택그룹 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param setProdVO SetProdVO
     * @param model
     * @return Result
     * @author 권지현
     * @since 2023.09.05
     */
    @RequestMapping(value = "/setProd/getSdselGrpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSdselGrpList(HttpServletRequest request, HttpServletResponse response,
                              SetProdVO setProdVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = setProdService.getSdselGrpList(setProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, setProdVO);
    }
}
