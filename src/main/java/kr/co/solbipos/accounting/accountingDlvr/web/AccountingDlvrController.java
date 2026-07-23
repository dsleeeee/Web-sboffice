package kr.co.solbipos.accounting.accountingDlvr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.accounting.accountingDlvr.service.AccountingDlvrService;
import kr.co.solbipos.accounting.accountingDlvr.service.AccountingDlvrVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : AccountingDlvrController.java
 * @Description : 벤슨 > 회계관리 > 배달비 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.16  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/accounting/accountingDlvr")
public class AccountingDlvrController {

    private final SessionService sessionService;
    private final AccountingDlvrService accountingDlvrService;

    public AccountingDlvrController(SessionService sessionService, AccountingDlvrService accountingDlvrService) {
        this.sessionService = sessionService;
        this.accountingDlvrService = accountingDlvrService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/accountingDlvr/view.sb", method = RequestMethod.GET)
    public String accountingDlvrView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "accounting/accountingDlvr/accountingDlvr";
    }

    /**
     * 배달비현황 - 일별 탭 조회
     *
     * @param   accountingDlvrVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026.07.13
     */
    @RequestMapping(value = "/accountingDlvrDay/getAccountingDlvrDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAccountingDlvrDayList(HttpServletRequest request, HttpServletResponse response,
                                           Model model, AccountingDlvrVO accountingDlvrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<Object>> list = accountingDlvrService.getAccountingDlvrDayList(accountingDlvrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, accountingDlvrVO);
    }

    /**
     * 배달비현황 - 일별 탭 엑셀 조회
     *
     * @param   accountingDlvrVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026.07.13
     */
    @RequestMapping(value = "/accountingDlvrDay/getAccountingDlvrDayExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAccountingDlvrDayExcelList(HttpServletRequest request, HttpServletResponse response,
                                                Model model, AccountingDlvrVO accountingDlvrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<Object>> list = accountingDlvrService.getAccountingDlvrDayExcelList(accountingDlvrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, accountingDlvrVO);
    }

    /**
     * 배달비현황 - 월별 탭 조회
     *
     * @param   accountingDlvrVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026.07.13
     */
    @RequestMapping(value = "/accountingDlvrMonth/getAccountingDlvrMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAccountingDlvrMonthList(HttpServletRequest request, HttpServletResponse response,
                                             Model model, AccountingDlvrVO accountingDlvrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<Object>> list = accountingDlvrService.getAccountingDlvrMonthList(accountingDlvrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, accountingDlvrVO);
    }

    /**
     * 배달비현황 - 월별 탭 엑셀 조회
     *
     * @param   accountingDlvrVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026.07.13
     */
    @RequestMapping(value = "/accountingDlvrMonth/getAccountingDlvrMonthExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAccountingDlvrMonthExcelList(HttpServletRequest request, HttpServletResponse response,
                                                  Model model, AccountingDlvrVO accountingDlvrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<Object>> list = accountingDlvrService.getAccountingDlvrMonthExcelList(accountingDlvrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, accountingDlvrVO);
    }
}
