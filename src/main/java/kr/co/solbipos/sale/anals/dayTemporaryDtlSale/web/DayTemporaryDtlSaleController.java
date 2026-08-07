package kr.co.solbipos.sale.anals.dayTemporaryDtlSale.web;

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
import kr.co.solbipos.sale.anals.dayTemporaryDtlSale.service.DayTemporaryDtlSaleService;
import kr.co.solbipos.sale.anals.dayTemporaryDtlSale.service.DayTemporaryDtlSaleVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
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
 * @Class Name  : DayTemporaryDtlSaleController.java
 * @Description : 맘스터치 > 매출분석2 > 일별 가승인 상세매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.05  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/anals/dayTemporaryDtlSale")
public class DayTemporaryDtlSaleController {

    private final SessionService sessionService;
    private final DayTemporaryDtlSaleService dayTemporaryDtlSaleService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayTemporaryDtlSaleController(SessionService sessionService, DayTemporaryDtlSaleService dayTemporaryDtlSaleService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.dayTemporaryDtlSaleService = dayTemporaryDtlSaleService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        // 맘스터치 전용 - 매장별가승인매출 코드 조회
        List<DefaultMap<Object>> temporaryPayColList = dayTemporaryDtlSaleService.getTemporaryPayColList();

        // 매장별가승인매출 코드를 , 로 연결하는 문자열 생성
        String temporaryPayCol = "";
        for(int i=0; i < temporaryPayColList.size(); i++) {
            temporaryPayCol += (temporaryPayCol.equals("") ? "" : ",") + temporaryPayColList.get(i).getStr("nmcodeCd");
        }
        model.addAttribute("temporaryPayColList", temporaryPayColList);

        return "sale/anals/dayTemporaryDtlSale/dayTemporaryDtlSale";
    }

    /**
     * 일별 가승인 상세매출 - 조회
     *
     * @param   request
     * @param   response
     * @param   model
     * @param   dayTemporaryDtlSaleVO
     * @return
     * @author  이다솜
     * @since   2026.08.05
     */
    @RequestMapping(value = "/getDayTemporaryDtlSaleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayTemporaryDtlSaleList(HttpServletRequest request, HttpServletResponse response,
                                              Model model, DayTemporaryDtlSaleVO dayTemporaryDtlSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayTemporaryDtlSaleService.getDayTemporaryDtlSaleList(dayTemporaryDtlSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayTemporaryDtlSaleVO);
    }
}
