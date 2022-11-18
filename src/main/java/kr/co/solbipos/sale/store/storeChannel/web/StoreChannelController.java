package kr.co.solbipos.sale.store.storeChannel.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelService;
import kr.co.solbipos.sale.store.storeChannel.service.StoreChannelVO;
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
 * @Class Name : StoreChannelController.java
 * @Description : 맘스터치 > 점포매출 > 채널별 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/store/storeChannel")
public class StoreChannelController {

    private final SessionService sessionService;
    private final StoreChannelService storeChannelService;

    public StoreChannelController(SessionService sessionService, StoreChannelService storeChannelService){
        this.sessionService = sessionService;
        this.storeChannelService = storeChannelService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.10.14
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        StoreChannelVO storeChannelVO = new StoreChannelVO();

        // 결제수단 조회
        List<DefaultMap<String>> payColList = storeChannelService.getPayColList(storeChannelVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        // 할인구분 조회
        List<DefaultMap<String>> dcColList = storeChannelService.getDcColList(storeChannelVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String dcCol = "";
        for(int i=0; i < dcColList.size(); i++) {
            dcCol += (dcCol.equals("") ? "" : ",") + dcColList.get(i).getStr("dcCd");
        }
        model.addAttribute("dcColList", dcColList);
        model.addAttribute("dcCol", dcCol);

        // 객수 조회
        List<DefaultMap<String>> guestColList = storeChannelService.getGuestColList(storeChannelVO, sessionInfoVO);

        // 객수 코드를 , 로 연결하는 문자열 생성
        String guestCol = "";
        for(int i=0; i < guestColList.size(); i++) {
            guestCol += (guestCol.equals("") ? "" : ",") + guestColList.get(i).getStr("guestCd");
        }
        model.addAttribute("guestColList", guestColList);
        model.addAttribute("guestCol", guestCol);

        // 주문채널 구분자 조회
        List<DefaultMap<String>> dlvrInFgColList = storeChannelService.getDlvrInFgColList(storeChannelVO, sessionInfoVO);

        // 주문채널 코드를 , 로 연결하는 문자열 생성
        String dlvrInFgCol = "";
        for(int i=0; i < dlvrInFgColList.size(); i++) {
            dlvrInFgCol += (dlvrInFgCol.equals("") ? "" : ",") + dlvrInFgColList.get(i).getStr("dlvrInFg");
        }
        model.addAttribute("dlvrInFgColList", dlvrInFgColList);
        model.addAttribute("dlvrInFgCol", dlvrInFgCol);

        return "sale/store/storeChannel/storeChannel";
    }

    /**
     * 주문채널별현황 - 기간별 탭 조회
     * @param storeChannelVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.10.14
     */
    @RequestMapping(value = "/getStoreChannelPeriodList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreChannelPeriodList(StoreChannelVO storeChannelVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeChannelService.getStoreChannelPeriodList(storeChannelVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

    /**
     * 주문채널별현황 - 일별 탭 조회
     * @param storeChannelVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.10.14
     */
    @RequestMapping(value = "/getStoreChannelDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreChannelDayList(StoreChannelVO storeChannelVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeChannelService.getStoreChannelDayList(storeChannelVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }



    /**
     * 주문채널별현황 - 일별 탭 상세 조회
     * @param storeChannelVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.10.14
     */
    @RequestMapping(value = "/getStoreChannelDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreChannelDtlList(StoreChannelVO storeChannelVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeChannelService.getStoreChannelDtlList(storeChannelVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

    /**
     * 주문채널별현황 - 월별 탭 조회
     * @param storeChannelVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.10.14
     */
    @RequestMapping(value = "/getStoreChannelMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreChannelMonthList(StoreChannelVO storeChannelVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeChannelService.getStoreChannelMonthList(storeChannelVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

}
