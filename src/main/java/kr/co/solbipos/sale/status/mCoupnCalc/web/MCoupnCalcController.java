package kr.co.solbipos.sale.status.mCoupnCalc.web;

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
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.status.mCoupnCalc.service.MCoupnCalcService;
import kr.co.solbipos.sale.status.mCoupnCalc.service.MCoupnCalcVO;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Class Name : MCoupnCalcController.java
 * @Description : 맘스터치 > 매출분석2 > 모바일쿠폰 정산
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.19  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.07.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/status/mCoupnCalc")
public class MCoupnCalcController {

    private final SessionService sessionService;
    private final MCoupnCalcService mCoupnCalcService;
    private final CmmCodeUtil cmmCodeUtil;
    private final DayProdService dayProdService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public MCoupnCalcController(SessionService sessionService, MCoupnCalcService mCoupnCalcService, CmmCodeUtil cmmCodeUtil, DayProdService dayProdService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.mCoupnCalcService = mCoupnCalcService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.dayProdService = dayProdService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mCoupnCalc/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        /** 맘스터치 */
        // [1250 맘스터치] 환경설정값 조회
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1250"), "0"));
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            model.addAttribute("momsEnvstVal", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1250"), "0"));
        }

        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("momsHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        // 사용자별 그룹 콤보박스 조회
        List branchCdComboList = dayProdService.getUserBranchComboList(sessionInfoVO);
        model.addAttribute("branchCdComboList", branchCdComboList.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(branchCdComboList, "name", "value", UseYn.N));

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


        return "sale/status/mCoupnCalc/mCoupnCalc";
    }

    /**
     * 모바일쿠폰 현황 - 조회
     *
     * @param mCoupnCalcVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2023. 07. 19.
     */
    @RequestMapping(value = "/mCoupnCalc/getMCoupnCalcList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnCalcList(MCoupnCalcVO mCoupnCalcVO, HttpServletRequest request, HttpServletResponse response, Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

       List<DefaultMap<Object>> result = mCoupnCalcService.getMCoupnCalcList(mCoupnCalcVO, sessionInfoVO);

       return ReturnUtil.returnListJson(Status.OK, result, mCoupnCalcVO);
    }

    /**
     * 모바일쿠폰 정산 조회조건 엑셀다운로드
     *
     * @param mCoupnCalcVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2023. 07. 19.
     */
    @RequestMapping(value = "/mCoupnCalc/getMCoupnCalcExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnCalcExcelList(MCoupnCalcVO mCoupnCalcVO, HttpServletRequest request, HttpServletResponse response, Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

       List<DefaultMap<Object>> result = mCoupnCalcService.getMCoupnCalcExcelList(mCoupnCalcVO, sessionInfoVO);

       return ReturnUtil.returnListJson(Status.OK, result, mCoupnCalcVO);
    }

    /**
     * 모바일쿠폰 정산 상세화면 조회
     *
     * @param mCoupnCalcVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2023. 07. 19.
     */
    @RequestMapping(value = "/mCoupnCalc/getMCoupnCalcDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMCoupnCalcDtlList(MCoupnCalcVO mCoupnCalcVO, HttpServletRequest request, HttpServletResponse response, Model model) {

       SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

       List<DefaultMap<Object>> result = mCoupnCalcService.getMCoupnCalcDtlList(mCoupnCalcVO, sessionInfoVO);

       return ReturnUtil.returnListJson(Status.OK, result, mCoupnCalcVO);
    }
}
