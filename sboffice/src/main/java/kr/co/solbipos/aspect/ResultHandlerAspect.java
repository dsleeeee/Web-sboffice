package kr.co.solbipos.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.JavaScriptResult;
import kr.co.solbipos.structure.JsonResult;

/**
 * Return 되는 객체를 받아 Handling 해주는 Class
 * 
 * @author 이호원
 */
@Aspect
@Component
public class ResultHandlerAspect {
    /** 메세지 서비스 */
    @Autowired
    MessageService messageService;

    /**
     * 결과 처리
     * 
     * @param pjp {@code ProceedingJoinPoint}
     * @return 이전 Method 의 전달받은 객체 {@code Object}
     * @throws Throwable
     */
    @Around("execution(public kr..JsonResult kr..*Controller.*(..))"
            + " || execution(public kr..JavaScriptResult kr..*Controller.*(..))"
            + " || execution(public Object kr..*Controller.*(..))")
    public Object ResultHandling(ProceedingJoinPoint pjp) throws Throwable {

        Object result = pjp.proceed();

        if (result instanceof JavaScriptResult) {
            return new ModelAndView("script").addObject("result", result);
        } else if (result instanceof JsonResult) {
            JsonResult jsonResult = (JsonResult) result;

            if (jsonResult.getStatus() != null) {
                jsonResult.setMessage(messageService.get(jsonResult.getStatus().getReason()));
            }
        }
        return result;
    }
}
