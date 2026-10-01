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
import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : UserWebHistController.java
 * @Description : 시스템관리 > 통계 > 사용자웹사용이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.15  김유승      최초생성
 * @ 2026.09.22  김유승      조회정보(조회건수) 팝업 조회 추가
 * @ 2026.09.23  김유승      ③ 메뉴(URL)별 건수 조회 추가
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

    /**
     * [조회정보 팝업] 조회건수 USER_ID 종합 조회
     *   - ETC050 조회건수 탭 ① USER_ID 종합 그리드와 동일 (임계치 OR 조건)
     *
     * @param   userWebHistVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author  김유승
     * @since   2026. 09. 22.
     */
    @RequestMapping(value = "/userWebHist/getUserWebChkCntList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserWebChkCntList(@RequestBody UserWebHistVO userWebHistVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = userWebHistService.getUserWebChkCntList(userWebHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, userWebHistVO);
    }

    /**
     * [조회정보 팝업] 조회건수 USER_ID + IP별 상세 조회
     *   - ETC050 조회건수 탭 ② IP별 상세 그리드와 동일
     *
     * @param   userWebHistVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author  김유승
     * @since   2026. 09. 22.
     */
    @RequestMapping(value = "/userWebHist/getUserWebChkCntIpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserWebChkCntIpList(@RequestBody UserWebHistVO userWebHistVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 대상 USER_ID 미입력 시 빈 목록 반환
        if (userWebHistVO.getIpUserId() == null || userWebHistVO.getIpUserId().trim().isEmpty()) {
            return ReturnUtil.returnListJson(Status.OK, new ArrayList<DefaultMap<Object>>(), userWebHistVO);
        }

        List<DefaultMap<Object>> result = userWebHistService.getUserWebChkCntIpList(userWebHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, userWebHistVO);
    }

    /**
     * [조회정보 팝업] ③ 메뉴(URL)별 건수 조회
     *   - ETC050 조회건수 탭 ③ 메뉴(URL)별 건수와 동일 (USER_ID 1명)
     *   - 웹훅 URLD(전체 사용자) + TB_WB_LOGIN_HIST_CHK(기록 대상) + 현재메뉴 불일치 + 예외 URL 판정
     *
     * @param   userWebHistVO
     * @param   request
     * @param   response
     * @param   model
     * @return
     * @author  김유승
     * @since   2026. 09. 23.
     */
    @RequestMapping(value = "/userWebHist/getUserWebChkCntMenuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserWebChkCntMenuList(@RequestBody UserWebHistVO userWebHistVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 대상 USER_ID 미입력 시 빈 목록 반환
        if (userWebHistVO.getIpUserId() == null || userWebHistVO.getIpUserId().trim().isEmpty()) {
            return ReturnUtil.returnListJson(Status.OK, new ArrayList<DefaultMap<Object>>(), userWebHistVO);
        }

        List<DefaultMap<Object>> result = userWebHistService.getUserWebChkCntMenuList(userWebHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, userWebHistVO);
    }

    /**
     * [조회정보 팝업] ② IP별 상세 0건 시 원천 데이터 진단
     *   - ETC050 조회건수 탭 chkcnt_ip 0건 진단과 동일
     *
     * @author  김유승
     * @since   2026. 09. 28.
     */
    @RequestMapping(value = "/userWebHist/getUserWebChkCntIpDiag.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserWebChkCntIpDiag(@RequestBody UserWebHistVO userWebHistVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        if (userWebHistVO.getIpUserId() == null || userWebHistVO.getIpUserId().trim().isEmpty()) {
            return ReturnUtil.returnJson(Status.OK, new DefaultMap<Object>());
        }

        DefaultMap<Object> result = userWebHistService.getUserWebChkCntIpDiag(userWebHistVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * [조회정보 팝업] USER_ID 일시정지 + 이력 등록
     *   - 비밀번호 검증은 기존 getPauseUserId 와 동일
     *   - 반환: 1 반영 / 0 미반영(이미 정지·상태 '00' 아님) / -1 비밀번호 오류 / -2 USER_ID 없음
     *
     * @author  김유승
     * @since   2026. 09. 29.
     */
    @RequestMapping(value = "/userWebHist/getPauseUserIdWithHist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPauseUserIdWithHist(@RequestBody UserWebHistVO userWebHistVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result;
        if (userWebHistVO.getUserId() == null || userWebHistVO.getUserId().trim().isEmpty()) {
            result = -2;
        } else if (!"00001".equals(userWebHistVO.getPassword())) {
            result = -1;
        } else {
            userWebHistVO.setUserId(userWebHistVO.getUserId().trim());
            result = userWebHistService.getPauseUserIdWithHist(userWebHistVO, sessionInfoVO);
        }

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * [조회정보 팝업] 일시정지 이력 조회
     *
     * @author  김유승
     * @since   2026. 09. 29.
     */
    @RequestMapping(value = "/userWebHist/getUserPauseHistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserPauseHistList(@RequestBody UserWebHistVO userWebHistVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = userWebHistService.getUserPauseHistList(userWebHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, userWebHistVO);
    }

    /**
     * [조회정보 팝업] USER_ID 일시정지 해제 + 이력 등록
     *   - 반환: 1 반영 / 0 미반영(상태 '50' 아님) / -1 비밀번호 오류 / -2 USER_ID 없음
     *
     * @author  김유승
     * @since   2026. 09. 30.
     */
    @RequestMapping(value = "/userWebHist/getResumeUserIdWithHist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getResumeUserIdWithHist(@RequestBody UserWebHistVO userWebHistVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result;
        if (userWebHistVO.getUserId() == null || userWebHistVO.getUserId().trim().isEmpty()) {
            result = -2;
        } else if (!"00001".equals(userWebHistVO.getPassword())) {
            result = -1;
        } else {
            userWebHistVO.setUserId(userWebHistVO.getUserId().trim());
            result = userWebHistService.getResumeUserIdWithHist(userWebHistVO, sessionInfoVO);
        }

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /**
     * [조회정보 팝업] ⑤ 웹사용자정보변경이력 조회
     *   - TB_LG_WB_USER_INFO_LOG 기준, 조회기간(chkDay1~chkDay2) + USER_ID(선택)
     *
     * @author  김유승
     * @since   2026. 09. 30.
     */
    @RequestMapping(value = "/userWebHist/getUserInfoLogList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserInfoLogList(@RequestBody UserWebHistVO userWebHistVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = userWebHistService.getUserInfoLogList(userWebHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, userWebHistVO);
    }

}