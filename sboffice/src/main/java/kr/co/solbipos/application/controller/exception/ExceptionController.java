package kr.co.solbipos.application.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import kr.co.solbipos.exception.AuthenticationException;
import kr.co.solbipos.exception.BizException;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.JavaScriptResult;
import kr.co.solbipos.structure.JsonResult;
import kr.co.solbipos.structure.Result.Status;
import kr.co.solbipos.utils.spring.StringUtil;
import kr.co.solbipos.utils.spring.WebUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 예외 처리 ( Controller )
 * 
 * @author 이호원
 */

@Slf4j
@ControllerAdvice
public class ExceptionController {
    
    /** 기록자 */
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
    
    /** 메세지 서비스 */
    @Autowired
    MessageService messageService;
    
    @Autowired
    SessionService sessionService;

    /**
     * 인증 예외 처리
     * 
     * @param e {@code AuthenticationException}
     * @return JSON 요청일 때 {@code JsonResult} 아닐 때 script {@code String}
     * @see ExceptionController#bizExceptionHandle( BizException e )
     */
    @ExceptionHandler(AuthenticationException.class)
    public Object authenticationExceptionHandle(AuthenticationException e) {
        return bizExceptionHandle(e);
    }

    /**
     * 업무관련 예외 처리
     * 
     * @param e {@code BizException}
     * @return JSON 요청일 때 {@code JsonResult} 아닐 때 script {@code String}
     */
    @ExceptionHandler(BizException.class)
    public Object bizExceptionHandle(BizException e) {
        String errorMessage = e.getMessage(), responseURL = e.getResponseURL();

        if (logger.isDebugEnabled())
            logger.debug("Response URL : {}", responseURL);

        if (WebUtil.isJsonRequest()) {
            JsonResult jsonResult = new JsonResult(errorMessage, responseURL);
            // common.js 에서 아래 타입으로 처리
            jsonResult.setStatus(Status.NOT_AUTHENTICATION);
            return new ResponseEntity<JsonResult>(jsonResult, HttpStatus.OK);
        }

        String script =
                String.format("alert('%s');%s;", errorMessage, StringUtil.getFirstIfEmptyOrSecond(
                        responseURL, "history.back()", "location.href='" + responseURL + "'"));

        return new JavaScriptResult(script);
    }

    /**
     * 기타 예외 처리
     * 
     * @param e {@code Exception}
     * @return JSON 요청일 때 {@code JsonResult} 아닐 때 view name {@code String}
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exceptionHandle(Exception e) {
        if (WebUtil.isJsonRequest()) {
            JsonResult jsonResult = new JsonResult(messageService.get("error.default"));

            return new ResponseEntity<JsonResult>(jsonResult, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return "Error";
    }
}