package kr.co.solbipos.sale.day.dayDevice.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.dayDevice.service.DayDeviceService;
import kr.co.solbipos.sale.day.dayDevice.service.DayDeviceVO;
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
 * @Class Name : DayDeviceController.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황(기기별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/day/dayDevice")
public class DayDeviceController {

    private final SessionService sessionService;
    private final DayDeviceService dayDeviceService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayDeviceController(SessionService sessionService, DayDeviceService dayDeviceService) {
        this.sessionService = sessionService;
        this.dayDeviceService = dayDeviceService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dayDevice/list.sb", method = RequestMethod.GET)
    public String dayDeviceView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/day/dayDevice/dayDevice";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayDeviceVO
     * @return  String
     * @author  권지현
     * @since   2022.10.06
     */
    @RequestMapping(value = "/dayDevice/getDayDeviceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayDeviceList(HttpServletRequest request, HttpServletResponse response, Model model, DayDeviceVO dayDeviceVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayDeviceService.getDayDeviceList(dayDeviceVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayDeviceVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayDeviceVO
     * @return  String
     * @author  권지현
     * @since   2022.10.06
     */
    @RequestMapping(value = "/dayDevice/getDayDeviceExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayDeviceExcelList(HttpServletRequest request, HttpServletResponse response, Model model, DayDeviceVO dayDeviceVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayDeviceService.getDayDeviceExcelList(dayDeviceVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayDeviceVO);
    }

}