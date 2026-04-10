package kr.co.solbipos.sys.stats.userWebHist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.template.RedisCustomTemplate;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.stats.userWebHist.service.UserWebHistService;
import kr.co.solbipos.sys.stats.userWebHist.service.UserWebHistVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final RedisCustomTemplate<String, SessionInfoVO> redisCustomTemplate;

    /**
     * Constructor Injection
     */
    public UserWebHistController(SessionService sessionService, UserWebHistService userWebHistService, RedisCustomTemplate<String, SessionInfoVO> redisCustomTemplate) {
        this.sessionService = sessionService;
        this.userWebHistService = userWebHistService;
        this.redisCustomTemplate = redisCustomTemplate;
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

    /**
     * 삭제 비밀번호 확인 후 삭제
     *
     * @param   userWebHistVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author  김유승
     * @since   2026. 04. 02.
     */
    @RequestMapping(value = "/userWebHist/getDeleteSession.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDeleteSession(@RequestBody UserWebHistVO userWebHistVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = "";

        String key = redisCustomTemplate.makeKey(userWebHistVO.getSessionId());

        // 삭제 전 존재 여부
        Boolean beforeExists = redisCustomTemplate.hasKey(key);

        System.out.println("세션정보" + userWebHistVO.getSessionId());
        System.out.println("존재여부" + beforeExists);

        if(userWebHistVO.getPassword().equals("00001")){
            if(beforeExists == true) {
                sessionService.deleteSessionInfo(userWebHistVO.getSessionId());
                Boolean afterExists = redisCustomTemplate.hasKey(key);
                if (afterExists == false) {
                    result = "true";   // 정상 삭제
                } else {
                    result = "delFail";  // 삭제 실패
                }
            }else{
                result = "delPrev"; // 이미 없음
            }
        }else{
            result = "chkPw"; // 비밀번호 오류
        }

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 사용자 아이디 일시정지
     *
     * @param   userWebHistVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author  김유승
     * @since   2026. 04. 03.
     */
    @RequestMapping(value = "/userWebHist/getPauseUserId.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPauseUserId(@RequestBody UserWebHistVO userWebHistVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        if(userWebHistVO.getPassword().equals("00001")) {
            result = userWebHistService.getPauseUserId(userWebHistVO, sessionInfoVO);
        }else{
            result = -1;
        }

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * 사용자 아이디 일시정지 해제
     *
     * @param   userWebHistVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author  김유승
     * @since   2026. 04. 03.
     */
    @RequestMapping(value = "/userWebHist/getResumeUserId.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getResumeUserId(@RequestBody UserWebHistVO userWebHistVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        if(userWebHistVO.getPassword().equals("00001")) {
            result = userWebHistService.getResumeUserId(userWebHistVO, sessionInfoVO);
        }else{
            result = -1;
        }

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
