package kr.co.solbipos.membr.info.point.web;

import com.sun.xml.internal.ws.addressing.WsaActionUtil;
import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistService;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadVO;
import kr.co.solbipos.membr.info.point.service.MemberPointService;
import kr.co.solbipos.membr.info.point.service.MemberPointVO;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

@Controller
@RequestMapping("/membr/info/point")
public class MemberPointController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private final MemberPointService memberPointService;
  private final SessionService sessionService;
  private final MessageService messageService;
  private final RegistService registService;
  private final CmmCodeUtil cmmCodeUtil;
  private final CmmEnvUtil cmmEnvUtil;

  @Autowired
  public MemberPointController(MemberPointService memberPointService, RegistService registService, SessionService sessionService, MessageService messageService,
                               CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
    this.memberPointService = memberPointService;
    this.sessionService = sessionService;
    this.registService = registService;
    this.messageService = messageService;
    this.cmmCodeUtil = cmmCodeUtil;
    this.cmmEnvUtil = cmmEnvUtil;
  }

  /**
   * 페이지 이동
   *
   * @param request
   * @param response
   * @param model
   */
  @RequestMapping(value = "/point/list.sb", method = RequestMethod.GET)
  public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    // 회원등급 리스트 조회
    List membrClassList = registService.getMembrClassList(sessionInfoVO);

    String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.N);
    model.addAttribute("memberClassList", membrClassListAll);

    return "membr/info/view/memberPoint";
  }

  /**
   * 회원 포인트 조정
   *
   * @param request
   * @param response
   * @param model
   * @return
   */
  @RequestMapping(value = "/point/adjustAll.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result memberPointList(MemberPointVO memberPointVO, HttpServletRequest request, HttpServletResponse response, Model model) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

    int result = memberPointService.getMemberPointSave(memberPointVO, sessionInfoVO, request);

    return ReturnUtil.returnJson(Status.OK, result);
  }

  /**
   * 회원검증 리스트
   *
   * @param memberPointVOs
   * @param request
   * @param response
   * @param model
   * @return
   */
  @RequestMapping(value = "/point/getMemberPointListChk.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getMemberPointListChk(@RequestBody MemberPointVO[] memberPointVOs, MemberPointVO memberPointVO, RegistVO registVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

    List<MemberPointVO> result = memberPointService.getMemberPointListChk(memberPointVOs, registVO, sessionInfoVO);

    return ReturnUtil.returnJson(Status.OK, result);
  }

  /**
   * 회원 포인트조정 저장
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/point/memberPointSave.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result memberPointSave(@RequestBody MemberPointVO[] memberPointVOs, RegistVO registVO, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    int result = memberPointService.memberPointSave(memberPointVOs, registVO, sessionInfoVO);
    return returnJson(Status.OK, result);
  }
}
