package kr.co.solbipos.membr.anals.incln.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.incln.service.InclnService;
import kr.co.solbipos.membr.anals.membrPoint.service.MembrPointVO;
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
@RequestMapping("/membr/anals/incln")
public class InclnController {

  private final SessionService sessionService;
//  private final InclnService inclnService;

  @Autowired
//  public InclnController(SessionService sessionService, InclnService inclnService) {
  public InclnController(SessionService sessionService) {
    this.sessionService = sessionService;
//    this.inclnService = inclnService;
  }

  /**
   * 페이지 이동
   *
   * @param request
   * @param response
   * @param model
   * */
  @RequestMapping(value = "/incln/list.sb", method = RequestMethod.GET)
  public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    System.out.println("-*********************************************-");
    System.out.println("-*********************************************-");
    System.out.println(sessionInfoVO);
    System.out.println("-*********************************************-");
    System.out.println("-*********************************************-");

    return "membr/anals/incln/incln";
  }




}
