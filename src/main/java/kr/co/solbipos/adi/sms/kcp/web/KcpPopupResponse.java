package kr.co.solbipos.adi.sms.kcp.web;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * KCP 팝업 콜백 HTML과 사용자 알림 스크립트를 생성한다.
 * 전달값을 JSON·HTML 이스케이프한 뒤 부모 창을 호출하고 팝업을 닫는다.
 */
public final class KcpPopupResponse {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** 정적 응답 함수만 제공하므로 인스턴스 생성을 막는다. */
    private KcpPopupResponse() {
    }

    /**
     * 부모 창의 지정 함수를 호출한 뒤 인증 팝업을 닫는다.
     *
     * @param response 콜백 HTML을 기록할 HTTP 응답
     * @param callbackName 부모 창에 미리 정의된 JavaScript 함수명
     * @param args 콜백 함수에 순서대로 전달할 값
     * @throws IOException 응답을 기록하지 못한 경우
     */
    public static void callback(HttpServletResponse response, String callbackName, Object... args) throws IOException {
        // callback 함수명 검증
        if (callbackName == null || !callbackName.matches("[A-Za-z_$][A-Za-z0-9_$]*")) {
            throw new IllegalArgumentException("Invalid callback name");
        }

        StringBuilder script = new StringBuilder(256);
        script.append("<script>(function(){try{")
              .append("if(window.opener&&!window.opener.closed&&typeof window.opener.")
              .append(callbackName)
              .append("==='function'){window.opener.")
              .append(callbackName)
              .append('(');

        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                script.append(',');
            }
            script.append(toScriptSafeJson(args[i]));
        }

        script.append(");}}finally{window.close();}}());</script>");
        write(response, script.toString());
    }

    /**
     * 처리 결과를 알림창으로 표시하고 팝업을 닫는다.
     * 등록 성공처럼 부모 화면을 즉시 갱신해야 하는 경우에만 {@code reloadOpener}를 사용한다.
     *
     * @param response 콜백 HTML을 기록할 HTTP 응답
     * @param message 사용자에게 표시할 메시지
     * @param reloadOpener 팝업을 닫기 전에 부모 창을 새로고침할지 여부
     * @throws IOException 응답을 기록하지 못한 경우
     */
    public static void alertAndClose(HttpServletResponse response, String message, boolean reloadOpener) throws IOException {
        // alert 후 필요하면 부모 창 새로고침
        StringBuilder script = new StringBuilder(192);
        script.append("<script>(function(){alert(")
              .append(toScriptSafeJson(message))
              .append(");");
        if (reloadOpener) {
            script.append("if(window.opener&&!window.opener.closed){window.opener.location.reload();}");
        }
        script.append("window.close();}());</script>");
        write(response, script.toString());
    }

    /** JSON 문자열이 HTML의 script 요소 안에서도 데이터로만 해석되도록 보정한다. */
    private static String toScriptSafeJson(Object value) throws IOException {
        return OBJECT_MAPPER.writeValueAsString(value)
                .replace("<", "\\u003c")
                .replace(">", "\\u003e")
                .replace("&", "\\u0026")
                .replace("\u2028", "\\u2028")
                .replace("\u2029", "\\u2029");
    }

    /** 팝업 브라우저가 즉시 실행할 수 있도록 UTF-8 HTML 응답을 완성한다. */
    private static void write(HttpServletResponse response, String body) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(body);
        out.flush();
    }
}
