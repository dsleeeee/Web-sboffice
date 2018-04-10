package kr.co.solbipos.aspect;

import static org.springframework.util.ObjectUtils.*;
import java.util.HashMap;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import kr.co.solbipos.application.domain.cmm.Page;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.JavaScriptResult;
import kr.co.solbipos.structure.Result;

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

    @Autowired
    SessionService sessionService;

    /**
     * 결과 처리
     * 
     * @param pjp {@code ProceedingJoinPoint}
     * @return 이전 Method 의 전달받은 객체 {@code Object}
     * @throws Throwable
     */
    @Around("execution(public kr..Result kr..*Controller.*(..))" // json
            + " || execution(public kr..JavaScriptResult kr..*Controller.*(..))" // javascript
            + " || execution(public Object kr..*Controller.*(..))") // ExceptionController
    public Object ResultHandling(ProceedingJoinPoint pjp) throws Throwable {

        Object result = pjp.proceed();

        /** 자바 스크립트 리턴 결과 */
        if (result instanceof JavaScriptResult) {
            return new ModelAndView("script").addObject("result", result);
        }
        /**
         * Result 결과에서 json 조회 결과가 없을 경우 
         * 1. 조회 데이터 없음 메세지를 입력해줌 
         * */
        else if (result instanceof Result) {
            Result r = (Result) result;
            if (r.getData() instanceof HashMap) {
                @SuppressWarnings("unchecked")
                HashMap<String, Object> map = (HashMap<String, Object>) r.getData();
                /** 
                 * 조회 결과 리스트가 없을 경우 없고 메시지 세팅을 따로 안한 경우
                 * */
                if (isEmpty(map.get("list")) && isEmpty(r.getMessage())) {
                    // 조회 결과가 없습니다. 메시지를 세팅
                    r.setMessage(messageService.get("cmm.empty.data"));
                }
            }
        }
        return result;
    }
}


