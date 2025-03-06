package kr.co.solbipos.store.manage.virtualLoginById.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.store.manage.virtualLoginById.service.VirtualLoginByIdService;
import kr.co.solbipos.store.manage.virtualLoginById.service.VirtualLoginByIdVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : VirtualLoginByIdController.java
 * @Description : 기초관리 > 가상로그인 > 가상 로그인(아이디별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.02.06  김유승      최초생성
 *
 * @author 링크 WEB개발팀 김유승
 * @since 2025.02.06
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/store/manage/virtualLoginById")
public class VirtualLoginByIdController {

    /** service */
    private final VirtualLoginByIdService virtualLoginByIdService;
    private final SessionService sessionService;
    private final AuthService authService;
    private final CmmMenuService cmmMenuService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    public VirtualLoginByIdController(VirtualLoginByIdService virtualLoginByIdService, SessionService sessionService, AuthService authService, CmmMenuService cmmMenuService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.virtualLoginByIdService = virtualLoginByIdService;
        this.sessionService = sessionService;
        this.authService = authService;
        this.cmmMenuService = cmmMenuService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 가상로그인(아이디별) - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2025. 02. 07.
     */
    @RequestMapping(value = "/virtualLoginById/view.sb", method = RequestMethod.GET)
    public String virtualLoginView(HttpServletRequest request, HttpServletResponse response,
                                   Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {

            // 브랜드사용여부
            model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));

            // 사용자별 브랜드 콤보박스 조회
            DayProdVO dayProdVO = new DayProdVO();
            model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

            /** 맘스터치 */
            // [1250 맘스터치] 환경설정값 조회
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));

            // 사용자별 코드별 공통코드 콤보박스 조회
            // - 팀별
            List momsTeamComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "151");
            model.addAttribute("momsTeamComboList", momsTeamComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsTeamComboList, "name", "value", UseYn.N));

            // - AC점포별
            List momsAcShopComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "152");
            model.addAttribute("momsAcShopComboList", momsAcShopComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsAcShopComboList, "name", "value", UseYn.N));

            // - 지역구분
            List momsAreaFgComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "153");
            model.addAttribute("momsAreaFgComboList", momsAreaFgComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsAreaFgComboList, "name", "value", UseYn.N));

            // - 상권
            List momsCommercialComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "154");
            model.addAttribute("momsCommercialComboList", momsCommercialComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsCommercialComboList, "name", "value", UseYn.N));

            // - 점포유형
            List momsShopTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "155");
            model.addAttribute("momsShopTypeComboList", momsShopTypeComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsShopTypeComboList, "name", "value", UseYn.N));

            // - 매장관리타입
            List momsStoreManageTypeComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "156");
            model.addAttribute("momsStoreManageTypeComboList", momsStoreManageTypeComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreManageTypeComboList, "name", "value", UseYn.N));

            // - 사용자별 그룹 콤보박스 조회
            List branchCdComboList = dayProdService.getUserBranchComboList(sessionInfoVO);
            model.addAttribute("branchCdComboList", branchCdComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(branchCdComboList, "name", "value", UseYn.N));

            // - 매장그룹
            List momsStoreFg01ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "167");
            model.addAttribute("momsStoreFg01ComboList", momsStoreFg01ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg01ComboList, "name", "value", UseYn.N));

            // - 매장그룹2
            List momsStoreFg02ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "169");
            model.addAttribute("momsStoreFg02ComboList", momsStoreFg02ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg02ComboList, "name", "value", UseYn.N));

            // - 매장그룹3
            List momsStoreFg03ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "170");
            model.addAttribute("momsStoreFg03ComboList", momsStoreFg03ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg03ComboList, "name", "value", UseYn.N));

            // - 매장그룹4
            List momsStoreFg04ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "171");
            model.addAttribute("momsStoreFg04ComboList", momsStoreFg04ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg04ComboList, "name", "value", UseYn.N));

            // - 매장그룹5
            List momsStoreFg05ComboList = dayProdService.getUserHqNmcodeComboList(sessionInfoVO, "172");
            model.addAttribute("momsStoreFg05ComboList", momsStoreFg05ComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(momsStoreFg05ComboList, "name", "value", UseYn.N));

        }else{
            // 관리자 또는 총판은 빈 콤보박스 셋팅(script 오류 방지)
            model.addAttribute("userHqBrandCdComboList", CmmUtil.comboListAll());
            model.addAttribute("momsTeamComboList", CmmUtil.comboListAll());
            model.addAttribute("momsAcShopComboList", CmmUtil.comboListAll());
            model.addAttribute("momsAreaFgComboList", CmmUtil.comboListAll());
            model.addAttribute("momsCommercialComboList", CmmUtil.comboListAll());
            model.addAttribute("momsShopTypeComboList", CmmUtil.comboListAll());
            model.addAttribute("momsStoreManageTypeComboList", CmmUtil.comboListAll());
            model.addAttribute("branchCdComboList", CmmUtil.comboListAll());
            model.addAttribute("momsStoreFg01ComboList", CmmUtil.comboListAll());
            model.addAttribute("momsStoreFg02ComboList", CmmUtil.comboListAll());
            model.addAttribute("momsStoreFg03ComboList", CmmUtil.comboListAll());
            model.addAttribute("momsStoreFg04ComboList", CmmUtil.comboListAll());
            model.addAttribute("momsStoreFg05ComboList", CmmUtil.comboListAll());
        }

        return "store/manage/virtualLoginById/virtualLoginById";
    }

    /**
     * 가상로그인(아이디별) - 조회
     * @param   request
     * @param   response
     * @param   virtualLoginByIdVO
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2025. 02. 07.
     */

    @RequestMapping(value = "/virtualLoginById/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVirtualLoginByIdList(HttpServletRequest request, HttpServletResponse response,
                                      VirtualLoginByIdVO virtualLoginByIdVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = virtualLoginByIdService.getVirtualLoginByIdList(virtualLoginByIdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, virtualLoginByIdVO);

    }
}
