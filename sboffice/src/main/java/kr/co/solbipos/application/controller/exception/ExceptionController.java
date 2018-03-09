package kr.co.solbipos.application.controller.exception;

import static org.springframework.util.ObjectUtils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.exception.AuthenticationException;
import kr.co.solbipos.exception.BizException;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.JavaScriptResult;
import kr.co.solbipos.structure.Result;
import kr.co.solbipos.utils.spring.StringUtil;
import kr.co.solbipos.utils.spring.WebUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 정용길
 *
 */
@Slf4j
@ControllerAdvice
public class ExceptionController {

    /** 메세지 서비스 */
    @Autowired
    MessageService messageService;

    @Autowired
    SessionService sessionService;

    /**
     * 업무관련 예외 처리<br>
     * {@link BizException} 를 상속 받은 모든 {@code Exception} 을 처리
     * 
     * @param e {@code BizException}
     * @return JSON 요청일 때 아닐 때 script {@code String}
     */
    @ExceptionHandler(BizException.class)
    public Object bizExceptionHandle(BizException e) {

        SessionInfo sessionInfo = sessionService.getSessionInfo();
        String userId = isEmpty(sessionInfo) ? "" : sessionInfo.getUserId();

        String errorMessage = e.getMessage();
        String responseURL = e.getResponseURL();
        Status status = e.getStatus();

        log.error("BizException : id: {}, status : {}, msg : {}, url : {}", userId, status,
                errorMessage, responseURL, e);

        if (WebUtil.isJsonRequest()) {
            Result result = new Result(status);
            result.setUrl(responseURL);
            result.setMessage(errorMessage);
            return new ResponseEntity<Result>(result, HttpStatus.OK);
        }

        String script =
                String.format("alert('%s');%s;", errorMessage, StringUtil.getFirstIfEmptyOrSecond(
                        responseURL, "history.back()", "location.href='" + responseURL + "'"));

        return new JavaScriptResult(script);
    }

    /**
     * 인증 예외 처리
     * 
     * @param e {@code AuthenticationException}
     * @return JSON 요청이 아닐 때 script {@code String}
     * @see ExceptionController#bizExceptionHandle( BizException e )
     */
    @ExceptionHandler(AuthenticationException.class)
    public Object authenticationExceptionHandle(AuthenticationException e) {
        return bizExceptionHandle(e);
    }

    /**
     * {@code BizException}, {@code AuthenticationException} 제외한 {@code Exception} 처리
     * 
     * @param e {@code Exception}
     * @return JSON 요청이 아닐 때 view name {@code String}
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exceptionHandle(Exception e) {

        SessionInfo sessionInfo = sessionService.getSessionInfo();
        String userId = isEmpty(sessionInfo) ? "" : sessionInfo.getUserId();

        log.error("Exception : id: {}", userId, e);

        if (WebUtil.isJsonRequest()) {
            String msg = messageService.get("msg.error");
            Result result = new Result(Status.SERVER_ERROR);
            result.setMessage(msg);
            return new ResponseEntity<Result>(result, HttpStatus.OK);
        }

        // 에러 페이지 이동
        return "error/403";
    }
}
