package kr.co.solbipos.excclc.excclc.dailyTable.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.dailyTable.service.DailyTableService;
import kr.co.solbipos.excclc.excclc.dailyTable.service.DailyTableVO;
import kr.co.solbipos.sale.anals.dailyreportnew.service.DailyReportNewVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;


/**
 * @Class Name : DailyTableController.java
 * @Description : 광운대 > 광운대일마감 > 일일일계표
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.15  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/excclc/excclc/dailyTable/")
public class DailyTableController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DailyTableService dailyTableService;
    private final SessionService sessionService;

    public DailyTableController(DailyTableService dailyTableService, SessionService sessionService) {
        this.dailyTableService = dailyTableService;
        this.sessionService = sessionService;
    }

    /**
     * 일일일계표 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.09.15
     */
    @RequestMapping(value = "dailyTable/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "excclc/excclc/dailyTable/dailyTable";
    }

    /**
     * 일일일계표 조회
     * @param   dailyTableVO
     * @param   request
     * @return  kr.co.common.data.structure.Result
     * @author  조현수
     * @since   2020.01.28
     */
    @RequestMapping(value="dailyTable/list.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getDailyTableList(DailyTableVO dailyTableVO, HttpServletRequest request) {
        SessionInfoVO		sessionInfoVO 	= sessionService.getSessionInfo(request);
        Map<String, Object> rtnMap 			= dailyTableService.getDailyTableList(dailyTableVO, sessionInfoVO);

        return returnJson(Status.OK, rtnMap);

    }

}
