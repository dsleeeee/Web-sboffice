package kr.co.solbipos.iostock.bonavie.disuseInfoBonavie.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.bonavie.disuseInfoBonavie.service.DisuseInfoBonavieService;
import kr.co.solbipos.iostock.bonavie.disuseInfoBonavie.service.DisuseInfoBonavieVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : DisuseInfoBonavieController.java
 * @Description : 수불관리 > 보나비 > 폐기내역조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.05.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/iostock/bonavie/disuseInfoBonavie")
public class DisuseInfoBonavieController {

    private final SessionService sessionService;
    private final DisuseInfoBonavieService disuseInfoBonavieService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DisuseInfoBonavieController(SessionService sessionService, DisuseInfoBonavieService disuseInfoBonavieService) {
        this.sessionService = sessionService;
        this.disuseInfoBonavieService = disuseInfoBonavieService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/disuseInfoBonavie/list.sb", method = RequestMethod.GET)
    public String disuseInfoBonavieView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "iostock/bonavie/disuseInfoBonavie/disuseInfoBonavie";
    }

    /**
     * 폐기내역조회 - 조회
     *
     * @param disuseInfoBonavieVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 23.
     */
    @RequestMapping(value = "/disuseInfoBonavie/getDisuseInfoBonavieList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDisuseInfoBonavieList(DisuseInfoBonavieVO disuseInfoBonavieVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = disuseInfoBonavieService.getDisuseInfoBonavieList(disuseInfoBonavieVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, disuseInfoBonavieVO);
    }

    /**
     * 폐기내역조회 - 엑셀다운로드 조회
     *
     * @param disuseInfoBonavieVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 05. 23.
     */
    @RequestMapping(value = "/disuseInfoBonavie/getDisuseInfoBonavieExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDisuseInfoBonavieExcelList(DisuseInfoBonavieVO disuseInfoBonavieVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = disuseInfoBonavieService.getDisuseInfoBonavieExcelList(disuseInfoBonavieVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, disuseInfoBonavieVO);
    }
}