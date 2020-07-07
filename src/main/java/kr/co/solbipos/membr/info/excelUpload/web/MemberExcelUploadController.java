package kr.co.solbipos.membr.info.excelUpload.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadVO;
import kr.co.solbipos.membr.info.excelUpload.service.MemberExcelUploadService;
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


}
