package kr.co.solbipos.interceptor;

import static org.springframework.util.ObjectUtils.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.exception.AuthenticationException;
import kr.co.solbipos.service.cmm.CmmMenuService;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.service.session.SessionService;
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

        // 세션 없음
        if (!sessionService.isValidSession(request)) {
            log.info("session null...");
            sessionService.deleteSessionInfo(request, response);
            response.sendRedirect("/");
            return false;
        }

        // 세션 가져오기
        SessionInfo sessionInfo = sessionService.getSessionInfo(request);
        // 권한 메뉴
        List<ResrceInfo> auth = sessionInfo.getAuthMenu();

        // 권한 체크
        if (!checkUrl(request, auth, requestURL, sessionInfo.getUserId(), sessionInfo)) {
            log.error(
                    "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n"
                            + "not auth : id : {},  url : {}, accept : {}",
                    sessionInfo.getUserId(), requestURL, request.getHeader("accept")
                            + "\n■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

            String exceptionMsg = messageService.get("error.access.denied");

            // 권한 없음 처리
            throw new AuthenticationException(exceptionMsg, "/error/403.sb");
        }

        // jsp > sessionscope 로 쓸수 있게 httpsession 에 세팅
        HttpSession session = request.getSession();
        session.setAttribute("sessionInfo", sessionInfo);

        return true;
    }

    /**
     * 유효 URL 체크!!
     * 
     * @param auth
     * @param url
     * @param userId
     * @return
     */
    private boolean checkUrl(HttpServletRequest request, List<ResrceInfo> auth, String url,
            String userId, SessionInfo sessionInfo) {
        
        if(url.equals("/main.sb")) {
            return true;
        }
        
        int n = auth.size();
        for (int i = 0; i < n; i++) {
            ResrceInfo resrceInfo = auth.get(i);
            String authUrl = resrceInfo.getUrl();
            if (!isEmpty(authUrl)) {
                // 등록된 URL 에 파라미터가 있는 경우 파라미터 제거
                if (authUrl.indexOf("?") > -1) {
                    authUrl = authUrl.substring(0, authUrl.indexOf("?"));
                }

                if (authUrl.equals(url)) {
                    // 메뉴 일때만 사용 등록과 메뉴 히스토리에 추가함
                    if (resrceInfo.getResrceFg().equals("M")) {
                        // 메뉴 사용 등록
                        cmmMenuService.insertMenuUseHist(resrceInfo, sessionInfo);
                        // 세션에 사용 메뉴 넣기
                        cmmMenuService.addHistMenu(resrceInfo, sessionInfo);
                    }
                    return true;
                }
            }
        }
        return false;
    }

}


