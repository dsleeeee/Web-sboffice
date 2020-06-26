package kr.co.solbipos.membr.info.chgBatch.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.chgBatch.service.ChgBatchService;
import kr.co.solbipos.membr.info.chgBatch.service.ChgBatchVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassPointVO;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
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
@RequestMapping(value = "/membr/info/chgBatch/")
public class ChgBatchController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ChgBatchService chgBatchService;
    private final SessionService sessionService;
    private final MessageService messageService;

    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public ChgBatchController(ChgBatchService chgBatchService, SessionService sessionService, MessageService messageService,
                              CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.chgBatchService = chgBatchService;
        this.sessionService = sessionService;
        this.messageService = messageService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 회원정보조회 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "chgBatch/list.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 등록 매장 조회
//        List regstrStoreList = registService.getRegistStore(sessionInfoVO);
        // 등록 매장 전체 포함
//        String regstrStoreListAll = cmmCodeUtil.assmblObj(regstrStoreList, "name", "value", UseYn.SELECT);
        // 회원등급 리스트 조회
        List membrClassList = chgBatchService.getMembrClassList(sessionInfoVO);
        String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.N);

        model.addAttribute("memberClassList", membrClassListAll);
//        model.addAttribute("membrChgBatchList", membrChgBatchList);

        return "membr/info/view/memberChgBatch";
    }

    /**
     * 회원정보 리스트 조회
     *
     * @param chgBatchVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "chgBatch/getMemberChgBatchList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberChgBatchList(ChgBatchVO chgBatchVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = chgBatchService.getMemberChgBatchList(chgBatchVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, chgBatchVO);
    }
    /**
     * 등급포인트 적립 저장
     * @param chgBatchVOs
     * @param   request
     * @param   response
     * @param   model
     * @return
     *
     */
    @ResponseBody
    @RequestMapping(value = "chgBatch/getMemberChgBatchSave.sb", method = RequestMethod.POST)
    public Result memberClassSave(@RequestBody ChgBatchVO[] chgBatchVOs, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        int result = chgBatchService.saveChgBatchList(chgBatchVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}