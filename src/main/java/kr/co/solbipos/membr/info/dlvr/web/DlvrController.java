package kr.co.solbipos.membr.info.dlvr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.dlvr.service.DlvrVO;
import kr.co.solbipos.membr.info.dlvr.service.DlvrService;
import kr.co.solbipos.membr.info.grade.service.MembrClassPointVO;
import kr.co.solbipos.membr.info.regist.service.RegistService;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.interfaces.PBEKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

@Controller
@RequestMapping("/membr/info/dlvr")
public class DlvrController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private final SessionService sessionService;
  private final DlvrService dlvrService;
  private final RegistService registService;
  private final CmmCodeUtil cmmCodeUtil;
  private final CmmEnvUtil cmmEnvUtil;

  @Autowired
  public DlvrController(SessionService sessionService, DlvrService dlvrService, RegistService registService, CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
    this.sessionService = sessionService;
    this.dlvrService = dlvrService;
    this.registService = registService;
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
  @RequestMapping(value = "/dlvr/list.sb", method = RequestMethod.GET)
  public String registList(DlvrVO dlvrVO, HttpServletRequest request, HttpServletResponse response, Model model) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    // 회원등급 리스트 조회
    List membrClassList = dlvrService.getMembrClassList(sessionInfoVO);
    String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.N);

    List memberDlvrLzone = dlvrService.getDlvrLzoneList(dlvrVO, sessionInfoVO);
    String memberDlvrLzoneAll = cmmCodeUtil.assmblObj(memberDlvrLzone, "name", "value", UseYn.N);

    LOGGER.debug("membrClassListAll: {}", membrClassListAll);

    model.addAttribute("memberClassList", membrClassListAll);
    model.addAttribute("memberDlvrLzone", memberDlvrLzoneAll);
    return "membr/info/view/dlvr";
  }

  /**
   * 중분류 리스트 조회
   *
   * @param dlvrVO
   * @param request
   * @param response
   * @param model
   * @return
   */
  @RequestMapping(value = "dlvr/getDlvrMzoneList.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getDlvrMzoneList(DlvrVO dlvrVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

    DefaultMap<Object> result = dlvrService.getDlvrMzoneList(dlvrVO, sessionInfoVO);

//        return ReturnUtil.returnListJson(Status.OK, result, registVO);
    return returnJson(Status.OK, result);
  }

  /**
   * 배달주소지
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/dlvr/getDlvrList.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getDlvrList(DlvrVO dlvrVO, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    List<DefaultMap<Object>> result = dlvrService.getDlvrList(dlvrVO, sessionInfoVO);
    return ReturnUtil.returnListJson(Status.OK, result, dlvrVO);
  }
  /**
   * 배달 전화번호
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/dlvr/getDlvrTelList.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getDlvrTelList(DlvrVO dlvrVO, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    List<DefaultMap<Object>> result = dlvrService.getDlvrTelList(dlvrVO, sessionInfoVO);
    return ReturnUtil.returnListJson(Status.OK, result, dlvrVO);
  }
  /**
   * 배달지 저장
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/dlvr/saveDlvr.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result saveDlvr(DlvrVO[] dlvrVOs, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    int result = dlvrService.saveDlvr(dlvrVOs, sessionInfoVO);
    return returnJson(Status.OK, result);
  }

  /**
   * 배달 전화번호 저장
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/dlvr/saveDlvrTel.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result saveDlvrTel(@RequestBody DlvrVO[] dlvrVOs, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    int result = dlvrService.saveDlvrTel(dlvrVOs, sessionInfoVO);
    return returnJson(Status.OK, result);
  }

  /**
   * 배달지 삭제
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/dlvr/deleteDlvr.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result deleteDlvr(@RequestBody DlvrVO[] dlvrVOs, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    int result = dlvrService.deleteDlvr(dlvrVOs, sessionInfoVO);
    return returnJson(Status.OK, result);
  }

  /**
   * 배달 전화번호 삭제
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/dlvr/deleteDlvrTel.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result deleteDlvrTel (@RequestBody DlvrVO[] dlvrVOs, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    int result = dlvrService.deleteDlvrTel(dlvrVOs, sessionInfoVO);
    return returnJson(Status.OK, result);
  }
}
