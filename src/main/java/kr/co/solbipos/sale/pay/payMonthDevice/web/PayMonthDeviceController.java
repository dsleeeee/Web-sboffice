package kr.co.solbipos.sale.pay.payMonthDevice.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.pay.payMonthDevice.service.PayMonthDeviceService;
import kr.co.solbipos.sale.pay.payMonthDevice.service.PayMonthDeviceVO;
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
 * @Class Name : PayMonthDeviceController.java
 * @Description : 맘스터치 > 결제수단별 매출 > 월별 결제수단 매출(기기별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/pay/payMonthDevice")
public class PayMonthDeviceController {

    private final SessionService sessionService;
    private final PayMonthDeviceService payMonthDeviceService;
    private final DayService dayService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PayMonthDeviceController(SessionService sessionService, PayMonthDeviceService payMonthDeviceService, DayService dayService) {
        this.sessionService = sessionService;
        this.payMonthDeviceService = payMonthDeviceService;
        this.dayService = dayService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/payMonthDevice/list.sb", method = RequestMethod.GET)
    public String payMonthDeviceView(HttpServletRequest request, HttpServletResponse response, Model model) {

        DayVO dayVO = new DayVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = dayService.getPayColAddList(dayVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        return "sale/pay/payMonthDevice/payMonthDevice";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payMonthDeviceVO
     * @return  String
     * @author  권지현
     * @since   2022.10.13
     */
    @RequestMapping(value = "/payMonthDevice/getPayMonthDeviceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayMonthDeviceList(HttpServletRequest request, HttpServletResponse response, Model model, PayMonthDeviceVO payMonthDeviceVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = payMonthDeviceService.getPayMonthDeviceList(payMonthDeviceVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payMonthDeviceVO);
    }

}