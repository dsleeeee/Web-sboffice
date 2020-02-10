package kr.co.solbipos.sale.status.corner.dayOfWeek.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.corner.dayOfWeek.service.CornerDayOfWeekService;
import kr.co.solbipos.sale.status.corner.dayOfWeek.service.CornerDayOfWeekVO;

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
 * @Class Name : CornerDayOfWeekController.java
 * @Description : 매출관리 > 매출현황 > 코너별매출 > 요일별 탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.13  조동훤      최초생성
 *
 * @author 조동훤
 * @since 2020.01.13
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/corner")
public class CornerDayOfWeekController {
    private final SessionService sessionService;
    private final CornerDayOfWeekService cornerDayOfWeekService;

    @Autowired
    public CornerDayOfWeekController(SessionService sessionService, CornerDayOfWeekService cornerDayOfWeekService) {
        this.sessionService = sessionService;
        this.cornerDayOfWeekService = cornerDayOfWeekService;
    }


    /**
     * 코너별매출 일자별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   cornerDayOfWeekVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/dayOfWeek/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCornerDayOfWeekList(HttpServletRequest request, HttpServletResponse response,
        Model model, CornerDayOfWeekVO cornerDayOfWeekVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = cornerDayOfWeekService.getCornerDayOfWeekList(cornerDayOfWeekVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, cornerDayOfWeekVO);
    }
}
