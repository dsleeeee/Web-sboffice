package kr.co.solbipos.sale.benson.prodSaleMonthStoreBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.prodSaleMonthStoreBenson.service.ProdSaleMonthStoreBensonService;
import kr.co.solbipos.sale.benson.prodSaleMonthStoreBenson.service.ProdSaleMonthStoreBensonVO;
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
 * @Class Name : ProdSaleMonthStoreBensonController.java
 * @Description : 벤슨 > 간소화화면 > 상품매출월별(매장)
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
@RequestMapping("/sale/benson/prodSaleMonthStoreBenson")
public class ProdSaleMonthStoreBensonController {

    private final SessionService sessionService;
    private final ProdSaleMonthStoreBensonService prodSaleMonthStoreBensonService;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleMonthStoreBensonController(SessionService sessionService, ProdSaleMonthStoreBensonService prodSaleMonthStoreBensonService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.prodSaleMonthStoreBensonService = prodSaleMonthStoreBensonService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param   request
     * @param   response
     * @param   model
     */
    @RequestMapping(value = "/prodSaleMonthStoreBenson/list.sb", method = RequestMethod.GET)
    public String prodSaleMonthStoreBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

        return "sale/benson/prodSaleMonthStoreBenson/prodSaleMonthStoreBenson";
    }

    /**
     * 상품매출월별(매장) - 조회
     *
     * @param   prodSaleMonthStoreBensonVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleMonthStoreBenson/getProdSaleMonthStoreBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleMonthStoreBensonList(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleMonthStoreBensonService.getProdSaleMonthStoreBensonList(prodSaleMonthStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleMonthStoreBensonVO);
    }
    /**
     * 상품매출월별(매장) - 엑셀다운로드 조회
     *
     * @param   prodSaleMonthStoreBensonVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleMonthStoreBenson/getProdSaleMonthStoreBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleMonthStoreBensonExcelList(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleMonthStoreBensonService.getProdSaleMonthStoreBensonExcelList(prodSaleMonthStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleMonthStoreBensonVO);
    }

    /**
     * 상품매출월별(매장) - 분할 엑셀다운로드 조회
     *
     * @param   prodSaleMonthStoreBensonVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleMonthStoreBenson/getProdSaleMonthStoreBensonExcelDivisionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleMonthStoreBensonExcelDivisionList(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, HttpServletRequest request,
                                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodSaleMonthStoreBensonService.getProdSaleMonthStoreBensonExcelDivisionList(prodSaleMonthStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodSaleMonthStoreBensonVO);
    }

    /**
     * 상품매출월별(매장) - 분할 엑셀다운로드 사용자 제한 체크
     *
     * @param   prodSaleMonthStoreBensonVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleMonthStoreBenson/getDivisionExcelDownloadUserIdChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadUserIdChk(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, HttpServletRequest request,
                                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodSaleMonthStoreBensonService.getDivisionExcelDownloadUserIdChk(prodSaleMonthStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 상품매출월별(매장) - 엑셀다운로드 진행 사용자 저장 insert
     *
     * @param   prodSaleMonthStoreBensonVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleMonthStoreBenson/getDivisionExcelDownloadSaveInsert.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadSaveInsert(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, HttpServletRequest request,
                                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = prodSaleMonthStoreBensonService.getDivisionExcelDownloadSaveInsert(prodSaleMonthStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 상품매출월별(매장) - 엑셀다운로드 진행 사용자 현재 인원수 체크
     *
     * @param   prodSaleMonthStoreBensonVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleMonthStoreBenson/getDivisionExcelDownloadCntChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadCntChk(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = prodSaleMonthStoreBensonService.getDivisionExcelDownloadCntChk(prodSaleMonthStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 상품매출월별(매장) - 엑셀다운로드 진행 사용자 저장 update
     *
     * @param   prodSaleMonthStoreBensonVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     */
    @RequestMapping(value = "/prodSaleMonthStoreBenson/getDivisionExcelDownloadSaveUpdate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDivisionExcelDownloadSaveUpdate(ProdSaleMonthStoreBensonVO prodSaleMonthStoreBensonVO, HttpServletRequest request,
                                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = prodSaleMonthStoreBensonService.getDivisionExcelDownloadSaveUpdate(prodSaleMonthStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
}
