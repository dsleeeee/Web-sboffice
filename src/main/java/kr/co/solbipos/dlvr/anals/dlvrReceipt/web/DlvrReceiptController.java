package kr.co.solbipos.dlvr.anals.dlvrReceipt.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.anals.dlvrReceipt.service.DlvrReceiptService;
import kr.co.solbipos.dlvr.anals.dlvrReceipt.service.DlvrReceiptVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/dlvr/manage/anals/")
public class DlvrReceiptController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DlvrReceiptService dlvrReceiptService;
    private final SessionService sessionService;
    private final MessageService messageService;

    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public DlvrReceiptController(DlvrReceiptService dlvrReceiptService, SessionService sessionService, MessageService messageService,
                                CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.dlvrReceiptService = dlvrReceiptService;
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 배달지별 내역 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "dlvrZone/list.sb", method = RequestMethod.GET)
    public String selectList(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        return "dlvr/anals/dlvrReceipt/dlvrReceipt";
    }

    /**
     * 회원정보 리스트 조회
     *
     * @param dlvrReceiptVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "dlvrZone/getDlvrReceiptList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrReceiptList(DlvrReceiptVO dlvrReceiptVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = dlvrReceiptService.getDlvrReceiptList(dlvrReceiptVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dlvrReceiptVO);
    }

    /**
     * 회원정보 리스트 조회
     *
     * @param dlvrReceiptVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "dlvrZone/getDlvrReceiptDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDlvrReceiptDetailList(DlvrReceiptVO dlvrReceiptVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> result = dlvrReceiptService.getDlvrReceiptDetailList(dlvrReceiptVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, result, dlvrReceiptVO);
    }
}
