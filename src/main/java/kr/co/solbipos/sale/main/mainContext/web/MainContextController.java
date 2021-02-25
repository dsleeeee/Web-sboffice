package kr.co.solbipos.sale.main.mainContext.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.common.service.main.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : MainContextController.java
 * @Description : 메인페이지 (조회 호출하려고 만듬)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.01.25  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.01.25
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/main/mainContext")
public class MainContextController {
    private final SessionService sessionService;
    private final ContentService contentService;

    @Autowired
    public MainContextController(SessionService sessionService, ContentService contentService) {
        this.sessionService = sessionService;
        this.contentService = contentService;
    }

//    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
//    public String mainContextView(HttpServletRequest request, HttpServletResponse response, Model model) {
//
//        return "sale/main/mainContext/mainContext";
//    }

  /**
     * 메인페이지 - 매출현황 조회 (일별(1주))
     *
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 01. 25.
     */
    @RequestMapping(value = "/mainContext/getSaleWeekList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleWeekList(HttpServletRequest request, HttpServletResponse response,
                                  Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = contentService.getSaleWeekList(sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list);
    }

    /**
     * 메인페이지 - 매출현황 조회 (요일별(1개월))
     *
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 01. 25.
     */
    @RequestMapping(value = "/mainContext/getSaleMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleMonthList(HttpServletRequest request, HttpServletResponse response,
                                  Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = contentService.getSaleMonthList(sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list);
    }

    /**
     * 메인페이지 - 매출현황 조회 (월별(1년))
     *
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 01. 25.
     */
    @RequestMapping(value = "/mainContext/getSaleYearList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleYearList(HttpServletRequest request, HttpServletResponse response,
                                   Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = contentService.getSaleYearList(sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list);
    }

    /**
     * 메인페이지 - 매출 상위 상품 조회 (오늘)
     *
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 01. 25.
     */
    @RequestMapping(value = "/mainContext/getSaleProdDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleProdDayList(HttpServletRequest request, HttpServletResponse response,
                                       Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = contentService.getSaleProdDayList(sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list);
    }

    /**
     * 메인페이지 - 매출 상위 상품 조회 (1주일)
     *
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 01. 25.
     */
    @RequestMapping(value = "/mainContext/getSaleProdWeekList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleProdWeekList(HttpServletRequest request, HttpServletResponse response,
                                     Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = contentService.getSaleProdWeekList(sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list);
    }

    /**
     * 메인페이지 - 매출 상위 상품 조회 (1개월)
     *
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 01. 25.
     */
    @RequestMapping(value = "/mainContext/getSaleProdMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleProdMonthList(HttpServletRequest request, HttpServletResponse response,
                                      Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = contentService.getSaleProdMonthList(sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list);
    }
}