package kr.co.solbipos.base.store.empBatchChanhe.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.empBatchChanhe.service.EmpBatchChangeService;
import kr.co.solbipos.base.store.empBatchChanhe.service.EmpBatchChangeVO;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
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
 * @Class Name : EmpBatchChangeController.java
 * @Description : 기초관리 > 사원관리 > 사원정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.16  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.02.16
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value="/base/store/empBatchChange/")
public class EmpBatchChangeController {

    private final EmpBatchChangeService empBatchChangeService;
    private final SessionService sessionService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public EmpBatchChangeController(EmpBatchChangeService empBatchChangeService, SessionService sessionService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.empBatchChangeService = empBatchChangeService;
        this.sessionService = sessionService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 점포영업지역관리 화면 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2023.02.16
     */
    @RequestMapping(value = "empBatchChange/view.sb", method = RequestMethod.GET)
    public String view(EmpBatchChangeVO empBatchChangeVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        StoreChannelVO storeChannelVO = new StoreChannelVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

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

        // 사용자별 지사 콤보박스 조회
        // 지사
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
            String momsTeamComboListAll2 = "";
            momsTeamComboListAll2 = cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.N);
            model.addAttribute("momsTeamComboList2", momsTeamComboListAll2);
        } else {
            model.addAttribute("momsTeamComboList2", momsTeamComboListAll);
        }
        // AC점포별
        if (momsAcShopComboList.size() > 1) {
            momsAcShopComboList.remove(0);
            String momsAcShopComboListAll2 = "";
            momsAcShopComboListAll2 = cmmCodeUtil.assmblObj(momsAcShopComboList, "name", "value", UseYn.N);
            model.addAttribute("momsAcShopComboList2", momsAcShopComboListAll2);
        } else {
            model.addAttribute("momsAcShopComboList2", momsAcShopComboListAll);
        }
        // 지역구분
        if (momsAreaFgComboList.size() > 1) {
            momsAreaFgComboList.remove(0);
            String momsAreaFgComboListAll2 = "";
            momsAreaFgComboListAll2 = cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.N);
            model.addAttribute("momsAreaFgComboList2", momsAreaFgComboListAll2);
        } else {
        model.addAttribute("momsAreaFgComboList2", momsAreaFgComboListAll);
        }
        // 상권
        if(momsCommercialComboList.size() > 1) {
            momsCommercialComboList.remove(0);
            String momsCommercialComboListAll2 = "";
            momsCommercialComboListAll2 = cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.N);
            model.addAttribute("momsCommercialComboList2", momsCommercialComboListAll2);
        } else {
            model.addAttribute("momsCommercialComboList2", momsCommercialComboListAll);
        }
        // 점포유형
        if(momsShopTypeComboList.size() > 1) {
            momsShopTypeComboList.remove(0);
            String momsShopTypeComboListAll2 = "";
            momsShopTypeComboListAll2 = cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.N);
            model.addAttribute("momsShopTypeComboList2", momsShopTypeComboListAll2);
        } else {
            model.addAttribute("momsShopTypeComboList2", momsShopTypeComboListAll);
        }
        // 매장관리타입
        if(momsStoreManageTypeComboList.size() > 1) {
            momsStoreManageTypeComboList.remove(0);
            String momsStoreManageTypeComboListAll2 = "";
            momsStoreManageTypeComboListAll2 = cmmCodeUtil.assmblObj(momsStoreManageTypeComboList, "name", "value", UseYn.N);
            model.addAttribute("momsStoreManageTypeComboList2", momsStoreManageTypeComboListAll2);
        } else {
            model.addAttribute("momsStoreManageTypeComboList2", momsStoreManageTypeComboListAll);
        }

        // 사용자별 지사 콤보박스 조회
        // 지사
        if(branchCdComboList.size() > 1) {
            branchCdComboList.remove(0);
            String branchCdComboListAll2 = "";
            branchCdComboListAll2 = cmmCodeUtil.assmblObj(branchCdComboList, "name", "value", UseYn.N);
            model.addAttribute("branchCdComboList2", branchCdComboListAll2);
        } else {
            model.addAttribute("branchCdComboList2", branchCdComboListAll);
        }

        return "base/store/empBatchChange/empBatchChangeTab";
    }

    /**
     * 매장목록 조회
     * @param   empBatchChangeVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  권지현
     * @since   2023.02.16
     */
    @RequestMapping(value = "empBatchChange/getEmpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpList(EmpBatchChangeVO empBatchChangeVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = empBatchChangeService.getEmpList(empBatchChangeVO, sessionInfoVO);

        return returnListJson(Status.OK, list, empBatchChangeVO);
    }

    /**
     * 매장정보 저장
     *
     * @param empBatchChangeVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2023.01.18
     */
    @RequestMapping(value = "empBatchChange/getEmpBatchChangeSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpBatchChangeSave(@RequestBody EmpBatchChangeVO[] empBatchChangeVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empBatchChangeService.getEmpBatchChangeSave(empBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 업로드시 임시테이블 삭제
     *
     * @param empBatchChangeVO
     * @param request
     * @return  Object
     * @author  권지현
     * @since   2023.01.18
     */
    @RequestMapping(value = "empBatchChange/getEmpExcelUploadCheckDeleteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpExcelUploadCheckDeleteAll(@RequestBody EmpBatchChangeVO empBatchChangeVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empBatchChangeService.getEmpExcelUploadCheckDeleteAll(empBatchChangeVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /** 업로드시 임시테이블 저장 */
    @RequestMapping(value = "empBatchChange/getEmpExcelUploadCheckSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpExcelUploadCheckSave(@RequestBody EmpBatchChangeVO[] empBatchChangeVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empBatchChangeService.getEmpExcelUploadCheckSave(empBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);

    }

    /** 검증결과 조회 */
    @RequestMapping(value = "empBatchChange/getEmpExcelUploadCheckList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExcelList(EmpBatchChangeVO empBatchChangeVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = empBatchChangeService.getEmpExcelUploadCheckList(empBatchChangeVO, sessionInfoVO);

        return returnListJson(Status.OK, list, empBatchChangeVO);
    }

    /** 검증결과 저장 */
    @RequestMapping(value = "empBatchChange/getEmpExcelUploadCheckSaveAdd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpExcelUploadCheckSaveAdd(@RequestBody EmpBatchChangeVO[] empBatchChangeVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empBatchChangeService.getEmpExcelUploadCheckSaveAdd(empBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /** 엑셀 저장 */
    @RequestMapping(value = "empBatchChange/getSimpleSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSimpleSave(@RequestBody EmpBatchChangeVO[] empBatchChangeVOs, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empBatchChangeService.getSimpleSave(empBatchChangeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
