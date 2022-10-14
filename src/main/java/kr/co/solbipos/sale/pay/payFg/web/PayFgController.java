package kr.co.solbipos.sale.pay.payFg.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.pay.payFg.service.PayFgService;
import kr.co.solbipos.sale.pay.payFg.service.PayFgVO;
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
 * @Class Name : PayFgController.java
 * @Description : 맘스터치 > 결제수단별 매출 > 결제수단별 일 매출현황
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
@RequestMapping("/sale/pay/payFg")
public class PayFgController {

    private final SessionService sessionService;
    private final PayFgService payFgService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PayFgController(SessionService sessionService, PayFgService payFgService) {
        this.sessionService = sessionService;
        this.payFgService = payFgService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/payFg/list.sb", method = RequestMethod.GET)
    public String payFgView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/pay/payFg/payFg";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payFgVO
     * @return  String
     * @author  권지현
     * @since   2022.10.13
     */
    @RequestMapping(value = "/payFg/getPayFgList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayFgList(HttpServletRequest request, HttpServletResponse response, Model model, PayFgVO payFgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = payFgService.getPayFgList(payFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payFgVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   payFgVO
     * @return  String
     * @author  권지현
     * @since   2022.10.13
     */
    @RequestMapping(value = "/payFg/getPayFgExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPayFgExcelList(HttpServletRequest request, HttpServletResponse response, Model model, PayFgVO payFgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = payFgService.getPayFgExcelList(payFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, payFgVO);
    }

}