package kr.co.solbipos.iostock.loan.loanInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.loan.loanInfo.service.LoanInfoService;
import kr.co.solbipos.iostock.loan.loanInfo.service.LoanInfoVO;
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
 * @Class Name : LoanController.java
 * @Description : 수불관리 > 주문관리 > 여신현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.15  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 10.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/iostock/loan/loanInfo")
public class LoanInfoController {
    private final SessionService sessionService;
    private final LoanInfoService loanInfoService;

    @Autowired
    public LoanInfoController(SessionService sessionService, LoanInfoService loanInfoService) {
        this.sessionService = sessionService;
        this.loanInfoService = loanInfoService;
    }

    /**
     * 여신현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 10. 15.
     */
    @RequestMapping(value = "/loanInfo/view.sb", method = RequestMethod.GET)
    public String loanInfoView(HttpServletRequest request, HttpServletResponse response,
        Model model) {
        return "iostock/loan/loanInfo/loanInfo";
    }

    /**
     * 여신현황 - 조회
     * @param   request
     * @param   response
     * @param   loanInfoVO
     * @param   model
     * @return  Result
     * @author  안동관
     * @since   2018. 10. 15.
     */

    @RequestMapping(value = "/loanInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLoanInfoList(HttpServletRequest request, HttpServletResponse response,
        LoanInfoVO loanInfoVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        loanInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        loanInfoVO.setStoreCd(sessionInfoVO.getStoreCd());

        List<DefaultMap<String>> list = loanInfoService.getLoanInfoList(loanInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, loanInfoVO);
    }
}
