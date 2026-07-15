package kr.co.solbipos.accounting.accountingMain.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.accounting.accountingMain.service.AccountingMainService;
import kr.co.solbipos.accounting.accountingMain.service.AccountingMainVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : AccountingMainController.java
 * @Description : 벤슨 > 회계관리 > 회계관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.13  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/accounting/accountingMain")
public class AccountingMainController {

    private final SessionService sessionService;
    private final AccountingMainService accountingMainService;

    /**
     *  Constructor Injection
     */
    public AccountingMainController(SessionService sessionService, AccountingMainService accountingMainService) {
        this.sessionService = sessionService;
        this.accountingMainService = accountingMainService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/accountingMain/view.sb", method = RequestMethod.GET)
    public String accountingMainView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "accounting/accountingMain/accountingMain";
    }

    /**
     * 일별전송 탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   accountingMainVO
     * @return  String
     * @author  김유승
     * @since   2026.07.14
     */
    @RequestMapping(value = "/acDayTransfer/getAcDayTransferList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcDayTransferList(HttpServletRequest request, HttpServletResponse response,
                                       Model model, AccountingMainVO accountingMainVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = accountingMainService.getAcDayTransferList(accountingMainVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, accountingMainVO);
    }

    /**
     * 월별전송 탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   accountingMainVO
     * @return  String
     * @author  김유승
     * @since   2026.07.14
     */
    @RequestMapping(value = "/acMonthTransfer/getAcMonthTransferList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcMonthTransferList(HttpServletRequest request, HttpServletResponse response,
                                       Model model, AccountingMainVO accountingMainVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = accountingMainService.getAcMonthTransferList(accountingMainVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, accountingMainVO);
    }

    /**
     * 매장별 항목관리 탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   accountingMainVO
     * @return  String
     * @author  김유승
     * @since   2026.07.14
     */
    @RequestMapping(value = "/acStoreOption/getAcStoreOptionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcStoreOptionList(HttpServletRequest request, HttpServletResponse response,
                                         Model model, AccountingMainVO accountingMainVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = accountingMainService.getAcStoreOptionList(accountingMainVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, accountingMainVO);
    }

    /**
     * 매장별 항목관리 탭 - 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   accountingMainVOs
     * @return  String
     * @author  김유승
     * @since   2026.07.14
     */
    @RequestMapping(value = "/acStoreOption/saveAcStoreOption.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAcStoreOption(@RequestBody AccountingMainVO[] accountingMainVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = accountingMainService.saveAcStoreOption(accountingMainVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장별 항목관리 탭 - 삭제
     * @param   request
     * @param   response
     * @param   model
     * @param   accountingMainVOs
     * @return  String
     * @author  김유승
     * @since   2026.07.14
     */
    @RequestMapping(value = "/acStoreOption/delAcStoreOption.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result delAcStoreOption(@RequestBody AccountingMainVO[] accountingMainVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = accountingMainService.delAcStoreOption(accountingMainVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 공통코드관리 탭 - 공통코드(좌측) 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   accountingMainVO
     * @return  String
     * @author  김유승
     * @since   2026.07.14
     */
    @RequestMapping(value = "/acComCode/getAcComCodeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcComCodeList(HttpServletRequest request, HttpServletResponse response,
                                    Model model, AccountingMainVO accountingMainVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = accountingMainService.getAcComCodeList(accountingMainVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, accountingMainVO);
    }

    /**
     * 공통코드관리 탭 - 상세코드(우측) 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   accountingMainVO
     * @return  String
     * @author  김유승
     * @since   2026.07.14
     */
    @RequestMapping(value = "/acComCode/getAcComCodeDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAcComCodeDtlList(HttpServletRequest request, HttpServletResponse response,
                                       Model model, AccountingMainVO accountingMainVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = accountingMainService.getAcComCodeDtlList(accountingMainVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, accountingMainVO);
    }

    /**
     * 공통코드관리 탭 - 공통코드(좌측) 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   accountingMainVOs
     * @return  String
     * @author  김유승
     * @since   2026.07.14
     */
    @RequestMapping(value = "/acComCode/saveAcComCode.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAcComCode(@RequestBody AccountingMainVO[] accountingMainVOs, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = accountingMainService.saveAcComCode(accountingMainVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 공통코드관리 탭 - 상세코드(우측) 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   accountingMainVOs
     * @return  String
     * @author  김유승
     * @since   2026.07.14
     */
    @RequestMapping(value = "/acComCode/saveAcComCodeDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAcComCodeDtl(@RequestBody AccountingMainVO[] accountingMainVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = accountingMainService.saveAcComCodeDtl(accountingMainVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
