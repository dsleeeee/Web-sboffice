package kr.co.solbipos.sale.benson.prodSaleDayStoreBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.prodSaleDayStoreBenson.service.ProdSaleDayStoreBensonService;
import kr.co.solbipos.sale.benson.prodSaleDayStoreBenson.service.ProdSaleDayStoreBensonVO;
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
 * @Class Name : ProdSaleDayStoreBensonController.java
 * @Description : 벤슨 > 간소화화면 > 상품매출일별(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/benson/prodSaleDayStoreBenson")
public class ProdSaleDayStoreBensonController {

    private final SessionService sessionService;
    private final ProdSaleDayStoreBensonService prodSaleDayStoreBensonService;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleDayStoreBensonController(SessionService sessionService, ProdSaleDayStoreBensonService prodSaleDayStoreBensonService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.prodSaleDayStoreBensonService = prodSaleDayStoreBensonService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodSaleDayStoreBenson/list.sb", method = RequestMethod.GET)
    public String prodSaleDayStoreBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

        return "sale/benson/prodSaleDayStoreBenson/prodSaleDayStoreBenson";
    }

    /**
     * 상품매출일별(매장) - 조회
     *
     * @param prodSaleDayStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleDayStoreBenson/getProdSaleDayStoreBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDayStoreBensonList(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleDayStoreBensonService.getProdSaleDayStoreBensonList(prodSaleDayStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleDayStoreBensonVO);
    }

    /**
     * 상품매출일별(매장) - 엑셀다운로드 조회
     *
     * @param prodSaleDayStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleDayStoreBenson/getProdSaleDayStoreBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDayStoreBensonExcelList(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleDayStoreBensonService.getProdSaleDayStoreBensonExcelList(prodSaleDayStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleDayStoreBensonVO);
    }

    /**
     * 상품매출일별(매장) - 분할 엑셀다운로드 조회
     *
     * @param prodSaleDayStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleDayStoreBenson/getProdSaleDayStoreBensonExcelDivisionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDayStoreBensonExcelDivisionList(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleDayStoreBensonService.getProdSaleDayStoreBensonExcelDivisionList(prodSaleDayStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleDayStoreBensonVO);
    }

    /**
     * 상품매출일별(매장) - 분할 엑셀다운로드 사용자 제한 체크
     *
     * @param prodSaleDayStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleDayStoreBenson/getDivisionExcelDownloadUserIdChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadUserIdChk(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, HttpServletRequest request,
                                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodSaleDayStoreBensonService.getDivisionExcelDownloadUserIdChk(prodSaleDayStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 상품매출일별(매장) - 엑셀다운로드 진행 사용자 저장 insert
     *
     * @param prodSaleDayStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleDayStoreBenson/getDivisionExcelDownloadSaveInsert.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadSaveInsert(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, HttpServletRequest request,
                                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = prodSaleDayStoreBensonService.getDivisionExcelDownloadSaveInsert(prodSaleDayStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 상품매출일별(매장) - 엑셀다운로드 진행 사용자 현재 인원수 체크
     *
     * @param prodSaleDayStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleDayStoreBenson/getDivisionExcelDownloadCntChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadCntChk(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = prodSaleDayStoreBensonService.getDivisionExcelDownloadCntChk(prodSaleDayStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 상품매출일별(매장) - 엑셀다운로드 진행 사용자 저장 update
     *
     * @param prodSaleDayStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleDayStoreBenson/getDivisionExcelDownloadSaveUpdate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadSaveUpdate(ProdSaleDayStoreBensonVO prodSaleDayStoreBensonVO, HttpServletRequest request,
                                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodSaleDayStoreBensonService.getDivisionExcelDownloadSaveUpdate(prodSaleDayStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
}
