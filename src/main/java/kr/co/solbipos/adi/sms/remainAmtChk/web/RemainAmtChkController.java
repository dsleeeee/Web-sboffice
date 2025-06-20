package kr.co.solbipos.adi.sms.remainAmtChk.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.sms.remainAmtChk.service.RemainAmtChkService;
import kr.co.solbipos.adi.sms.remainAmtChk.service.RemainAmtChkVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.salePriceResve.service.SalePriceResveVO;
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
 * @Class Name : RemainAmtChkController.java
 * @Description : 부가서비스 > SMS분석 > 잔여금액확인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.06.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/remainAmtChk")
public class RemainAmtChkController {

    private final SessionService sessionService;
    private final RemainAmtChkService remainAmtChkService;

    @Autowired
    public RemainAmtChkController(SessionService sessionService, RemainAmtChkService remainAmtChkService) {
        this.sessionService = sessionService;
        this.remainAmtChkService = remainAmtChkService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/remainAmtChk/list.sb", method = RequestMethod.GET)
    public String remainAmtChk(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "adi/sms/remainAmtChk/remainAmtChk";
    }

    /**
     * 잔여금액확인 - 조회
     * @param   request
     * @param   response
     * @param   remainAmtChkVO
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2025. 06. 10.
     */

    @RequestMapping(value = "/remainAmtChk/getRemainAmtChkList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRemainAmtChkList(HttpServletRequest request, HttpServletResponse response,
                                      RemainAmtChkVO remainAmtChkVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = remainAmtChkService.getRemainAmtChkList(remainAmtChkVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, remainAmtChkVO);

    }

    /**
     * 충전/사용내역 팝업 - 조회
     * @param   request
     * @param   remainAmtChkVO
     * @return  Result
     * @author  김유승
     * @since   2025. 06. 10.
     */
    @RequestMapping(value = "/remainAmtHist/getRemainAmtHistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRemainAmtHistList(RemainAmtChkVO remainAmtChkVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = remainAmtChkService.getRemainAmtHistList(remainAmtChkVO, sessionInfoVO);

        return returnListJson(Status.OK, result, remainAmtChkVO);
    }
}
