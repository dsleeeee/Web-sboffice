package kr.co.solbipos.excclc.excclc.depositDdc.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistVO;
import kr.co.solbipos.excclc.excclc.depositDdc.service.DepositDdcService;
import kr.co.solbipos.excclc.excclc.depositDdc.service.DepositDdcVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Class Name : DepositDdcController.java
 * @Description : 수불관리 > 수주관리 > 입금/공제관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.20  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.04.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/excclc/excclc/depositDdc/")
public class DepositDdcController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DepositDdcService depositDdcService;
    private final SessionService sessionService;
    private final CmmCodeUtil cmmCodeUtil;

    @Autowired
    public DepositDdcController(DepositDdcService depositDdcService, SessionService sessionService, CmmCodeUtil cmmCodeUtil) {
        this.depositDdcService = depositDdcService;
        this.sessionService = sessionService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 입금/공제관리 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2022.04.20
     */
    @RequestMapping(value = "view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "excclc/excclc/depositDdc/depositDdcTab";
    }

    /**
     * 매장별집계 탭 - 매장별집계 리스트 조회
     * @param depositDdcVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2022.04.20
     */
    @RequestMapping(value = "getStoreTotalList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreTotalList(DepositDdcVO depositDdcVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = depositDdcService.getStoreTotalList(depositDdcVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, depositDdcVO);
    }

    /**
     * 매장별집계 탭 - 상세내역 리스트 조회
     * @param depositDdcVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2022.04.20
     */
    @RequestMapping(value = "getStoreTotalDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreTotalDtlList(DepositDdcVO depositDdcVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = depositDdcService.getStoreTotalDtlList(depositDdcVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, depositDdcVO);
    }

    /**
     * 매장별집계 탭 - 입금/기타공제 계정 콤보박스 데이터 조회
     * @param depositDdcVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getMoneyFgCombo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMoneyFgCombo(DepositDdcVO depositDdcVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = depositDdcService.getMoneyFgCombo(depositDdcVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, depositDdcVO);
    }

    /**
     * 매장별집계 탭 - 상세내역 리스트의 입금/기타공제 단일건 저장
     * @param depositDdcVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveDepositDdc.sb", method = RequestMethod.POST)
    public Result saveDepositDdc(@RequestBody DepositDdcVO depositDdcVO, HttpServletRequest request,
                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int result = depositDdcService.saveDepositDdc(depositDdcVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장별집계 탭 - 상세내역 리스트의 입금/기타공제 단일건 조회
     * @param depositDdcVO
     * @param request
     * @param response
     * @param model
     * @author  이다솜
     * @since   2022.04.20
     */
    @RequestMapping(value = "getDepositDdc.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDepositDdc(DepositDdcVO depositDdcVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = depositDdcService.getDepositDdc(depositDdcVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

}
