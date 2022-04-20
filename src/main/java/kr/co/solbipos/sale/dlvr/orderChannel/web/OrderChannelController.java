package kr.co.solbipos.sale.dlvr.orderChannel.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.orderChannelSale.service.MobileOrderChannelSaleVO;
import kr.co.solbipos.sale.dlvr.orderChannel.service.OrderChannelService;
import kr.co.solbipos.sale.dlvr.orderChannel.service.OrderChannelVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

@Controller
@RequestMapping("/sale/dlvr/orderChannel")
public class OrderChannelController {

    private final SessionService sessionService;
    private final OrderChannelService orderChannelService;

    public OrderChannelController(SessionService sessionService, OrderChannelService orderChannelService){
        this.sessionService = sessionService;
        this.orderChannelService = orderChannelService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  이다솜
     * @since   2021.09.01
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        OrderChannelVO orderChannelVO = new OrderChannelVO();

        // 결제수단 조회
        List<DefaultMap<String>> payColList = orderChannelService.getPayColList(orderChannelVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        // 할인구분 조회
        List<DefaultMap<String>> dcColList = orderChannelService.getDcColList(orderChannelVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String dcCol = "";
        for(int i=0; i < dcColList.size(); i++) {
            dcCol += (dcCol.equals("") ? "" : ",") + dcColList.get(i).getStr("dcCd");
        }
        model.addAttribute("dcColList", dcColList);
        model.addAttribute("dcCol", dcCol);

        // 객수 조회
        List<DefaultMap<String>> guestColList = orderChannelService.getGuestColList(orderChannelVO, sessionInfoVO);

        // 객수 코드를 , 로 연결하는 문자열 생성
        String guestCol = "";
        for(int i=0; i < guestColList.size(); i++) {
            guestCol += (guestCol.equals("") ? "" : ",") + guestColList.get(i).getStr("guestCd");
        }
        model.addAttribute("guestColList", guestColList);
        model.addAttribute("guestCol", guestCol);

        // 주문채널 구분자 조회
        List<DefaultMap<String>> dlvrInFgColList = orderChannelService.getDlvrInFgColList(orderChannelVO, sessionInfoVO);

        // 주문채널 코드를 , 로 연결하는 문자열 생성
        String dlvrInFgCol = "";
        for(int i=0; i < dlvrInFgColList.size(); i++) {
            dlvrInFgCol += (dlvrInFgCol.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFg");
        }
        model.addAttribute("dlvrInFgColList", dlvrInFgColList);
        model.addAttribute("dlvrInFgCol", dlvrInFgCol);

        return "sale/dlvr/orderChannel/orderChannel";
    }

    /**
     * 주문채널별현황 - 기간별 탭 조회
     * @param orderChannelVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  이다솜
     * @since   2021.09.02
     */
    @RequestMapping(value = "/getOrderChannelPeriodList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderChannelPeriodList(OrderChannelVO orderChannelVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderChannelService.getOrderChannelPeriodList(orderChannelVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

    /**
     * 주문채널별현황 - 일별 탭 조회
     * @param orderChannelVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  이다솜
     * @since   2021.09.02
     */
    @RequestMapping(value = "/getOrderChannelDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderChannelDayList(OrderChannelVO orderChannelVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderChannelService.getOrderChannelDayList(orderChannelVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }



    /**
     * 주문채널별현황 - 일별 탭 상세 조회
     * @param orderChannelVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.04.19
     */
    @RequestMapping(value = "/getOrderChannelDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderChannelDtlList(OrderChannelVO orderChannelVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderChannelService.getOrderChannelDtlList(orderChannelVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

    /**
     * 주문채널별현황 - 월별 탭 조회
     * @param orderChannelVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  이다솜
     * @since   2021.09.02
     */
    @RequestMapping(value = "/getOrderChannelMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderChannelMonthList(OrderChannelVO orderChannelVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderChannelService.getOrderChannelMonthList(orderChannelVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

}
