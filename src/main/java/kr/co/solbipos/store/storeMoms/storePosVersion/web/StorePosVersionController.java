package kr.co.solbipos.store.storeMoms.storePosVersion.web;

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
import kr.co.solbipos.pos.confg.vermanage.service.ApplcStoreVO;
import kr.co.solbipos.pos.confg.vermanage.service.VerManageService;
import kr.co.solbipos.store.storeMoms.storePosVersion.service.StorePosVersionService;
import kr.co.solbipos.store.storeMoms.storePosVersion.service.StorePosVersionVO;
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
 * @Class Name : StorePosVersionController.java
 * @Description : 맘스터치 > 매장관리 > 매장포스버전현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.30  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.03.30
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/store/storeMoms/storePosVersion")
public class StorePosVersionController {
    private final SessionService sessionService;
    private final StorePosVersionService storePosVersionService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    @Autowired
    public StorePosVersionController(SessionService sessionService, StorePosVersionService storePosVersionService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.storePosVersionService = storePosVersionService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2023.03.30
     */
    @RequestMapping(value = "/storePosVersion/view.sb", method = RequestMethod.GET)
    public String empMonthView(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        // 버전선택
        List selectVerComboList = storePosVersionService.getSelectVerList(sessionInfoVO);

        // 매장포스버전현황 탭
        String selectVerComboListAll = cmmCodeUtil.assmblObj(selectVerComboList, "name", "value", UseYn.N);

        model.addAttribute("selectVerComboList", selectVerComboListAll);

        // 포스패치로그 탭
        String selectVerComboListAllSel = cmmCodeUtil.assmblObj(selectVerComboList, "name", "value", UseYn.SELECT);

        model.addAttribute("selectVerComboListSel", selectVerComboListAllSel);

        System.out.println("ㅎㅇ올"+selectVerComboListAll);
        System.out.println("ㅎㅇ올셀"+selectVerComboListAllSel);

        // 포스용도
        List selectSubPos = storePosVersionService.getSelectSubPos();

        String selectSubPosAll = cmmCodeUtil.assmblObj(selectSubPos, "name", "value", UseYn.ALL);

        model.addAttribute("selectSubPos", selectSubPosAll);


        return "store/storeMoms/storePosVersion/storePosVersionTab";
    }

    /**
     * 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storePosVersionVO
     * @return  String
     * @author  권지현
     * @since   2023.03.30
     */
    @RequestMapping(value = "/storePosVersion/getStorePosVersionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStorePosVersionList(HttpServletRequest request, HttpServletResponse response,
                                   Model model, StorePosVersionVO storePosVersionVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storePosVersionService.getStorePosVersionList(storePosVersionVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storePosVersionVO);
    }

    /**
     * 패치정보 상세 팝업
     * @param   request
     * @param   response
     * @param   model
     * @param   storePosVersionVO
     * @return  String
     * @author  김유승
     * @since   2024.03.12
     */
    @RequestMapping(value = "/patchDtl/patchDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result posDtlList(HttpServletRequest request, HttpServletResponse response,
                                         Model model, StorePosVersionVO storePosVersionVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storePosVersionService.getPatchDtlList(storePosVersionVO);

        return ReturnUtil.returnListJson(Status.OK, list, storePosVersionVO);
    }

    /**
     * 버전 적용 매장 등록
     *
     * @param   request
     * @param   response
     * @param   model
     * @param   storePosVersionVO
     * @return
     * @author  김유승
     * @since   2024.03.12
     */
    @RequestMapping(value = "/storePosVersion/registStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result registStore(@RequestBody StorePosVersionVO storePosVersionVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        int result = storePosVersionService.registStore(storePosVersionVO, sessionInfo);

        return returnJson(Status.OK, result);
    }

    /**
     * 포스패치로그 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storePosVersionVO
     * @return
     * @author  김유승
     * @since   2024.03.13
     */
    @RequestMapping(value = "/posPatchLog/posPatchLogList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result posPatchLogList(HttpServletRequest request, HttpServletResponse response,
                             Model model, StorePosVersionVO storePosVersionVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storePosVersionService.getPosPatchLogList(storePosVersionVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storePosVersionVO);
    }

}
