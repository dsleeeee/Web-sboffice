package kr.co.solbipos.mobile.sale.status.voucherNo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.voucherNo.service.MobileVoucherNoService;
import kr.co.solbipos.mobile.sale.status.voucherNo.service.MobileVoucherNoVO;
import kr.co.solbipos.mobile.sale.status.weekSale.service.MobileWeekSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : MobileVoucherNoController.java
 * @Description : (모바일) 매출현황 > 최종교환권번호
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.11.16  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.11.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/mobile/sale/status/mobileVoucherNo")
public class MobileVoucherNoController {

    private final SessionService sessionService;
    private final MobileVoucherNoService mobileVoucherNoService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileVoucherNoController(SessionService sessionService, MobileVoucherNoService mobileVoucherNoService) {
        this.sessionService = sessionService;
        this.mobileVoucherNoService = mobileVoucherNoService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String mobileVoucherNoView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "mobile/sale/status/voucherNo/mobileVoucherNo";
    }

    /**
     * 최종교환번호 조회
     *
     * @param mobileVoucherNoVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  이다솜
     * @since   2021. 11. 17.
     */
    @RequestMapping(value = "/getVoucherNo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVoucherNo(MobileVoucherNoVO mobileVoucherNoVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = mobileVoucherNoService.getVoucherNo(mobileVoucherNoVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
