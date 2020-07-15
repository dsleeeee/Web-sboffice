package kr.co.solbipos.membr.anals.incln.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.incln.service.InclnService;
import kr.co.solbipos.membr.anals.incln.service.InclnVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @author 두어시스템 개발본부 백엔드 Daniel
 * @version 1.0
 * @Class Name : InclnController.java
 * @Description : 회원관리 > 회원분석 > 구매성향 분석
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.01  Daniel      최초생성
 * @Copyright (C) by DOASYS CORP. All right reserved.
 * @see
 * @since 2020.07.01
 */
@Controller
@RequestMapping(value = "/membr/anals/incln")
public class InclnController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private final InclnService inclnService;
  private final SessionService sessionService;

  @Autowired
  public InclnController(InclnService inclnService, SessionService sessionService) {
    this.inclnService = inclnService;
    this.sessionService = sessionService;
  }

  /**
   * 페이지 이동
   *
   * @param request
   * @param response
   * @param model
   */
  @RequestMapping(value = "/incln/list.sb", method = RequestMethod.GET)
  public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    return "membr/anals/incln/incln";
  }

  @RequestMapping(value = "/inclin/getInclinList.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getInclinList(InclnVO inclinVO, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    List<DefaultMap<Object>> result = inclnService.getInclnList(inclinVO, sessionInfoVO);

    System.out.println("+++++++++++++++++++++++++++++++++++++++");
    System.out.println("+++++++++++++++++++++++++++++++++++++++");
    System.out.println(result);
    System.out.println("+++++++++++++++++++++++++++++++++++++++");
    System.out.println("+++++++++++++++++++++++++++++++++++++++");

    return ReturnUtil.returnListJson(Status.OK, result, inclinVO);
  }
}
