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

/**
 * @Class Name : DayDlvrController.java
 * @Description :
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.09  Joshua      최초생성
 *
 * @author
 * @since 2020.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

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

  /**
   * 일자별 배달현황(일자,배달매출,배달외매출)
   *
   * @param request
   */
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


  /**
   * 일자별 배달현황 상세(상품정보)
   *
   * @param request
   */
  @RequestMapping(value = "/dayDlvr/getDaySaleDtlList.sb", method = RequestMethod.POST)
  @ResponseBody
  public Result getDaySaleDtlList(DayDlvrVO dayDlvrVO, HttpServletRequest request) {
    SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
    HashMap<String, Object> result = new HashMap<>();

    List<DefaultMap<Object>> DayDlvrSale = dayDlvrService.getDaySaleDtlList(dayDlvrVO, sessionInfoVO);
//    List<DefaultMap<Object>> DayNonDlvrSale = dayDlvrService.getDayNonDlvrSaleList(dayDlvrVO, sessionInfoVO);

//    DayDlvrSale.addAll(DayNonDlvrSale);

    return ReturnUtil.returnListJson(Status.OK, DayDlvrSale, dayDlvrVO);
  }
}
