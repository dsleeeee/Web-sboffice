package kr.co.solbipos.membr.info.grade.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.grade.service.MemberClassService;
import kr.co.solbipos.membr.info.grade.service.MembrClassPointVO;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import kr.co.solbipos.membr.info.regist.validate.RegistDelete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnJsonBindingFieldError;

/**
 * @Class Name : GradeController.java
 * @Description : 회원관리 > 회원정보 > 회원등급설정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.06.09  이재영      최초생성
 *
 */
@Controller
@RequestMapping(value = "/membr/info/grade/")
public class MemberClassController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MemberClassService classService;
    private final RegistService registService;
    private final SessionService sessionService;
    private final MessageService messageService;

    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public MemberClassController(RegistService registService, MemberClassService classService, SessionService sessionService, MessageService messageService,
                                 CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.registService = registService;
        this.classService = classService;
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
    @RequestMapping(value = "grade/list.sb", method = RequestMethod.GET)
    public String memberClassController(MembrClassVO membrClassVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 회원등급 관리구분
        String membrClassManageFg = CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1237"), "1");
        model.addAttribute("membrClassManageFg", membrClassManageFg);

        // 회원등급 조회
        membrClassVO.setMembrClassManageFg(membrClassManageFg);
        String result = classService.getMemberClassList(membrClassVO, sessionInfoVO);

        // 회원등급 리스트 조회
        List membrClassList = registService.getMembrClassList(sessionInfoVO);
        String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.N);

        model.addAttribute("result",  result);
        model.addAttribute("membrClassList",  membrClassListAll);
        return "membr/info/view/memberClass";
    }
    /**
     * 회원등급설정 조회
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "view/getMemberClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberClassList(MembrClassVO membrClassVO, HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> result = classService.getMemberClassGridList(membrClassVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 회원등급 상세조회
     *
     * @param membrClassVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "view/getMemberClassDetail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberClasslist(MembrClassVO membrClassVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = classService.getMember(membrClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 등급 저장
     * @param membrClassVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     *
     */
    @ResponseBody
    @RequestMapping(value = "grade/classRegist.sb", method = RequestMethod.POST)
    public Result classRegist(@RequestBody MembrClassVO membrClassVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int result = classService.classInfoChk(membrClassVO,sessionInfoVO);
        
        membrClassVO.setOrgnFg(sessionInfoVO.getOrgnFg().toString());
        DefaultMap<Object> status = classService.getMember(membrClassVO, sessionInfoVO);

        if(result == 0) {
            status.put("data", result);
            status.put("message", messageService.get("grade.membr.deflt.yn.fail"));
            return returnJson(Status.OK, status);
        } else {
            status.put("data", result);
            status.put("message", messageService.get("cmm.saveSucc"));
            return returnJson(Status.OK, status);
        }
    }

    /**
     * 회원정보 삭제 체크
     *
     * @param membrClassVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "grade/removeChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result classRemoveChk(@RequestBody  MembrClassVO[] membrClassVOs, BindingResult bindingResult,
                                 HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> resultMap = classService.deleteClassInfoChk(membrClassVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, resultMap);
    }

    /**
     * 회원정보 삭제
     *
     * @param membrClassVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "grade/remove.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result classRemove(@Validated(RegistDelete.class) @RequestBody  MembrClassVO[] membrClassVOs, BindingResult bindingResult,
                             HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        int result = classService.deleteClassInfo(membrClassVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 등급포인트 적립 저장
     * @param membrClassPointVOs
     * @param   request
     * @param   response
     * @param   model
     * @return
     *
     */
    @ResponseBody
    @RequestMapping(value = "grade/getMemberClassPointSave.sb", method = RequestMethod.POST)
    public Result memberClassSave(@RequestBody MembrClassPointVO[] membrClassPointVOs, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        int result = classService.saveClassPointList(membrClassPointVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 등급포인트 적립 저장
     * @param membrClassPointVOs
     * @param   request
     * @param   response
     * @param   model
     * @return
     *
     */
    @ResponseBody
    @RequestMapping(value = "grade/getMemberClassPointDel.sb", method = RequestMethod.POST)
    public Result getMemberClassPointDel(@RequestBody MembrClassPointVO[] membrClassPointVOs, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        int result = classService.getMemberClassPointDel(membrClassPointVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}