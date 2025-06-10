package kr.co.solbipos.application.common.web;

import kr.co.common.data.enums.CodeType;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.JavaScriptResult;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.exception.BizException;
import kr.co.common.exception.CodeException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.application.session.auth.enums.LoginFg;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @author 정용길
 *
 */
@ControllerAdvice
public class ExceptionController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** 메세지 서비스 */
    private final MessageService messageService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public ExceptionController(MessageService messageService, SessionService sessionService) {
        this.messageService = messageService;
        this.sessionService = sessionService;
    }

    /**
     * 업무관련 예외 처리<br>
     * {@link BizException} 를 상속 받은 모든 {@code Exception} 을 처리
     *
     * @param e {@code BizException}
     * @return JSON 요청일 때 아닐 때 script {@code String}
     */
    @ExceptionHandler(BizException.class)
    public Object bizExceptionHandle(BizException e) {

        //SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        //String userId = isEmpty(sessionInfoVO) ? "" : sessionInfoVO.getUserId();

        String errorMessage = e.getMessage();
        String responseURL = e.getResponseURL();
        Status status = e.getStatus();

        if (WebUtil.isJsonRequest()) {
            if(isEmpty(status)) {
                status = Status.FAIL;
            }
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
     * 코드관련 예외 처리<br>
     * {@link CodeException} 를 상속 받은 모든 {@code Exception} 을 처리
     *
     * @param e {@code CodeException}
     * @return JSON 요청일 때 아닐 때 script {@code String}
     */
    @ExceptionHandler(CodeException.class)
    public String codeExceptionHandle(CodeException e, RedirectAttributes redirectAttributes) {

        CodeType codeType = e.getCodeType();
        String codeCd = e.getCodeCd();
        String responseURL = e.getResponseURL();

        redirectAttributes.addAttribute("codeType", codeType);
        redirectAttributes.addAttribute("codeCd", codeCd);
        return "redirect:" + responseURL;
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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        String userId = isEmpty(sessionInfoVO) ? "" : sessionInfoVO.getUserId();

        LOGGER.error("Exception : id: {}", userId, e);

        // tomcat root directory
        String catalinaBase = System.getProperty("catalina.base");
        String catalinaHome = System.getProperty("catalina.home");
        LOGGER.debug("catalinaBase : " + catalinaBase);
        LOGGER.debug("catalinaHome : " + catalinaHome);

        // 오늘 날짜
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyyMMdd").format(date);
        String nowDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(date);

        // 생성 파일 경로
        //String fileName = "D:\\prod_img\\ERRORLOG_" + nowDate + ".OUT"; // TEST
        String fileName = catalinaBase + "/logs/ERRORLOG_" + nowDate + ".OUT";

        // 로그 데이터
        StringBuilder str2 = new StringBuilder(100);
        str2.append("====================================== START [" + nowDateTime + "] ========================================" + "\n");
        str2.append("Exception : id: " + userId + "\n");
        str2.append(ExceptionUtils.getStackTrace(e) + "\n");
        str2.append("============================================= END ==============================================" + "\n");

        try {

            // 파일 객체 생성
            File file = new File(fileName);

            // true 지정시 파일의 기존 내용에 이어서 작성
            FileWriter fw = new FileWriter(file, true);

            // 파일안에 문자열 쓰기
            fw.write(str2.toString());
            fw.flush();

            // 객체 닫기
            fw.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (WebUtil.isJsonRequest()) {
            String msg = messageService.get("cmm.error");
            Result result = new Result(Status.SERVER_ERROR);
            result.setMessage(msg);
            return new ResponseEntity<Result>(result, HttpStatus.OK);
        }

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();

        String ROOT_PATH = "";
        if(sessionInfoVO != null){
          if(sessionInfoVO.getLoginFg() != null && sessionInfoVO.getLoginFg().equals(LoginFg.MOBILE.getCode())){
              ROOT_PATH = "mobile/";
          }
        }else if(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_FG) != null){
          if(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_FG).getValue().equals(LoginFg.MOBILE.getCode())){
              ROOT_PATH = "mobile/";
          }
        }else if (request.getRequestURI().substring(0, 8).equals("/mobile/")){
          ROOT_PATH = "mobile/";
        }

        // 에러 페이지 이동
        return ROOT_PATH + "error/403";
    }
}
