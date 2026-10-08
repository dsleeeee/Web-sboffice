package kr.co.common.utils.log;

import kr.co.common.utils.DateUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static kr.co.common.utils.HttpUtils.getClientIp;

/**
 * @Class Name : DirectAccessLogUtil.java
 * @Description : 백오피스 로그인 화면을 거치지 않는 직접 접속(POS 웹 로그인 등) 로그 파일 생성
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.10.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.10.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class DirectAccessLogUtil {

    private DirectAccessLogUtil() {
    }

    /**
     * 직접 접속 로그를 로그 파일에 출력
     *
     * @param title         로그 제목(접속 구분)
     * @param request       HTTP 요청
     * @param sessionUserId 요청 시점에 이미 존재하던 세션의 사용자ID (없으면 null)
     */
    public static void makeDirectAccessLog(String title, HttpServletRequest request, String sessionUserId) {

        try {
            String storeCd = request.getParameter("storeCd");
            String paramUserId = request.getParameter("userId");
            // 실제 로그인 대상 사용자ID (userId 미입력 시 매장코드 소문자로 로그인됨 - AuthServiceImpl.posLogin 참고)
            String userId = (paramUserId != null && !paramUserId.isEmpty()) ? paramUserId
                    : (storeCd == null ? "" : storeCd.toLowerCase());
            String currentDt = DateUtil.currentDateTimeString();
            String prefix = "_direct_access_" + userId + "," + currentDt + ",";

            StringBuilder log = new StringBuilder();
            log.append("----------_direct_access_").append(title).append(" START----------\n")
               .append(prefix).append("요청URL : ").append(request.getRequestURI()).append("\n")
               .append(prefix).append("Method : ").append(request.getMethod()).append("\n")
               .append(prefix).append("QueryString : ").append(request.getQueryString()).append("\n")
               .append(prefix).append("url : ").append(request.getParameter("url")).append("\n")
               .append(prefix).append("매장코드 : ").append(storeCd).append("\n")
               .append(prefix).append("사용자ID(파라미터) : ").append(paramUserId).append("\n")
               .append(prefix).append("사용자ID(로그인대상) : ").append(userId).append("\n")
               .append(prefix).append("하드웨어인증키 : ").append(request.getParameter("hwAuthKey")).append("\n")
               .append(prefix).append("기존세션 사용자ID : ").append(sessionUserId).append("\n")
               .append(prefix).append("접속IP : ").append(getClientIp(request)).append("\n")
               .append(prefix).append("RemoteAddr : ").append(request.getRemoteAddr()).append("\n")
               .append(prefix).append("User-Agent : ").append(request.getHeader("User-Agent")).append("\n")
               .append(prefix).append("referer : ").append(request.getHeader("referer")).append("\n")
               .append(prefix).append("Sec-Fetch-Site : ").append(request.getHeader("Sec-Fetch-Site")).append("\n")
               .append(prefix).append("Accept : ").append(request.getHeader("Accept")).append("\n")
               .append("----------_direct_access_").append(title).append(" END----------\n");

            String catalinaBase = System.getProperty("catalina.base");
            // 오늘 날짜
            String nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

            // 생성 파일 경로
            //String fileName = "D:\\log_test\\DIRECT_" + nowDate + ".OUT"; // TEST
            String fileName = catalinaBase + "/logs/DIRECT_" + nowDate + ".OUT";

            // 파일 객체 생성
            File file = new File(fileName);

            // true 지정시 파일의 기존 내용에 이어서 작성
            FileWriter fw = new FileWriter(file, true);

            // 파일안에 문자열 쓰기
            fw.write(log.toString());
            fw.flush();

            // 객체 닫기
            fw.close();
        } catch (Exception e) {
            // 로그 생성 실패가 로그인 처리에 영향을 주지 않도록 예외는 출력만 함
            e.printStackTrace();
        }
    }
}
