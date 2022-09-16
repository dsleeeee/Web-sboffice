package kr.co.solbipos.sale.status.reportKwu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgService;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgVO;
import kr.co.solbipos.sale.status.reportKwu.service.ReportKwuService;
import kr.co.solbipos.sale.status.reportKwu.service.ReportKwuVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : ReportKwuController.java
 * @Description : 광운대 > 리포트 > 분류별결제수단별 매출내역
 *                광운대 > 리포트 > 결제수단별 매출내역
 *                광운대 > 리포트 > 신용카드 매출내역
 *                광운대 > 리포트 > 현금영수증 발행내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.13  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.13
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/reportKwu")
public class ReportKwuController {
    private final SessionService sessionService;
    private final ReportKwuService reportKwuService;
    private final ProdPayFgService prodPayFgService;

    public ReportKwuController(SessionService sessionService, ReportKwuService reportKwuService, ProdPayFgService prodPayFgService){
        this.sessionService = sessionService;
        this.reportKwuService = reportKwuService;
        this.prodPayFgService = prodPayFgService;
    }

    /**
     * 분류별결제수단별 매출내역 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이다솜
     * @since   2022.09.13
     */
    @RequestMapping(value = "/prodClassPayFgSale/view.sb", method = RequestMethod.GET)
    public String prodClassPayFgSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        ProdPayFgVO prodPayFgVO = new ProdPayFgVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = prodPayFgService.getPayColList(prodPayFgVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        return "sale/status/reportKwu/prodClassPayFgSale";
    }

    /**
     * 분류별결제수단별 매출내역 - 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param reportKwuVO
     * @return
     * @author  이다솜
     * @since   2022.09.13
     */
    @RequestMapping(value = "/prodClassPayFgSale/getList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassPayFgSaleList(HttpServletRequest request, HttpServletResponse response, Model model, ReportKwuVO reportKwuVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = reportKwuService.getProdClassPayFgSaleList(reportKwuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, reportKwuVO);
    }

    /**
     * 결제수단별 매출내역 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이다솜
     * @since   2022.09.13
     */
    @RequestMapping(value = "/payFgSale/view.sb", method = RequestMethod.GET)
    public String payFgSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        ProdPayFgVO prodPayFgVO = new ProdPayFgVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = prodPayFgService.getPayColList(prodPayFgVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        return "sale/status/reportKwu/payFgSale";
    }

    /**
     * 결제수단별 매출내역 - 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param reportKwuVO
     * @return
     * @author  이다솜
     * @since   2022.09.13
     */
    @RequestMapping(value = "/payFgSale/getList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayFgSaleList(HttpServletRequest request, HttpServletResponse response, Model model, ReportKwuVO reportKwuVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = reportKwuService.getPayFgSaleList(reportKwuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, reportKwuVO);
    }

    /**
     * 신용카드 매출내역 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이다솜
     * @since   2022.09.13
     */
    @RequestMapping(value = "/payCardSale/view.sb", method = RequestMethod.GET)
    public String payCardSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/status/reportKwu/payCardSale";
    }

    /**
     * 신용카드 매출내역 - 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param reportKwuVO
     * @return
     * @author  이다솜
     * @since   2022.09.13
     */
    @RequestMapping(value = "/payCardSale/getList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayCardSaleList(HttpServletRequest request, HttpServletResponse response, Model model, ReportKwuVO reportKwuVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = reportKwuService.getPayCardSaleList(reportKwuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, reportKwuVO);
    }

    /**
     * 현금영수증 발행내역 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  이다솜
     * @since   2022.09.13
     */
    @RequestMapping(value = "/cashBillInfo/view.sb", method = RequestMethod.GET)
    public String cashBillInfoView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/status/reportKwu/cashBillInfo";
    }

    /**
     * 현금영수증 발행내역 - 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param reportKwuVO
     * @return
     * @author  이다솜
     * @since   2022.09.13
     */
    @RequestMapping(value = "/cashBillInfo/getList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCashBillInfoList(HttpServletRequest request, HttpServletResponse response, Model model, ReportKwuVO reportKwuVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = reportKwuService.getCashBillInfoList(reportKwuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, reportKwuVO);
    }
}
