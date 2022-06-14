package kr.co.solbipos.sale.status.nonSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.nonSale.service.NonSaleService;
import kr.co.solbipos.sale.status.nonSale.service.NonSaleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : NonSaleController.java
 * @Description : 매출관리 > 매출현황2 > 보증금현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.05.16  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.05.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/nonSale")
public class NonSaleController {
    private final SessionService sessionService;
    private final NonSaleService nonSaleService;

    public NonSaleController(SessionService sessionService, NonSaleService nonSaleService) {
        this.sessionService = sessionService;
        this.nonSaleService = nonSaleService;
    }

    /**
     * 보증금현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2022.05.16
     */
    @RequestMapping(value = "/nonSale/view.sb", method = RequestMethod.GET)
    public String NonSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/status/nonSale/nonSaleTab";
    }

    /**
     * 일별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   nonSaleVO
     * @return  String
     * @author  권지현
     * @since   2022.05.16
     */
    @RequestMapping(value = "/nonSale/getNonSaleDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNonSaleDayList(HttpServletRequest request, HttpServletResponse response,
                                    Model model, NonSaleVO nonSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = nonSaleService.getNonSaleDayList(nonSaleVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  nonSaleVO);
    }

    /**
     * 일별 - 엑셀리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   nonSaleVO
     * @return  String
     * @author  권지현
     * @since   2022.05.16
     */
    @RequestMapping(value = "/nonSale/getNonSaleDayExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNonSaleDayExcelList(HttpServletRequest request, HttpServletResponse response,
                                    Model model, NonSaleVO nonSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = nonSaleService.getNonSaleDayExcelList(nonSaleVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  nonSaleVO);
    }

    /**
     * 반환내역 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   nonSaleVO
     * @return  String
     * @author  권지현
     * @since   2022.06.08
     */
    @RequestMapping(value = "/nonSale/getCupRefundList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCupRefundList(HttpServletRequest request, HttpServletResponse response,
                                    Model model, NonSaleVO nonSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = nonSaleService.getCupRefundList(nonSaleVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  nonSaleVO);
    }

    /**
     * 반환내역 - 엑셀리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   nonSaleVO
     * @return  String
     * @author  권지현
     * @since   2022.06.08
     */
    @RequestMapping(value = "/nonSale/getCupRefundExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCupRefundExcelList(HttpServletRequest request, HttpServletResponse response,
                                    Model model, NonSaleVO nonSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = nonSaleService.getCupRefundExcelList(nonSaleVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  nonSaleVO);
    }

}
