package kr.co.common.interceptor;

import static org.springframework.util.ObjectUtils.isEmpty;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import kr.co.common.data.enums.Status;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.exception.JsonException;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.AppUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.solbipos.application.common.enums.ResrceFg;
import kr.co.solbipos.application.common.service.ResrceInfoVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    SessionService sessionService;

    @Autowired
    CmmMenuService cmmMenuService;

    @Autowired
    MessageService messageService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {

        String requestURL = request.getRequestURI().toString();
        log.info("url : {}, accept : {}, ", requestURL, request.getHeader("accept"));

        /**
         * 세션 종료 처리
         */
        if (!sessionService.isValidSession(request)) {
            sessionService.deleteSessionInfo(request);

            if (WebUtil.isJsonRequest(request)) {
                String msg = messageService.get("cmm.session.expire");
                String msg1 = messageService.get("cmm.move.login");
                throw new JsonException(Status.SESSION_EXFIRE, msg + msg1, "/");
            } else {
                response.sendRedirect("/");
                return false;
            }
        }

        // 세션 가져오기
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 권한 메뉴
        List<ResrceInfoVO> auth = sessionInfoVO.getAuthMenu();
        // 유져 조회 날짜 저장
        sessionInfoVO = setUserSelectDate(request, sessionInfoVO);

        // 본사, 가맹점 만 storeCd 에 대해서 체크한다.
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            checkStoreCd(request, sessionInfoVO);
        }
        else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            String storeCd = request.getParameter("storeCd");
            if(!isEmpty(storeCd)) {
                if(!storeCd.equals(sessionInfoVO.getOrgnCd())) {
                    // 유효하지 않는 매장코드 입니다.
                    String msg = messageService.get("cmm.not.storecd");
                    throw new AuthenticationException(msg, "/error/403.sb");
                }
            }
        }

        // 권한 체크

        if (!checkUrl(request, auth, requestURL, sessionInfoVO.getUserId(), sessionInfoVO)) {
            log.error(
                    "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n"
                     + "not auth : id : {},  url : {}, accept : {}",
                     sessionInfoVO.getUserId(), requestURL, request.getHeader("accept")
                     + "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

            @SuppressWarnings( "unused" )
            String exceptionMsg = messageService.get("cmm.access.denied");

            // 권한 없음 처리
            //TODO 개발 진행중 주석처리
            //throw new AuthenticationException(exceptionMsg, "/error/403.sb");
        }


        // jsp > sessionscope 로 쓸수 있게 httpsession 에 세팅
        HttpSession session = request.getSession();
        session.setAttribute("sessionInfo", sessionInfoVO);

        return true;
    }

    /**
      * storecd 의 유효성을 검증한다.<br>
      *
      *
      * @param request
      * @param sessionInfoVO
      */
    private void checkStoreCd(HttpServletRequest request, SessionInfoVO sessionInfoVO) {
        String storeCd = request.getParameter("storeCd");
        if(!isEmpty(storeCd)) {
            if(storeCd.indexOf(",") > -1) {
                if(!AppUtil.listIndexOf(sessionInfoVO.getArrStoreCdList(), storeCd.split(","))) {
                    // 유효하지 않는 매장코드 입니다.
                    String msg = messageService.get("cmm.not.storecd");
                    throw new AuthenticationException(msg, "/error/403.sb");
                }

            }
            else {
                if(!AppUtil.listIndexOf(sessionInfoVO.getArrStoreCdList(), storeCd)) {
                    // 유효하지 않는 매장코드 입니다.
                    String msg = messageService.get("cmm.not.storecd");
                    throw new AuthenticationException(msg, "/error/403.sb");
                }
            }
        }
    }

    /**
      * 유져가 조회한 조회 날짜를 세션에 저장
      *
      * @param request
      * @param sessionInfoVO
      */
    private SessionInfoVO setUserSelectDate(HttpServletRequest request, SessionInfoVO sessionInfoVO) {
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        if(!isEmpty(startDt)) {
            sessionInfoVO.setStartDt(startDt);
        }
        if(!isEmpty(endDt)) {
            sessionInfoVO.setEndDt(endDt);
        }
        sessionService.setSessionInfo(sessionInfoVO);
        return sessionInfoVO;
    }

    /**
     * 유효 URL 체크!!
     *
     * @param auth
     * @param url
     * @param userId
     * @return
     */
    private boolean checkUrl(HttpServletRequest request, List<ResrceInfoVO> auth, String url,
            String userId, SessionInfoVO sessionInfoVO) {

        if (url.equals("/main.sb")) {
            return true;
        }

        int n = auth.size();
        for (int i = 0; i < n; i++) {
            ResrceInfoVO resrceInfoVO = auth.get(i);
            String authUrl = resrceInfoVO.getUrl();
            if (!isEmpty(authUrl)) {
                // 등록된 URL 에 파라미터가 있는 경우 파라미터 제거
                if (authUrl.indexOf("?") > -1) {
                    authUrl = authUrl.substring(0, authUrl.indexOf("?"));
                }

                if (authUrl.equals(url)) {
                    // 메뉴 일때만 사용 등록과 메뉴 히스토리에 추가함
                    if (resrceInfoVO.getResrceFg() == ResrceFg.MENU) {
                        // 메뉴 사용 등록
                        cmmMenuService.insertMenuUseHist(resrceInfoVO, sessionInfoVO);
                        // 세션에 사용 메뉴 넣기
                        cmmMenuService.addHistMenu(resrceInfoVO, sessionInfoVO);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}


