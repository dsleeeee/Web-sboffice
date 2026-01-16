package kr.co.solbipos.sale.anals.onlineOrder.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.onlineOrder.service.OnlineOrderService;
import kr.co.solbipos.sale.anals.onlineOrder.service.OnlineOrderVO;
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
 * @Class Name  : OnlineOrderController.java
 * @Description : 매출관리 > 매출분석 > 온라인주문확인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.16  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.16
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/sale/anals/onlineOrder")
public class OnlineOrderController {

    private final SessionService sessionService;
    private final OnlineOrderService onlineOrderService;


    /**
     *  Constructor Injection
     */
    public OnlineOrderController(SessionService sessionService, OnlineOrderService onlineOrderService) {
        this.sessionService = sessionService;
        this.onlineOrderService = onlineOrderService;
    }


    /**
     *  페이지 이동
     */
    @RequestMapping(value = "/onlineOrder/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/anals/onlineOrder/onlineOrder";
    }

    /**
     * 온라인주문확인 - 조회
     *
     * @param   onlineOrderVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2024.01.30
     * @return
     */
    @RequestMapping(value = "/onlineOrder/getSearchOnlineOrderList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchOnlineOrderList(OnlineOrderVO onlineOrderVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = onlineOrderService.getSearchOnlineOrderList(onlineOrderVO, sessionInfoVO);

        return returnListJson(Status.OK, list, onlineOrderVO);
    }
}
