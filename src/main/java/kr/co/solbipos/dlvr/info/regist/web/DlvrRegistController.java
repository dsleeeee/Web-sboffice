package kr.co.solbipos.dlvr.info.regist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistService;
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistVO;
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

@Controller
@RequestMapping(value = "/dlvr/manage/info/")
public class DlvrRegistController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DlvrRegistService dlvrRegistService;
    private final SessionService sessionService;
    private final MessageService messageService;

    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public DlvrRegistController(DlvrRegistService dlvrRegistService, SessionService sessionService, MessageService messageService,
                            CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.dlvrRegistService = dlvrRegistService;
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 배달지등록 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "dlvrZone/list.sb", method = RequestMethod.GET)
    public String selectList(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String dlvrFirstList = dlvrRegistService.getDlvrManageList(sessionInfoVO);

        model.addAttribute("dlvrFirstList",  dlvrFirstList);
        return "dlvr/info/view/dlvrManage";
    }
    /**
     * 배달지등록
     *
     * @param request
     * @param response
     * @param model
     */
    @ResponseBody
    @RequestMapping(value = "dlvrZone/regist.sb", method = RequestMethod.POST)
    public Result Save(@RequestBody DlvrRegistVO[] dlvrRegistVOs, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        int result = dlvrRegistService.saveDlvrRegistList(dlvrRegistVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 상세조회
     *
     * @param dlvrRegistVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "dlvrZone/dlvrDetail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result dlvrDetailList(DlvrRegistVO dlvrRegistVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = dlvrRegistService.dlvrDetailList(dlvrRegistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 배달지등록 - 중분류
     *
     * @param request
     * @param response
     * @param model
     */
    @ResponseBody
    @RequestMapping(value = "dlvrZone/detailRegist.sb", method = RequestMethod.POST)
    public Result DetailSave(@RequestBody DlvrRegistVO[] dlvrRegistVOs, HttpServletRequest request,
                       HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        int result = dlvrRegistService.saveDlvrDetailRegistList(dlvrRegistVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
