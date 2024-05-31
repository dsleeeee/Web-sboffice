package kr.co.solbipos.sale.cmmSalePopup.prodInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.ProdInfoService;
import kr.co.solbipos.sale.cmmSalePopup.prodInfo.service.ProdInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : ProdInfoController.java
 * @Description : 매출공통팝업 - 상품매출 상세내역 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.06.17  이다솜      최초생성
 * @ 2019.12.11  김설아      수정
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2019.06.17
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/cmmSalePopup/prodInfo")
public class ProdInfoController {

    private final SessionService sessionService;
    private final ProdInfoService prodInfoService;

    @Autowired
    public ProdInfoController(SessionService sessionService, ProdInfoService prodInfoService) {
        this.sessionService = sessionService;
        this.prodInfoService = prodInfoService;
    }

    /**
     * 매출공통팝업 - 상품매출 상세내역 조회
     *
     * @param   prodInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김설아
     * @since   2019. 12. 11.
     */
//    @RequestMapping(value = "/prodSaleDtl/list.sb", method = RequestMethod.POST)
//    @ResponseBody
//    public Result getProdSaleDtlList(ProdInfoVO prodInfoVO, HttpServletRequest request,
//                                           HttpServletResponse response, Model model) {
//
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//
//        List<DefaultMap<Object>> result = prodInfoService.getProdSaleDtlList(prodInfoVO, sessionInfoVO);
//
//        return ReturnUtil.returnListJson(Status.OK, result, prodInfoVO);
//    }

    /**
     * 매출공통팝업 - 상품매출 상세내역 조회
     *
     * @param   prodInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 24.
     */
    @RequestMapping(value = "/prodSaleDtl/getProdSaleDtlDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDtlDayList(ProdInfoVO prodInfoVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodInfoService.getProdSaleDtlDayList(prodInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodInfoVO);
    }

    /**
     * 매출공통팝업 - 상품매출 상세내역 조회
     *
     * @param   prodInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 24.
     */
    @RequestMapping(value = "/prodSaleDtl/getProdSaleDtlMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDtlMonthList(ProdInfoVO prodInfoVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodInfoService.getProdSaleDtlMonthList(prodInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodInfoVO);
    }

    /**
     * 매출공통팝업 - 상품매출 상세내역 조회
     *
     * @param   prodInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 24.
     */
    @RequestMapping(value = "/prodSaleDtl/getProdSaleDtlDayProdClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDtlDayProdClassList(ProdInfoVO prodInfoVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodInfoService.getProdSaleDtlDayProdClassList(prodInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodInfoVO);
    }

    /**
     * 매출공통팝업 - 상품매출 상세내역 조회
     *
     * @param   prodInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 24.
     */
    @RequestMapping(value = "/prodSaleDtl/getProdSaleDtlMonthProdClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDtlMonthProdClassList(ProdInfoVO prodInfoVO, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodInfoService.getProdSaleDtlMonthProdClassList(prodInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodInfoVO);
    }

    /**
     * 매출공통팝업 - 상품매출 상세내역 조회
     *
     * @param   prodInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 02. 28.
     */
    @RequestMapping(value = "/prodSaleDtl/getProdSaleDtlDayOfWeekList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleDtlDayOfWeekList(ProdInfoVO prodInfoVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodInfoService.getProdSaleDtlDayOfWeekList(prodInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodInfoVO);
    }

    /**
     * 매출공통팝업 - 일자별 - 포스별 상품매출 상세내역 조회
     *
     * @param   prodInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 05. 17.
     */
    @RequestMapping(value = "/prodSaleDtl/getPosProdSaleDtlDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosProdSaleDtlDayList(ProdInfoVO prodInfoVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodInfoService.getPosProdSaleDtlDayList(prodInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodInfoVO);
    }


    /**
     * 매출공통팝업 - 월별 - 포스별 상품매출 상세내역 조회
     *
     * @param   prodInfoVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024. 05. 20.
     */
    @RequestMapping(value = "/prodSaleDtl/getPosProdSaleDtlMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosProdSaleDtlMonthList(ProdInfoVO prodInfoVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = prodInfoService.getPosProdSaleDtlMonthList(prodInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prodInfoVO);
    }
}