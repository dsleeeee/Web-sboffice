package kr.co.common.interceptor;

import kr.co.common.data.enums.Status;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.exception.JsonException;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.solbipos.application.common.service.ResrceInfoBaseVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.application.session.auth.enums.LoginFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @Class Name : AuthenticationInterceptor.java
 * @Description : 권한관련 Interceptor
 * @Modification Information
 * @
 * @ 수정일       수정자      수정내용
 * @ ----------  ---------  -------------------------------
 * @ 2018.09.04  노현수      부분수정
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final CmmMenuService cmmMenuService;
    private final MessageService messageService;

    String ROOT_PATH = "/";

    /** Constructor Injection */
    @Autowired
    public AuthenticationInterceptor(SessionService sessionService, CmmMenuService cmmMenuService, MessageService messageService) {
        this.sessionService = sessionService;
        this.cmmMenuService = cmmMenuService;
        this.messageService = messageService;
    }

    /**
     * preHandler : Interceptor 진입시 수행
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param handler Object
     * @Return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        String requestURL = request.getRequestURI();
        LOGGER.info("url : {}, accept : {}, ", requestURL, request.getHeader("accept"));

        // 세션 가져오기
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 세션 상태
        boolean isSessionValid = true;
        // 세션 객체가 없는 경우
        if ( sessionInfoVO == null ) {
            isSessionValid = false;
        } else {
            // 세션 객체는 있지만 필수값들이 없는 경우
            if ( sessionInfoVO.getUserId() == null && sessionInfoVO.getMenuData() == null ) {
                isSessionValid = false;
            }
        }

        //
        ROOT_PATH = "/";
        if(sessionInfoVO != null){
            if(sessionInfoVO.getLoginFg() != null && sessionInfoVO.getLoginFg().equals(LoginFg.MOBILE.getCode())){
                ROOT_PATH = "/mobile/";
            }
        }else if(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_FG) != null){
            if(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_FG).getValue().equals(LoginFg.MOBILE.getCode())){
                ROOT_PATH = "/mobile/";
            }
        }else if (request.getRequestURI().substring(0, 8).equals("/mobile/")){
            ROOT_PATH = "/mobile/";
        }

        // 세션 종료 처리
        if (!isSessionValid) {
            // 로그 기록. inValidation 처리시 쉽게 알아보기 위함.
            LOGGER.info("AuthenticationInterceptor :: isValidSession :: deleteSessionInfo");
            // 세션 삭제
            sessionService.deleteSessionInfo(request);
            // Json 호출인지 판단하여 별도 메시지 리턴
            if (WebUtil.isJsonRequest(request)) {
                String msg = messageService.get("cmm.session.expire");
                String msg1 = messageService.get("cmm.move.login");
                // 로그 기록
                LOGGER.info("AuthenticationInterceptor :: isJsonRequest :: " + msg);
                throw new JsonException(Status.SESSION_EXFIRE, msg + msg1, ROOT_PATH.equals("/") ? ROOT_PATH : ROOT_PATH + "auth/login.sb");
            } else {
                response.sendRedirect(ROOT_PATH.equals("/") ? ROOT_PATH : ROOT_PATH + "auth/login.sb");
                return false;
            }

        }else{
            // (세션이 있는 경우만) 세션 값 확인, 로그 검증용(가상로그인의 경우 매장/대리점 구분이 안되어 로그 남김)
            LOGGER.info("getUserId : {}, getvUserId : {}, getvLogindIds : {}, ", sessionInfoVO.getUserId(), sessionInfoVO.getvUserId(), sessionInfoVO.getvLogindIds());
            //정상 세션값 확인시
            //LOGGER.info("getSessionId : {},getUserId : {},getUserPwd : {},getUserNm : {},getAuthGrpCd : {},getArrStoreCdList : {},getOrgnFg : {},getOrgnGrpCd : {},getOrgnGrpNm : {},getOrgnCd : {},getOrgnNm : {},getStoreCd : {},getStoreNm : {},getHqOfficeCd : {},getHqOfficeNm : {},getEmpNo : {},getLastLoginDt : {},getLastPwdChgDt : {},getLoginFailCnt : {},getUserStatFg : {},getUseYn : {},getLoginIp : {},getBrwsrInfo : {},getLoginResult : {},getStartDate : {},getEndDate : {},getMenuData : {},getMenuTreeData : {},getBkmkMenuData : {},getBkmkMenuTreeData : {},getFixedMenuData : {},getHistoryMenuData : {},getCurrentMenu : {},getvUserId : {},getvLogindIds : {},getHwAuthKey : {},getpAgencyCd : {},getAreaFg : {}", sessionInfoVO.getSessionId(), sessionInfoVO.getUserId(), "NOT CHECK", sessionInfoVO.getUserNm(), sessionInfoVO.getAuthGrpCd(), sessionInfoVO.getArrStoreCdList(), sessionInfoVO.getOrgnFg(), sessionInfoVO.getOrgnGrpCd(), sessionInfoVO.getOrgnGrpNm(), sessionInfoVO.getOrgnCd(), sessionInfoVO.getOrgnNm(), sessionInfoVO.getStoreCd(), sessionInfoVO.getStoreNm(), sessionInfoVO.getHqOfficeCd(), sessionInfoVO.getHqOfficeNm(), sessionInfoVO.getEmpNo(), sessionInfoVO.getLastLoginDt(), sessionInfoVO.getLastPwdChgDt(), sessionInfoVO.getLoginFailCnt(), sessionInfoVO.getUserStatFg(), sessionInfoVO.getUseYn(), sessionInfoVO.getLoginIp(), sessionInfoVO.getBrwsrInfo(), sessionInfoVO.getLoginResult(), sessionInfoVO.getStartDate(), sessionInfoVO.getEndDate(), sessionInfoVO.getMenuData(), sessionInfoVO.getMenuTreeData(), sessionInfoVO.getBkmkMenuData(), sessionInfoVO.getBkmkMenuTreeData(), sessionInfoVO.getFixedMenuData(), sessionInfoVO.getHistoryMenuData(), sessionInfoVO.getCurrentMenu(), sessionInfoVO.getvUserId(), sessionInfoVO.getvLogindIds(), sessionInfoVO.getHwAuthKey(), sessionInfoVO.getpAgencyCd(), sessionInfoVO.getAreaFg());
        }

        // 유져 조회 날짜 저장
        sessionInfoVO = setUserSelectDate(request, sessionInfoVO);

        // 본사, 가맹점 만 storeCd 에 대해서 체크한다.
//        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
//            checkStoreCd(request, sessionInfoVO);
//        } else
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            String storeCd = request.getParameter("storeCd");
            if (!isEmpty(storeCd)) {
                if (!storeCd.equals(sessionInfoVO.getOrgnCd())) {
                    // 유효하지 않는 매장코드 입니다.
                    String msg = messageService.get("cmm.not.storecd");
                    // 로그 기록
                    LOGGER.info("AuthenticationInterceptor :: " + storeCd + " :: " + msg);
                    throw new AuthenticationException(msg, "/error/403.sb");
                }
            }
        }

        // 메뉴 권한 체크
        if (!checkUrl(request, requestURL, sessionInfoVO)) {
            LOGGER.info("not auth : id : {},  url : {}, accept : {}", sessionInfoVO.getUserId(),
                requestURL, request.getHeader("accept"));
            @SuppressWarnings("unused")
            String exceptionMsg = messageService.get("cmm.access.denied");

            // 권한 없음 처리
            throw new AuthenticationException(exceptionMsg, "/error/403.sb");
        }

        // jsp > sessionscope 로 쓸수 있게 httpsession 에 세팅
        HttpSession session = request.getSession();
        session.setAttribute("sessionInfo", sessionInfoVO);

        return super.preHandle(request, response, handler);
    }

    /**
     * storecd 의 유효성을 검증한다.<br>
     *
     * @param request HttpServletRequest
     * @param sessionInfoVO SessionInfoVO
     */
    private void checkStoreCd(HttpServletRequest request, SessionInfoVO sessionInfoVO) {
        String storeCd = request.getParameter("storeCd");
        if (!isEmpty(storeCd)) {
            if (storeCd.contains(",")) {
                if (!CmmUtil.listIndexOf(sessionInfoVO.getArrStoreCdList(), storeCd.split(","))) {
                    // 유효하지 않는 매장코드 입니다.
                    String msg = messageService.get("cmm.not.storecd") + ">>";
                    throw new AuthenticationException(msg, "/error/403.sb");
                }
            } else {
                if (!CmmUtil.listIndexOf(sessionInfoVO.getArrStoreCdList(), storeCd)) {
                    // 유효하지 않는 매장코드 입니다.
                    String msg = messageService.get("cmm.not.storecd") + ">>>";
                    throw new AuthenticationException(msg, "/error/403.sb");
                }
            }
        }
    }

    /**
     * 유져가 조회한 조회 날짜를 세션에 저장
     *
     * @param request HttpServletRequest
     * @param sessionInfoVO SessionInfoVO
     */
    private SessionInfoVO setUserSelectDate(HttpServletRequest request, SessionInfoVO sessionInfoVO) {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if ( !isEmpty(startDate) ) {
            sessionInfoVO.setStartDate(startDate);
        }
        if ( !isEmpty(endDate) ) {
            sessionInfoVO.setEndDate(endDate);
        }
        // 값이 있는 경우에만 session 에 넣는다
        if ( !isEmpty(startDate) || !isEmpty(endDate) ) {
            sessionService.setSessionInfo(sessionInfoVO);
        }
        return sessionInfoVO;
    }

    /**
     * 유효 URL 체크 : 메뉴Data에 존재하는 URL 인지 체크
     *
     * @param request HttpServletRequest
     * @param url String
     * @param sessionInfoVO SessionInfoVO
     * @return boolean
     */
    private boolean checkUrl(HttpServletRequest request, String url, SessionInfoVO sessionInfoVO) {
        // main 주소는 제외
        if (url.equals(ROOT_PATH + "main.sb") || url.equals("/sample/tonymory/Login.sb")) {
            return true;
        }
        // url 파라미터 제거
        if ( url.contains("?") ) {
            url = url.substring(0, url.indexOf("?"));
        }

        // main.sb 호출 시, 로그인한 사람의 권한에 따라 다른 메인 URL redirect
        if(url.contains("application/main/content/")){

            // 관리자
            if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER){
                if(!(ROOT_PATH + "application/main/content/sys.sb").equals(url)){ return false; }
            }

            // 대리점
            if(sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY){
                if(!(ROOT_PATH + "application/main/content/agency.sb").equals(url)){ return false; }
            }

            // 본사
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
                if(!(ROOT_PATH +"application/main/content/hq.sb").equals(url)){ return false; }
            }

            // 매장
            if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
                if(!(ROOT_PATH + "application/main/content/store.sb").equals(url)){ return false; }
            }

            return true;
        }

        // 유효 메뉴 여부 확인(화면 URL만 체크, Event URL은 skip)
        if(cmmMenuService.menuResrceChk(url) < 1){
            LOGGER.info("Event URL : " + url);
            LOGGER.info("getUserId : {}, getvUserId : {}, getvLogindIds : {}, ", sessionInfoVO.getUserId(), sessionInfoVO.getvUserId(), sessionInfoVO.getvLogindIds());
            return true;
        }

        // 세션 권한이 사용할 수 있는 메뉴 목록
        List<ResrceInfoBaseVO> menuList = sessionInfoVO.getMenuData();
        // url 값 비교
        for (ResrceInfoBaseVO resrceInfoBaseVO : menuList) {
            String authUrl = resrceInfoBaseVO.getUrl();
            if ( !isEmpty(authUrl) ) {
                // 등록된 URL 에 파라미터가 있는 경우 파라미터 제거
                if ( authUrl.contains("?") ) {
                    authUrl = authUrl.substring(0, authUrl.indexOf("?"));
                }
                if ( authUrl.equals(url) ) {
                    // 메뉴 일때만 사용 등록과 메뉴 히스토리에 추가함
                    if ( RequestMethod.GET.toString().equals(request.getMethod()) ) {
                        // 세션에 사용 메뉴 넣기
                        cmmMenuService.addHistMenu(resrceInfoBaseVO, sessionInfoVO);
                        // 사용 히스토리 등록
                        cmmMenuService.insertMenuUseHist(resrceInfoBaseVO, sessionInfoVO);
                        return true;
                    } else if (RequestMethod.POST.toString().equals(request.getMethod())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
