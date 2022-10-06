package kr.co.solbipos.sale.today.todayMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.today.todayMoms.service.TodayMomsService;
import kr.co.solbipos.sale.today.todayMoms.service.TodayMomsVO;
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
 * @Class Name : TodayMomsController.java
 * @Description : 맘스터치 > 매출분석 > 당일 매출 리스트
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.29  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/today/todayMoms")
public class TodayMomsController {

    private final SessionService sessionService;
    private final TodayMomsService todayMomsService;

    /**
     * Constructor Injection
     */
    @Autowired
    public TodayMomsController(SessionService sessionService, TodayMomsService todayMomsService) {
        this.sessionService = sessionService;
        this.todayMomsService = todayMomsService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/todayMoms/list.sb", method = RequestMethod.GET)
    public String todayMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/today/todayMoms/todayMoms";
    }

    /**
     * 조회
     *
     * @param todayMomsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.10.06
     */
    @RequestMapping(value = "/todayMoms/getTodayMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodayMomsList(TodayMomsVO todayMomsVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = todayMomsService.getTodayMomsList(todayMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, todayMomsVO);
    }

    /**
     * 현금영수증 승인 조회_엑셀
     *
     * @param todayMomsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.10.06
     */
    @RequestMapping(value = "/todayMoms/getTodayMomsExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodayMomsExcelList(TodayMomsVO todayMomsVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = todayMomsService.getTodayMomsExcelList(todayMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, todayMomsVO);
    }


}