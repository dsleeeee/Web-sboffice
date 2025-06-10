package kr.co.solbipos.base.output.postemplate.web;

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
import kr.co.solbipos.base.output.postemplate.service.PosTemplateService;
import kr.co.solbipos.base.output.postemplate.service.PosTemplateVO;
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
 * @Class Name : TemplateController.java
 * @Description : 기초관리 > 출력물관리 > 포스출력물관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.04  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/output/posTemplate")
public class PosTemplateController {

    private final PosTemplateService posTemplateService;
    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public PosTemplateController(PosTemplateService posTemplateService, SessionService sessionService, CmmEnvUtil cmmEnvUtil, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.posTemplateService = posTemplateService;
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 포스출력물관리 - 페이지 이동
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return String
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/view.sb", method = RequestMethod.GET)
    public String posView(HttpServletRequest request, HttpServletResponse response, Model model) {

        List<DefaultMap<String>> posTemplateList = new ArrayList<DefaultMap<String>>();
        PosTemplateVO params = new PosTemplateVO();
        // 출력물종류 목록 조회
        posTemplateList = posTemplateService.getPrintTypeList(params);
        model.addAttribute("listPrintType", convertToJson(posTemplateList));


        // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값울 판단하여 view화면 처리
        if(request.getParameter("posLoginReconnect") != null && request.getParameter("posLoginReconnect").length() > 0){
         model.addAttribute("posLoginReconnect", request.getParameter("posLoginReconnect"));
        }

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

        return "base/output/posTemplate/posTemplate";
    }
    
    /**
     * 포스출력물관리 - 출력물코드 목록 조회
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param posTemplateVO PosTemplateVO
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/code/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrintCodeList(HttpServletRequest request, HttpServletResponse response,
        PosTemplateVO posTemplateVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 출력물코드 목록 조회
        list = posTemplateService.getPrintCodeList(posTemplateVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, posTemplateVO);

    }
    
    /**
     * 포스출력물관리 - 출력물템플릿 목록 조회
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param posTemplateVO PosTemplateVO
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosTemplateList(HttpServletRequest request, HttpServletResponse response,
        PosTemplateVO posTemplateVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 소속구분 설정
        posTemplateVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        posTemplateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        posTemplateVO.setStoreCd(sessionInfoVO.getStoreCd());

        // 매장인경우에는 실제출력물 실시간 생성
        if ( "S".equals(posTemplateVO.getOrgnFg()) ) {
            // 실제출력물 없는경우 대비해서 저장처리
            posTemplateService.insertPosTemplatePrint(posTemplateVO, sessionInfoVO);
        }
        // 출력물코드 목록 조회
        list = posTemplateService.getPosTemplateList(posTemplateVO);

        return ReturnUtil.returnListJson(Status.OK, list, posTemplateVO);
        
    }

    /**
     * 포스출력물관리 - 출력물템플릿 목록 저장
     *
     * @param posTemplateVOs PosTemplateVO[]
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/saveList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePosTemplateList(@RequestBody PosTemplateVO[] posTemplateVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = posTemplateService.savePosTemplateList(posTemplateVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * 포스출력물관리 - 출력물템플릿 저장
     *
     * @param posTemplateVO PosTemplateVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result savePosTemplate(@RequestBody PosTemplateVO posTemplateVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = posTemplateService.savePosTemplate(posTemplateVO, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * 포스출력물관리 - 출력물템플릿 실제출력물저장
     *
     * @param posTemplateVO PosTemplateVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/applyToPrint.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePosTemplatePrint(@RequestBody PosTemplateVO posTemplateVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = posTemplateService.updatePosTemplatePrint(posTemplateVO, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * 포스출력물관리 - 출력물템플릿 매장적용
     *
     * @param posTemplateVO PosTemplateVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/applyToStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result applyToStoreTemplate(@RequestBody PosTemplateVO posTemplateVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = posTemplateService.applyToStoreTemplate(posTemplateVO, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /**
     * 포스출력물관리 - 매장조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param posTemplateVO PosTemplateVO
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/getRegStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRegStoreList(HttpServletRequest request, HttpServletResponse response,
                                   PosTemplateVO posTemplateVO, Model model) {

        List<DefaultMap<String>> list = new ArrayList<DefaultMap<String>>();
        // 출력물코드 목록 조회
        list = posTemplateService.getRegStoreList(posTemplateVO);

        return ReturnUtil.returnListJson(Status.OK, list, posTemplateVO);

    }

    /**
     * 포스출력물관리 - 출력물템플릿 매장적용
     *
     * @param posTemplateVOs PosTemplateVO[]
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 10. 04.
     */
    @RequestMapping(value = "/template/applyToStoreReal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result applyToStoreReal(@RequestBody PosTemplateVO[] posTemplateVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = posTemplateService.applyToStoreReal(posTemplateVOs, sessionInfoVO);
        return returnJson(Status.OK, result);
    }

    /**
     *  출력코드명 조회
     *
     * @param request
     * @param response
     * @param posTemplateVO
     * @param model
     * @return String
     * @author 이다솜
     * @since 2023. 06. 19.
     */
    @RequestMapping(value = "/template/getPrintCodeNm.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrintCodeNm(HttpServletRequest request, HttpServletResponse response,
                                           PosTemplateVO posTemplateVO, Model model) {

        String result = posTemplateService.getPrintCodeNm(posTemplateVO);

        return returnJson(Status.OK, result);
    }

}
