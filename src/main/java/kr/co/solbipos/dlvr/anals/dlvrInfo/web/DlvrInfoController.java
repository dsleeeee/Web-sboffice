package kr.co.solbipos.dlvr.anals.dlvrInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.anals.dlvrInfo.service.DlvrInfoService;
import kr.co.solbipos.dlvr.anals.dlvrInfo.service.DlvrInfoVO;
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
@RequestMapping("/dlvr/manage/anals")
public class DlvrInfoController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private final SessionService sessionService;
  private final DlvrInfoService dlvrInfoService;

  @Autowired
  public DlvrInfoController(SessionService sessionService, DlvrInfoService dlvrInfoService) {
    this.sessionService = sessionService;
    this.dlvrInfoService = dlvrInfoService;
  }


  /**
   * 페이지 이동
   *
   * @param request
   * @param response
   * @param model
   */
  @RequestMapping(value = "/dlvrInfo/list.sb", method = RequestMethod.GET)
  public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    return "dlvr/anals/dlvrInfo/dlvrInfo";
  }


  //  /**
//   * 배달지 조회 및 변경
//   *
//   * @param request
//   * @param response
//   * @param model
//   * @return
//   */
  @RequestMapping(value = "/dlvr/getDlvrInfoList.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getDlvrInfoList(DlvrInfoVO dlvrInfoVO, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    List<DefaultMap<Object>> result = dlvrInfoService.getDlvrInfoList(dlvrInfoVO, sessionInfoVO);
    return ReturnUtil.returnListJson(Status.OK, result, dlvrInfoVO);
  }

}
