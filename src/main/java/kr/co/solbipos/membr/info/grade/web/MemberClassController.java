package kr.co.solbipos.membr.info.grade.web;

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
import kr.co.solbipos.base.store.emp.enums.EmpResult;
import kr.co.solbipos.base.store.emp.store.service.StoreEmpVO;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import kr.co.solbipos.membr.info.grade.service.MemberClassService;
import kr.co.solbipos.membr.info.grade.service.MembrClassVO;
import kr.co.solbipos.membr.info.regist.enums.WeddingYn;
import kr.co.solbipos.membr.info.regist.service.MemberMappingVO;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import kr.co.solbipos.membr.info.regist.validate.Regist;
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
import static kr.co.common.utils.spring.StringUtil.convertToJson;

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

//        ReturnUtil returnUtil = new ReturnUtil();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 회원등급 리스트 조회
        String result = classService.getMemberClassList(sessionInfoVO);

        List membrClassList = registService.getMembrClassList(sessionInfoVO);
        String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.N);

        // 본사일 경우 해당 본사의 기본매장(코드)을 조회 해야 함.
        // [보나비]의 경우 기본매장코드를 사용하여
        // 회원등록 매장이 기본매장일 경우 후불회원 적용매장을 등록한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        model.addAttribute("memberClassList", membrClassListAll);
        model.addAttribute("result",  result);
        model.addAttribute("defaultStoreCd", defaultStoreCd);

        return "membr/info/view/memberClass";
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
        DefaultMap<Object> result = classService.classInfoChk(membrClassVO,sessionInfoVO);

        return returnJson(Status.OK, result);
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
}
