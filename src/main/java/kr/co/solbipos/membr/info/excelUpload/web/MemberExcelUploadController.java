package kr.co.solbipos.membr.info.excelUpload.web;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadService;
import kr.co.solbipos.membr.info.point.service.MemberPointVO;
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

@Controller
@RequestMapping("/membr/info/upload")
public class MemberExcelUploadController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private final SessionService sessionService;
  private final MemberExcelUploadService memberExcelUploadService;

  @Autowired
  public MemberExcelUploadController(MemberExcelUploadService memberExcelUploadService, SessionService sessionService) {
    this.memberExcelUploadService = memberExcelUploadService;
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
  public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

    return "membr/info/view/memberExcelUpload";
  }

//  /**
//   * 회원 포인트 조정
//   *
//   * @param request
//   * @param response
//   * @param model
//   * @return
//   */
//  @RequestMapping(value = "/point/adjustAll.sb", method = RequestMethod.POST)
//  @ResponseBody
//  public Result memberPointList(MemberPointVO memberPointVO, HttpServletRequest request, HttpServletResponse response, Model model) {
//    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//
//    int result = memberPointService.getMemberPointSave(memberPointVO, sessionInfoVO, request);
//
//    return ReturnUtil.returnListJson(Status.OK, result, memberPointVO);
//  }

}
