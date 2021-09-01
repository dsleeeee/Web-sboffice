package kr.co.solbipos.mobile.sale.status.orderChannelSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.orderChannelSale.service.MobileOrderChannelSaleService;
import kr.co.solbipos.mobile.sale.status.orderChannelSale.service.MobileOrderChannelSaleVO;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleService;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : MobileOrderChannelSaleController.java
 * @Description : (모바일) 매출현황 > 주문채널별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.30  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.08.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/status/orderChannelSale")
public class MobileOrderChannelSaleController {

    private final SessionService sessionService;
    private final MobileOrderChannelSaleService mobileOrderChannelSaleService;
    private final MobileProdSaleService mobileProdSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileOrderChannelSaleController(SessionService sessionService, MobileOrderChannelSaleService mobileOrderChannelSaleService, MobileProdSaleService mobileProdSaleService) {
        this.sessionService = sessionService;
        this.mobileOrderChannelSaleService = mobileOrderChannelSaleService;
        this.mobileProdSaleService = mobileProdSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  이다솜
     * @since   2021.08.31
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String orderChannelSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 주문채널 구분자 조회
        MobileOrderChannelSaleVO mobileOrderChannelSaleVO = new MobileOrderChannelSaleVO();
        List<DefaultMap<String>> dlvrInFgColList = mobileOrderChannelSaleService.getDlvrInFgColList(mobileOrderChannelSaleVO, sessionInfoVO);

        // 주문채널 코드를 , 로 연결하는 문자열 생성
        String dlvrInFgCol = "";
        for(int i=0; i < dlvrInFgColList.size(); i++) {
            dlvrInFgCol += (dlvrInFgCol.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFg");
        }
        model.addAttribute("dlvrInFgColList", dlvrInFgColList);
        model.addAttribute("dlvrInFgCol", dlvrInFgCol);

        // 다중매장조회
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);
        model.addAttribute("multiStoreFg", list.size());

        return "mobile/sale/status/orderChannelSale/mobileOrderChannelSale";
    }

    /**
     * 모바일 매출현황 - 주문채널별 현황 조회
     * @param mobileOrderChannelSaleVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  이다솜
     * @since   2021.08.31
     */
    @RequestMapping(value = "/getOrderChannelSalePayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderChannelSalePayList(MobileOrderChannelSaleVO mobileOrderChannelSaleVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileOrderChannelSaleService.getOrderChannelSalePayList(mobileOrderChannelSaleVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

    /**
     * 모바일 매출현황 - 주문채널 일자별 매출현황 조회
     * @param mobileOrderChannelSaleVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  이다솜
     * @since   2021.08.31
     */
    @RequestMapping(value = "/getOrderChannelSaleDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderChannelSaleDtlList(MobileOrderChannelSaleVO mobileOrderChannelSaleVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileOrderChannelSaleService.getOrderChannelSaleDtlList(mobileOrderChannelSaleVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }
}
