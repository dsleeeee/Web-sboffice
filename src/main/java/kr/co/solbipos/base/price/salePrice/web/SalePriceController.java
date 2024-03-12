package kr.co.solbipos.base.price.salePrice.web;

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
import kr.co.solbipos.base.price.salePrice.service.SalePriceService;
import kr.co.solbipos.base.price.salePrice.service.SalePriceVO;
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
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : SalePriceController.java
 * @Description : 기초관리 - 가격관리 - 판매가격관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.20  김지은       최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 12.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/salePrice")
public class SalePriceController {

    private final SessionService sessionService;
    private final SalePriceService salePriceService;
    private final CmmEnvUtil cmmEnvUtil;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public SalePriceController(SessionService sessionService, SalePriceService salePriceService, CmmEnvUtil cmmEnvUtil, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.salePriceService = salePriceService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 매장 판매가 관리 화면 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author  김지은
     * @since   2018. 12. 24.
     */
    @RequestMapping(value = "/salePrice/salePriceView.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 내점/배달/포장 가격관리 사용여부
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));
            // 매장판매가관리본사강제수정
            model.addAttribute("coercionFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1113"), "0"));

        }else{
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));
            model.addAttribute("coercionFg", "0");
        }
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

        return "base/price/salePrice/salePriceView";
    }


    /***
     * 상품별 정보 조회
     * @param salePriceVO
     * @param request
     * @return
     * @author  김지은
     * @since   2018. 12. 24.
     */
    @RequestMapping(value = "/prodSalePrice/getProdInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfo(SalePriceVO salePriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = salePriceService.getProdInfo(salePriceVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /***
     * 상품별 매장 판매가 조회
     * @param salePriceVO
     * @param request
     * @return
     * @author  김지은
     * @since   2018. 12. 24.
     */
    @RequestMapping(value = "/prodSalePrice/getProdSalePriceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSalePriceList(SalePriceVO salePriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        System.out.println("값 체크_cont");
        System.out.println(salePriceVO.getProdCd());
        System.out.println(salePriceVO.getMomsAcShop());
        System.out.println(salePriceVO.getMomsCommercial());
        System.out.println(salePriceVO.getMomsTeam());
        System.out.println(salePriceVO.getMomsStoreManageType());
        System.out.println(salePriceVO.getMomsAreaFg());
        System.out.println(salePriceVO.getMomsShopType());
        System.out.println(salePriceVO.getBranchCd());
        System.out.println(salePriceVO.getUserBrandList());

        List<DefaultMap<String>> list = salePriceService.getProdSalePriceList(salePriceVO, sessionInfoVO);

        return returnListJson(Status.OK, list, salePriceVO);
    }
    /**
     * 상품별 매장 판매가 저장
     * @param   salePriceVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 12. 24.
     */
    @RequestMapping(value = "/prodSalePrice/saveProdSalePrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveProdSalePrice(@RequestBody SalePriceVO[] salePriceVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceService.saveProdSalePrice(salePriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /***
     * 매장별 정보 조회
     * @param salePriceVO
     * @param request
     * @return
     * @author  김지은
     * @since   2018. 12. 26.
     */
    @RequestMapping(value = "/storeSalePrice/getStoreSalePriceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSalePriceList(SalePriceVO salePriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceService.getStoreSalePriceList(salePriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceVO);
    }

    /***
     * 본사 정보 조회
     * @param salePriceVO
     * @param request
     * @return
     * @author  권지현
     * @since   2021. 07. 20.
     */
    @RequestMapping(value = "/hqSalePrice/getHqSalePriceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSalePriceList(SalePriceVO salePriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceService.getHqSalePriceList(salePriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceVO);
    }

    /**
     * 본사 판매가 저장
     * @param   salePriceVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2021. 07. 20.
     */
    @RequestMapping(value = "/prodSalePrice/saveHqProdSalePrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveHqProdSalePrice(@RequestBody SalePriceVO[] salePriceVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceService.saveHqProdSalePrice(salePriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 본사 판매가 관리 화면 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author  김지은
     * @since   2018. 12. 24.
     */
    @RequestMapping(value = "/salePrice/hqSalePriceView.sb", method = RequestMethod.GET)
    public String hqView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);


        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));
            // 매장판매가관리본사강제수정
            model.addAttribute("coercionFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1113"), "0"));

        }else{
            // 내점/배달/포장 가격관리 사용여부
            model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));
            model.addAttribute("coercionFg", "0");
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
        /** //맘스터치 */

        return "base/price/salePrice/hqSalePriceTab";
    }

    /**
     * 엑셀업로드 탭 - 엑셀 양식다운로드 조회
     *
     * @param salePriceVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 02. 21.
     */
    @RequestMapping(value = "/salePriceExcelUpload/getSalePriceExcelUploadSampleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceExcelUploadSampleList(SalePriceVO salePriceVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceService.getSalePriceExcelUploadSampleList(salePriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceVO);
    }

    /**
     * 검증결과 조회
     *
     * @param salePriceVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 02. 21.
     */
    @RequestMapping(value = "/salePriceExcelUpload/getSalePriceExcelUploadCheckList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceExcelUploadCheckList(SalePriceVO salePriceVO, HttpServletRequest request,
                                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceService.getSalePriceExcelUploadCheckList(salePriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceVO);
    }

    /**
     * 검증결과 전체 삭제
     *
     * @param salePriceVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 02. 21.
     */
    @RequestMapping(value = "/salePriceExcelUpload/getSalePriceExcelUploadCheckDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceExcelUploadCheckDeleteAll(@RequestBody SalePriceVO salePriceVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceService.getSalePriceExcelUploadCheckDeleteAll(salePriceVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 검증결과 삭제
     *
     * @param salePriceVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 02. 21.
     */
    @RequestMapping(value = "/salePriceExcelUpload/getSalePriceExcelUploadCheckDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceExcelUploadCheckDelete(@RequestBody SalePriceVO[] salePriceVOs, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceService.getSalePriceExcelUploadCheckDelete(salePriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 업로드시 임시테이블 저장
     *
     * @param salePriceVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 02. 21.
     */
    @RequestMapping(value = "/salePriceExcelUpload/getSalePriceExcelUploadCheckSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceExcelUploadCheckSave(@RequestBody SalePriceVO[] salePriceVOs, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceService.getSalePriceExcelUploadCheckSave(salePriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 검증결과 저장
     *
     * @param salePriceVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 02. 21.
     */
    @RequestMapping(value = "/salePriceExcelUpload/getSalePriceExcelUploadCheckSaveAdd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSalePriceExcelUploadCheckSaveAdd(@RequestBody SalePriceVO[] salePriceVOs, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceService.getSalePriceExcelUploadCheckSaveAdd(salePriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 본사판매가관리 엑셀업로드 탭 - 판매가 저장
     *
     * @param salePriceVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 02. 21.
     */
    @RequestMapping(value = "/salePriceExcelUpload/getHqSalePriceExcelUploadSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSalePriceExcelUploadSave(@RequestBody SalePriceVO[] salePriceVOs, HttpServletRequest request,
                                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceService.getHqSalePriceExcelUploadSave(salePriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장판매가관리 엑셀업로드 탭 - 판매가 저장
     *
     * @param salePriceVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 02. 21.
     */
    @RequestMapping(value = "/salePriceExcelUpload/getStoreSalePriceExcelUploadSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSalePriceExcelUploadSave(@RequestBody SalePriceVO[] salePriceVOs, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = salePriceService.getStoreSalePriceExcelUploadSave(salePriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 상품별 판매가관리 탭 - 엑셀조회
     * @param   request
     * @param   response
     * @param   model
     * @param   salePriceVO
     * @return  Object
     * @author  김유승
     * @since   2023.12.27
     */
    @RequestMapping(value = "/salePrice/getProdSaleExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleExcelList(HttpServletRequest request, HttpServletResponse response, Model model, SalePriceVO salePriceVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = salePriceService.getProdSaleExcelList(salePriceVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, salePriceVO);
    }

    /**
     * 매장별 판매가관리 탭 - 엑셀조회
     * @param   request
     * @param   response
     * @param   model
     * @param   salePriceVO
     * @return  String
     * @author  김유승
     * @since   2023.12.27
     */
    @RequestMapping(value = "/salePrice/getStoreSaleExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSaleExcelList(HttpServletRequest request, HttpServletResponse response, Model model, SalePriceVO salePriceVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceService.getStoreSaleExcelList(salePriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceVO);
    }

    /***
     * 본사 판매가관리
     * @param   request
     * @param   response
     * @param   model
     * @param   salePriceVO
     * @return  String
     * @author  김유승
     * @since   2023. 12. 29.
     */
    @RequestMapping(value = "/hqSalePrice/getHqSaleExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSaleExcelList(SalePriceVO salePriceVO, HttpServletResponse response, Model model, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceService.getHqSaleExcelList(salePriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceVO);
    }

}
