package kr.co.solbipos.base.prod.recpOrigin.web;

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
import kr.co.solbipos.base.prod.recpOrigin.service.RecpOriginService;
import kr.co.solbipos.base.prod.recpOrigin.service.RecpOriginVO;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.apache.ibatis.annotations.Select;
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
 * @Class Name : RecpOriginController.java
 * @Description : 기초관리 > 상품관리 > 원산지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2020.07.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/recpOrigin")
public class RecpOriginController {

    private final SessionService sessionService;
    private final RecpOriginService recpOriginService;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public RecpOriginController(SessionService sessionService, RecpOriginService recpOriginService, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.recpOriginService = recpOriginService;
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
     */
    @RequestMapping(value = "/recpOrigin/list.sb", method = RequestMethod.GET)
    public String recpOriginView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

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

        return "base/prod/recpOrigin/recpOriginTab";
    }

    /**
     * 원산지관리 조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 10.
     */
    @RequestMapping(value = "/recpOrigin/getRecpOriginList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpOriginList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getRecpOriginList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 브랜드 콤보박스 리스트 조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021. 03. 18.
     */
    @RequestMapping(value = "/recpOrigin/getBrandComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBrandComboList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        recpOriginVO.setHqOfficeCd(request.getParameter("hqOfficeCd"));

        List<DefaultMap<Object>> result = recpOriginService.getBrandComboList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 원산지관리 저장
     *
     * @param recpOriginVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 10.
     */
    @RequestMapping(value = "/recpOrigin/getRecpOriginSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpOriginSave(@RequestBody RecpOriginVO[] recpOriginVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = recpOriginService.getRecpOriginSave(recpOriginVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 재료-상품 조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 10.
     */
    @RequestMapping(value = "/recpOrigin/getRecpOriginDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpOriginDetailList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getRecpOriginDetailList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 재료-상품 등록 팝업 - 상품조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 13.
     */
    @RequestMapping(value = "/recpProd/getRecpProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpProdList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getRecpProdList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 재료-상품 등록 팝업 - 재료-상품 저장
     *
     * @param recpOriginVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 13.
     */
    @RequestMapping(value = "/recpOrigin/getRecpOriginDetailSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpOriginDetailSave(@RequestBody RecpOriginVO[] recpOriginVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = recpOriginService.getRecpOriginDetailSave(recpOriginVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 상품-원산지관리탭 - 조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 25.
     */
    @RequestMapping(value = "/prodRecpOrigin/getProdRecpOriginList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRecpOriginList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getProdRecpOriginList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 상품-원산지관리탭 - 재료 및 원산지 등록 조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 25.
     */
    @RequestMapping(value = "/prodRecpOrigin/getProdRecpOriginDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRecpOriginDetailList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getProdRecpOriginDetailList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 재료 및 원산지 등록 팝업 - 조회
     *
     * @param recpOriginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 25.
     */
    @RequestMapping(value = "/prodRecpOriginAdd/getProdRecpOriginAddList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRecpOriginAddList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getProdRecpOriginAddList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 재료 및 원산지 등록 팝업 - 저장
     *
     * @param recpOriginVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 25.
     */
    @RequestMapping(value = "/prodRecpOriginAdd/getProdRecpOriginAddSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRecpOriginAddSave(@RequestBody RecpOriginVO[] recpOriginVOs, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = recpOriginService.getProdRecpOriginAddSave(recpOriginVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 상품-원산지관리탭 - 저장
     *
     * @param recpOriginVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 07. 10.
     */
    @RequestMapping(value = "/prodRecpOrigin/getProdRecpOriginSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdRecpOriginSave(@RequestBody RecpOriginVO[] recpOriginVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = recpOriginService.getProdRecpOriginSave(recpOriginVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 원산지관리-정보입력 - 원산지 조회
     *
     * @param   recpOriginVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 01. 20.
     */
    @RequestMapping(value = "/recpOrigin/getRecpOriginInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpOriginInfoList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getRecpOriginInfoList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 원산지관리-정보입력 - TEXT 조회
     *
     * @param   recpOriginVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 01. 20.
     */
    @RequestMapping(value = "/recpOrigin/getRecpOriginInfoDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpOriginInfoDetailList(RecpOriginVO recpOriginVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = recpOriginService.getRecpOriginInfoDetailList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, recpOriginVO);
    }

    /**
     * 원산지관리-정보입력 원산지관리 저장
     *
     * @param   recpOriginVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 01. 21.
     */
    @RequestMapping(value = "/recpOrigin/getRecpOriginInfoSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpOriginInfoSave(@RequestBody RecpOriginVO[] recpOriginVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = recpOriginService.getRecpOriginInfoSave(recpOriginVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 원산지관리-정보입력 원산지정보 TEXT 저장
     *
     * @param   recpOriginVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 01. 21.
     */
    @RequestMapping(value = "/recpOrigin/getRecpOriginInfoDetailSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRecpOriginInfoDetailSave(@RequestBody RecpOriginVO recpOriginVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = recpOriginService.getRecpOriginInfoDetailSave(recpOriginVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 원산지관리-정보입력 매장적용 팝업 원산지 코드 조회
     *
     * @param   recpOriginVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 01. 21.
     */
    @RequestMapping(value = "/recpOrigin/getSelectOriginCdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelectOriginCdList(HttpServletRequest request, HttpServletResponse response,
                                         Model model, RecpOriginVO recpOriginVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = recpOriginService.getSelectOriginCdList(recpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, recpOriginVO);
    }

    /**
     * 원산지관리-정보입력 매장적용 팝업 매장리스트 조회
     * @param   selectStoreVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 01. 21.
     */
    @RequestMapping(value = "/recpOrigin/getOriginInfoStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOriginInfoStoreList(HttpServletRequest request, HttpServletResponse response,
                                     Model model, SelectStoreVO selectStoreVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = recpOriginService.getOriginInfoStoreList(selectStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, selectStoreVO);
    }

    /**
     * 원산지관리-정보입력 매장적용 팝업 매장적용
     *
     * @param   recpOriginVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 01. 21.
     */
    @RequestMapping(value = "/recpOrigin/getOriginInfoRegStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOriginInfoRegStore(@RequestBody RecpOriginVO[] recpOriginVOs, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = recpOriginService.getOriginInfoRegStore(recpOriginVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}