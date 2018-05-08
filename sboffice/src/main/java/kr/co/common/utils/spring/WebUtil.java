package kr.co.common.utils.spring;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebUtil extends WebUtils {

    private static RequestAttributes getRequestAttributes() {
        return RequestContextHolder.getRequestAttributes();
    }

    /**
     * JSON 요청인지 확인
     * 
     * @return boolean
     */
    public static boolean isJsonRequest() {
        return isJsonRequest(getRequest());
    }

    /**
     * JSON 요청인지 확인
     * 
     * @param request HttpServletRequest
     * @return boolean
     */
    public static boolean isJsonRequest(HttpServletRequest request) {
        if (request == null) {
            if (log.isErrorEnabled())
                log.error("isJsonRequest : request parameter is null");

            return false;
        }

        return request.getHeader("Accept").indexOf(MediaType.APPLICATION_JSON_VALUE) > -1;
    }

    /**
     * HttpServletRequest 취득
     * 
     * @return ( RequestContextHolder ) HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) getRequestAttributes()).getRequest();
    }

    /**
     * HttpServletResponse 취득
     * 
     * @return ( RequestContextHolder ) HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) getRequestAttributes()).getResponse();
    }

    /**
     * HttpSession 취득
     * 
     * @return ( RequestContextHolder ) HttpSession
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * Session 값 취득 ( 동일 메서드에서 여러번 사용시 {@link #getSession} 사용 )
     * 
     * @param key String
     * @return Object type session value
     */
    public static Object getSessionValue(String key) {
        return getRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * Session 값 삽입 ( 동일 메서드에서 여러번 사용시 {@link #getSession} 사용 )
     * 
     * @param key String
     */
    public static void setSessionValue(String key, Object o) {
        getRequestAttributes().setAttribute(key, o, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * Session 값 취득 ( 동일 메서드에서 여러번 사용시 {@link #getSession} 사용 )
     * 
     * @param key String
     * @return String type session value
     */
    public static String getSessionString(String key) {
        Object value = getSessionValue(key);

        return value == null ? "" : value.toString();
    }

    /**
     * Cookie 값 취득
     * 
     * @param name String
     * @return cookie value
     */
    public static String getCookieValue(String name) {
        Cookie cookie = getCookie(getRequest(), name);

        return cookie == null ? "" : cookie.getValue();
    }
    
    /**
      * http 응답분할 공격 방지
      * @param name
      * @param value
      * @return
      */
    private static Cookie newCookie(String name, String value) {
        value = StringUtil.isEmpty(value) ? "" : value.replaceAll("[\\r\\n]", "");
        return new Cookie(name, value);
    }
    

    /**
     * Cookie 값 삽입
     * 
     * @param name String
     */
    public static void setCookieValue(String name, String value, int expireDay) {
        Cookie cookie = newCookie(name, value);
        cookie.setMaxAge(60 * 60 * 24 * (expireDay < 1 ? 1 : expireDay));
        getResponse().addCookie(cookie);
    }
    
    /**
      * Cookie 값 삽입 : setPath("/")
      * @param name
      * @param value
      * @param maxAge
      */
    public static void setCookie(String name, String value, int maxAge) {
        Cookie cookie = newCookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        getResponse().addCookie(cookie);
    }
    
    /**
      * 쿠키 지우기
      * @param cookie
      */
    public static void removeCookie(Cookie cookie) {
        if (cookie != null) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            getResponse().addCookie(cookie);
        }
    }

    /**
     * Cookie 값 제거
     * 
     * @param name
     */
    public static void removeCookie(String name) {
        setCookieValue(name, null, 0);
    }

    /**
     * REFERER 정보 호출
     * 
     * @request HttpServletRequest
     * @return String
     */
    public static String getRefererUrl(HttpServletRequest request) {
        String referer = request.getHeader("REFERER");

        return StringUtil.isEmpty(referer) ? "" : referer.substring(referer.lastIndexOf("/") + 1);
    }
}
