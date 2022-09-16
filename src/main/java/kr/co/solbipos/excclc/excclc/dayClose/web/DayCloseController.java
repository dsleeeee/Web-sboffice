package kr.co.solbipos.excclc.excclc.dayClose.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.dayClose.service.DayCloseService;
import kr.co.solbipos.excclc.excclc.dayClose.service.DayCloseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : DayCloseController.java
 * @Description : 광운대 > 광운대일마감 > 광운대일마감
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.07  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.07
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping(value = "/excclc/excclc/dayClose/")
public class DayCloseController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DayCloseService dayCloseService;
    private final SessionService sessionService;

    public DayCloseController(DayCloseService dayCloseService, SessionService sessionService) {
        this.dayCloseService = dayCloseService;
        this.sessionService = sessionService;
    }

    /**
     * 매출수기등록 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.09.07
     */
    @RequestMapping(value = "dayClose/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "excclc/excclc/dayClose/dayClose";
    }

    /**
     * 조회
     * @param dayCloseVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.09.08
     */
    @RequestMapping(value = "dayClose/getDayCloseList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayCloseList(DayCloseVO dayCloseVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = dayCloseService.getDayCloseList(dayCloseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 마감데이터수신
     * @param dayCloseVO
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.09.08
     */
    @RequestMapping(value = "dayClose/getDayCloseDtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayCloseDtl(DayCloseVO dayCloseVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = dayCloseService.getDayCloseDtl(dayCloseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 매출수기등록 저장
     *
     * @param dayCloseVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.09.08
     */
    @RequestMapping(value = "/dayClose/saveClose.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveClose(@RequestBody DayCloseVO dayCloseVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dayCloseService.saveClose(dayCloseVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }


    /**
     * 매출수기등록 저장
     *
     * @param dayCloseVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.09.08
     */
    @RequestMapping(value = "/dayClose/closeCancel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result closeCancel(@RequestBody DayCloseVO dayCloseVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = dayCloseService.closeCancel(dayCloseVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
