package kr.co.solbipos.membr.info.excelUpload.web;

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
import kr.co.solbipos.dlvr.info.regist.service.DlvrRegistService;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadVO;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadService;
import kr.co.solbipos.membr.info.regist.enums.WeddingYn;
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
@RequestMapping("/membr/info/upload")
public class MemberExcelUploadController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private final SessionService sessionService;
  private final MemberExcelUploadService memberExcelUploadService;
  private final MessageService messageService;
  private final RegistService registService;
  private final DlvrRegistService dlvrRegistService;
  private final CmmCodeUtil cmmCodeUtil;
  private final CmmEnvUtil cmmEnvUtil;

  @Autowired
  public MemberExcelUploadController(MemberExcelUploadService memberExcelUploadService, RegistService registService, DlvrRegistService dlvrRegistService, SessionService sessionService, MessageService messageService,
                                     CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
    this.memberExcelUploadService = memberExcelUploadService;
    this.dlvrRegistService = dlvrRegistService;
    this.registService = registService;
    this.messageService = messageService;
    this.cmmCodeUtil = cmmCodeUtil;
    this.cmmEnvUtil = cmmEnvUtil;
    this.sessionService = sessionService;
  }

  /**
   * 페이지 이동
   *
   * @param request
   * @param response
   * @param model
   */
  @RequestMapping(value = "/upload/list.sb", method = RequestMethod.GET)
  public String registList(RegistVO registVO , HttpServletRequest request, HttpServletResponse response, Model model) {

    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    // 등록 매장 조회
    List regstrStoreList = registService.getRegistStore(sessionInfoVO);
    // 등록 매장
    String regstrStoreListAll = cmmCodeUtil.assmblObj(regstrStoreList, "name", "value", UseYn.N);

    // 회원등급 리스트 조회
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

    List dlvrLzoneList = registService.getLzoneList(registVO, sessionInfoVO);
    String dlvrLzoneListAll = cmmCodeUtil.assmblObj(dlvrLzoneList, "name", "value", UseYn.SELECT);

    model.addAttribute("dlvrLzoneList", dlvrLzoneListAll);
    model.addAttribute("regstrStoreList", regstrStoreListAll);
    model.addAttribute("memberClassList", membrClassListAll);
    model.addAttribute("defaultStoreCd", defaultStoreCd);
    return "membr/info/view/memberExcelUpload";
  }

  //  /**
//   * 회원 엑셀 조회
//   *
//   * @param request
//   * @param response
//   * @param model
//   * @return
//   */
  @RequestMapping(value = "/excel/getMemberExcelList.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getMemberExcelList(MemberExcelUploadVO memberExcelUploadVO, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    List<DefaultMap<Object>> result = memberExcelUploadService.getMemberExcelList(memberExcelUploadVO, sessionInfoVO);
    return ReturnUtil.returnListJson(Status.OK, result, memberExcelUploadVO);
  }

    /**
   * 회원 엑셀 저장
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/excel/memberExcelSave.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result memberExcelSave(@RequestBody MemberExcelUploadVO[] memberExcelUploadVOs, RegistVO registVO, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    int result = memberExcelUploadService.memberExcelSave(memberExcelUploadVOs, registVO, sessionInfoVO);
    return returnJson(Status.OK, result);
  }
}
