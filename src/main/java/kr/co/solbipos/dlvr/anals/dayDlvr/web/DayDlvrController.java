package kr.co.solbipos.dlvr.anals.dayDlvr.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.dlvr.anals.dayDlvr.service.DayDlvrService;
import kr.co.solbipos.dlvr.anals.dayDlvr.service.DayDlvrVO;
import kr.co.solbipos.membr.info.dlvr.service.DlvrVO;
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
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/dlvr/manage/anals")
public class DayDlvrController {
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private final SessionService sessionService;
  private final DayDlvrService dayDlvrService;

  @Autowired
  public DayDlvrController(SessionService sessionService, DayDlvrService dayDlvrService) {
    this.sessionService = sessionService;
    this.dayDlvrService = dayDlvrService;
  }

  /**
   * 페이지 이동
   *
   * @param request
   * @param response
   * @param model
   */
  @RequestMapping(value = "/dayDlvr/list.sb", method = RequestMethod.GET)
  public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    return "dlvr/anals/dayDlvr/dayDlvrList";
  }

  @RequestMapping(value = "/dayDlvr/getDayDlvrList.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getDayDlvrSaleList(DayDlvrVO dayDlvrVO, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    HashMap<String, Object> result = new HashMap<>();

    List<DefaultMap<Object>> DayDlvrSale = dayDlvrService.getDayDlvrSaleList(dayDlvrVO, sessionInfoVO);
//    List<DefaultMap<Object>> DayNonDlvrSale = dayDlvrService.getDayNonDlvrSaleList(dayDlvrVO, sessionInfoVO);

//    DayDlvrSale.addAll(DayNonDlvrSale);

    return ReturnUtil.returnListJson(Status.OK, DayDlvrSale, dayDlvrVO);
  }
}
