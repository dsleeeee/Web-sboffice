package kr.co.solbipos.base.price.storeChgCostPrice.web;

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
import kr.co.solbipos.base.price.chgCostPrice.service.ChgCostPriceVO;
import kr.co.solbipos.base.price.storeChgCostPrice.service.StoreChgCostPriceService;
import kr.co.solbipos.base.price.storeChgCostPrice.service.StoreChgCostPriceVO;
import kr.co.solbipos.base.price.storeSplyPrice.service.StoreSplyPriceVO;
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
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreChgCostPriceController.java
 * @Description : 기초관리 - 가격관리 - 매장원가임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.17  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.05.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/storeChgCostPrice")
public class StoreChgCostPriceController {

    private final SessionService sessionService;
    private final StoreChgCostPriceService storeChgCostPriceService;
    private final CmmEnvUtil cmmEnvUtil;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    /** Constructor Injection */
    @Autowired
    public StoreChgCostPriceController(SessionService sessionService, StoreChgCostPriceService storeChgCostPriceService, CmmEnvUtil cmmEnvUtil, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil){
        this.sessionService = sessionService;
        this.storeChgCostPriceService = storeChgCostPriceService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

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
        }

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


        return "base/price/storeChgCostPrice/storeChgCostPriceTab";
    }

    /**
     * 매장원가임의변경 - 상품의 본사 가격정보 조회
     * @param storeChgCostPriceVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.05.20
     */
    @RequestMapping(value = "/getProdPriceInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdPriceInfo(StoreChgCostPriceVO storeChgCostPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = storeChgCostPriceService.getProdPriceInfo(storeChgCostPriceVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장원가임의변경 - 상품별 원가관리 리스트 조회
     * @param storeChgCostPriceVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.05.20
     */
    @RequestMapping(value = "/getByProdChgCostPriceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getByProdChgCostPriceList(StoreChgCostPriceVO storeChgCostPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = storeChgCostPriceService.getByProdChgCostPriceList(storeChgCostPriceVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeChgCostPriceVO);
    }

    /**
     * 매장원가임의변경 - 상품별 원가관리 리스트 엑셀다운로드 조회
     * @param storeChgCostPriceVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.05.20
     */
    @RequestMapping(value = "/getByProdChgCostPriceExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getByProdChgCostPriceExcelList(StoreChgCostPriceVO storeChgCostPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = storeChgCostPriceService.getByProdChgCostPriceExcelList(storeChgCostPriceVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeChgCostPriceVO);
    }

    /**
     * 매장원가임의변경 - 매장 원가 변경
     * @param storeChgCostPriceVOs
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.04.30
     */
    @RequestMapping(value = "/saveStoreCostPrice.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveStoreCostPrice(@RequestBody StoreChgCostPriceVO[] storeChgCostPriceVOs, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeChgCostPriceService.saveStoreCostPrice(storeChgCostPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장원가임의변경 - 매장별 원가관리 리스트 조회
     * @param storeChgCostPriceVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.05.20
     */
    @RequestMapping(value = "/getByStoreChgCostPriceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getByStoreChgCostPriceList(StoreChgCostPriceVO storeChgCostPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = storeChgCostPriceService.getByStoreChgCostPriceList(storeChgCostPriceVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeChgCostPriceVO);
    }

    /**
     * 매장원가임의변경 - 매장별 원가관리 리스트 엑셀다운로드 조회
     * @param storeChgCostPriceVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.05.20
     */
    @RequestMapping(value = "/getByStoreChgCostPriceExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getByStoreChgCostPriceExcelList(StoreChgCostPriceVO storeChgCostPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = storeChgCostPriceService.getByStoreChgCostPriceExcelList(storeChgCostPriceVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeChgCostPriceVO);
    }

    /**
     * 매장원가임의변경 - 엑셀 양식다운로드 조회
     *
     * @param storeChgCostPriceVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.05.20
     */
    @RequestMapping(value = "/getStoreChgCostPriceExcelUploadSampleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreChgCostPriceExcelUploadSampleList(StoreChgCostPriceVO storeChgCostPriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = storeChgCostPriceService.getStoreChgCostPriceExcelUploadSampleList(storeChgCostPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, storeChgCostPriceVO);
    }

    /**
     * 매장원가임의변경 - 원가 업로드 임시테이블 전체 삭제
     * @param storeChgCostPriceVO
     * @param request
     * @param response
     * @author  이다솜
     * @since   2024.05.20
     */
    @RequestMapping(value = "/deleteCostPriceExcelUploadCheckAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCostPriceExcelUploadCheckAll(@RequestBody StoreChgCostPriceVO storeChgCostPriceVO, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeChgCostPriceService.deleteCostPriceExcelUploadCheckAll(storeChgCostPriceVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장원가임의변경 - 원가 업로드 임시테이블 삭제
     * @param storeChgCostPriceVOs
     * @param request
     * @param response
     * @author  이다솜
     * @since   2024.05.20
     */
    @RequestMapping(value = "/deleteCostPriceExcelUploadCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCostPriceExcelUploadCheck(@RequestBody StoreChgCostPriceVO[] storeChgCostPriceVOs, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeChgCostPriceService.deleteCostPriceExcelUploadCheck(storeChgCostPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장원가임의변경 - 원가 업로드 임시테이블 저장
     * @param storeChgCostPriceVOs
     * @param request
     * @param response
     * @author  이다솜
     * @since   2024.05.20
     */
    @RequestMapping(value = "/saveCostPriceExcelUploadCheck.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCostPriceExcelUploadCheck(@RequestBody StoreChgCostPriceVO[] storeChgCostPriceVOs, HttpServletRequest request,
                                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeChgCostPriceService.saveCostPriceExcelUploadCheck(storeChgCostPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장원가임의변경 - 원가 업로드 임시테이블 데이터 조회
     * @param storeChgCostPriceVO
     * @param request
     * @param response
     * @author  이다솜
     * @since   2024.05.20
     */
    @RequestMapping(value = "/getCostPriceExcelUploadCheckList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCostPriceExcelUploadCheckList(StoreChgCostPriceVO storeChgCostPriceVO, HttpServletRequest request,
                                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = storeChgCostPriceService.getCostPriceExcelUploadCheckList(storeChgCostPriceVO, sessionInfoVO);

        return returnListJson(Status.OK, result, storeChgCostPriceVO);
    }

    /**
     * 매장원가임의변경 - 원가 업로드 검증결과 저장
     * @param storeChgCostPriceVOs
     * @param request
     * @param response
     * @author  이다솜
     * @since   2024.05.20
     */
    @RequestMapping(value = "/saveCostPriceExcelUploadCheckResult.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCostPriceExcelUploadCheckResult(@RequestBody StoreChgCostPriceVO[] storeChgCostPriceVOs, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeChgCostPriceService.saveCostPriceExcelUploadCheckResult(storeChgCostPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장원가임의변경 - 원가 엑셀업로드 저장
     * @param storeChgCostPriceVOs
     * @param request
     * @param response
     * @author  이다솜
     * @since   2024.05.20
     */
    @RequestMapping(value = "/saveCostPriceExcelUpload.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCostPriceExcelUpload(@RequestBody StoreChgCostPriceVO[] storeChgCostPriceVOs, HttpServletRequest request,
                                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = storeChgCostPriceService.saveCostPriceExcelUpload(storeChgCostPriceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
