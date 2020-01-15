package kr.co.solbipos.sale.cmmSalePopup.saleInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.saleInfo.service.SaleInfoService;
import kr.co.solbipos.sale.cmmSalePopup.saleInfo.service.SaleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import static kr.co.common.utils.grid.ReturnUtil.returnJson;

@Controller
@RequestMapping("/sale/cmmSalePopup/saleInfo")
public class SaleInfoController {

    private final SessionService sessionService;
    private final SaleInfoService saleInfoService;
    private final DayService dayService;

    /** Constructor Injection */
    @Autowired
    public SaleInfoController(SessionService sessionService, SaleInfoService saleInfoService, DayService dayService){
        this.sessionService = sessionService;
        this.saleInfoService = saleInfoService;
        this.dayService = dayService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String SaleInfoView(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = dayService.getPayColList(dayVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        return "sale/cmmSalePopup/saleInfo/saleInfo";
    }

    /**
     * 매장정보,매출종합내역,결제내역,회원정보 조회
     *
     * @param saleInfoVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  김설아
     * @since   2020. 01. 10.
     */
    @RequestMapping(value = "saleInfo/getSaleDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleDtlList(SaleInfoVO saleInfoVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//        System.out.println("test11111");

        DefaultMap<String> result = saleInfoService.getSaleDtlList(saleInfoVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 신용카드 결재내역 조회
     *
     * @param saleInfoVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  김설아
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "saleInfo/getSaleCardDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleCardDtlList(SaleInfoVO saleInfoVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleInfoService.getSaleCardDtlList(saleInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleInfoVO);
    }

    /**
     * 현금영수증 결재내역 조회
     *
     * @param saleInfoVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  김설아
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "saleInfo/getSaleCashDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleCashDtlList(SaleInfoVO saleInfoVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleInfoService.getSaleCashDtlList(saleInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleInfoVO);
    }

    /**
     * 상품내역 조회
     *
     * @param saleInfoVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  김설아
     * @since   2020. 01. 14.
     */
    @RequestMapping(value = "saleInfo/getSaleProdDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleProdDtlList(SaleInfoVO saleInfoVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = saleInfoService.getSaleProdDtlList(saleInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleInfoVO);
    }
}