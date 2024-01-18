package kr.co.solbipos.sys.stats.userWebHist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.stats.userWebHist.service.UserWebHistService;
import kr.co.solbipos.sys.stats.userWebHist.service.UserWebHistVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : UserWebHistController.java
 * @Description : 시스템관리 > 통계 > 사용자웹사용이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.15  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.15
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/stats/userWebHist")
public class UserWebHistController {

    private final SessionService sessionService;
    private final UserWebHistService userWebHistService;

    /**
     * Constructor Injection
     */
    public UserWebHistController(SessionService sessionService, UserWebHistService userWebHistService) {
        this.sessionService = sessionService;
        this.userWebHistService = userWebHistService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/userWebHist/list.sb", method = RequestMethod.GET)
    public String webLoginView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/stats/userWebHist/userWebHist";
    }

    /**
     * 사용자웹사용이력 조회
     *
     * @param userWebHistVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 06. 01.
     */
    @RequestMapping(value = "/userWebHist/getUserWebHistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserWebHistList(UserWebHistVO userWebHistVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = userWebHistService.getUserWebHistList(userWebHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, userWebHistVO);
    }
}
