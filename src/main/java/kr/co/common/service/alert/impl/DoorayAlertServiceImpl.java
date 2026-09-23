package kr.co.common.service.alert.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.common.service.alert.DoorayAlertService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 사내 메신저(두레이 인커밍 웹훅) 알림 ( Implement )
 *
 *  전송 형식 : 두레이 인커밍 웹훅 JSON { botName, botIconImage, text } POST
 *  알림은 단일 데몬 스레드로 비동기 전송하며, 실패해도 경고 로그만 남기고
 *  호출한 업무 흐름에는 영향을 주지 않는다.
 *
 *  채널(웹훅 URL)/봇명은 업무별 상수로 상단에서 관리한다.
 *
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.21  김유승      최초생성 (SMS 차단 알림)
 * @ 2026.09.23  김유승      사이드메뉴 재전송차단 알림 추가, 업무별 알림 공통 서비스로 통합
 *
 * @author 김유승
 */
@Service("doorayAlertService")
public class DoorayAlertServiceImpl implements DoorayAlertService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();

    /** 알림 봇 아이콘(공통) */
    private static final String BOT_ICON_IMAGE = "https://static.dooray.com/static_images/dooray-bot.png";
    /** 연결 타임아웃(ms) */
    private static final int CONNECT_TIMEOUT_MS = 3000;
    /** 응답 타임아웃(ms) */
    private static final int READ_TIMEOUT_MS = 5000;

    /** SMS 차단 알림 사용여부 */
    private static final String SMS_BLOCK_USE_YN = "Y";
    /** SMS 차단 알림 웹훅 URL */
    private static final String SMS_BLOCK_HOOK_URL = "https://nhnent.dooray.com/services/3898710244970049535/4347934656978591610/8Sklf5SOTfGAeNvtIxI_wg";
    /** SMS 차단 알림 봇 이름 */
    private static final String SMS_BLOCK_BOT_NAME = "SMS차단알림봇";

    /** 사이드메뉴 재전송차단 알림 사용여부 */
    private static final String SIDEMENU_RESEND_USE_YN = "Y";
    /** 사이드메뉴 재전송차단 알림 웹훅 URL (채널 확정 시 교체) */
    private static final String SIDEMENU_RESEND_HOOK_URL = "https://nhnent.dooray.com/services/3898710244970049535/4332060267598961154/BZaRi-z-Tru2G04bmtRizg";
    /** 사이드메뉴 재전송차단 알림 봇 이름 */
    private static final String SIDEMENU_RESEND_BOT_NAME = "사이드복사차단알림봇";

    /** SMS 본문 미리보기 최대 길이 */
    private static final int PREVIEW_LEN = 50;

    /** 알림 전송용 단일 데몬 스레드 — 호출 업무 흐름과 분리 */
    private final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "dooray-alert");
            t.setDaemon(true);
            return t;
        }
    });

    @Override
    public void notifySmsBadwordBlock(String keywords, String msgContent, SessionInfoVO sessionInfoVO) {
        if (!"Y".equals(SMS_BLOCK_USE_YN)) {
            return;
        }
        send(SMS_BLOCK_HOOK_URL, SMS_BLOCK_BOT_NAME,
                "[SMS 차단] 금칙어 탐지"
                        + "\norgnCd: " + orgnInfo(sessionInfoVO)
                        + "\n탐지: " + keywords
                        + "\n본문: " + preview(msgContent));
    }

    @Override
    public void notifySmsUrlBlock(List<String> urlSummaries, String msgContent, SessionInfoVO sessionInfoVO) {
        if (!"Y".equals(SMS_BLOCK_USE_YN)) {
            return;
        }
        send(SMS_BLOCK_HOOK_URL, SMS_BLOCK_BOT_NAME,
                "[SMS 차단] URL 탐지"
                        + "\norgnCd: " + orgnInfo(sessionInfoVO)
                        + "\n탐지: " + String.join(", ", urlSummaries)
                        + "\n본문: " + preview(msgContent));
    }

    @Override
    public void notifySideMenuResendBlock(String reqType, String reqNonce, int reqCnt, SessionInfoVO sessionInfoVO) {
        if (!"Y".equals(SIDEMENU_RESEND_USE_YN)) {
            return;
        }
        send(SIDEMENU_RESEND_HOOK_URL, SIDEMENU_RESEND_BOT_NAME,
                "[사이드복사 재전송차단] " + sideMenuReqTypeNm(reqType)
                        + "\norgnCd: " + orgnInfo(sessionInfoVO)
                        + "\n건수: " + reqCnt
                        + "\nnonce: " + reqNonce);
    }

    /** 사이드메뉴 요청 종류 표기명 */
    private String sideMenuReqTypeNm(String reqType) {
        if ("CLASS_COPY".equals(reqType)) {
            return "분류복사";
        }
        if ("PROD_COPY".equals(reqType)) {
            return "상품복사";
        }
        return reqType;
    }

    /** 조직코드 / 사용자ID */
    private String orgnInfo(SessionInfoVO sessionInfoVO) {
        if (sessionInfoVO == null) {
            return "-";
        }
        return sessionInfoVO.getOrgnCd() + " / ID: " + sessionInfoVO.getUserId();
    }

    /** 본문 앞부분 미리보기 - 줄바꿈은 공백 치환, 앞 50자 초과분은 ... 처리 */
    private String preview(String msgContent) {
        if (msgContent == null || msgContent.isEmpty()) {
            return "";
        }
        String oneLine = msgContent.replaceAll("[\\r\\n]+", " ").trim();
        if (oneLine.length() <= PREVIEW_LEN) {
            return oneLine;
        }
        return oneLine.substring(0, PREVIEW_LEN) + "...";
    }

    /** 비동기 전송 요청 — 훅 URL 미설정이면 무시 */
    private void send(final String hookUrl, final String botName, final String text) {
        if (hookUrl == null || hookUrl.isEmpty()) {
            return;
        }
        executor.submit(new Runnable() {
            @Override
            public void run() {
                post(hookUrl, botName, text);
            }
        });
    }

    /** 두레이 웹훅 POST — 실패해도 경고 로그만 남긴다 */
    private void post(String hookUrl, String botName, String text) {
        HttpURLConnection connection = null;
        try {
            Map<String, String> payload = new LinkedHashMap<>();
            payload.put("botName", botName);
            payload.put("botIconImage", BOT_ICON_IMAGE);
            payload.put("text", text);
            byte[] body = objectMapper.writeValueAsBytes(payload);

            connection = (HttpURLConnection) new URL(hookUrl).openConnection();
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
            connection.setReadTimeout(READ_TIMEOUT_MS);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setFixedLengthStreamingMode(body.length);

            try (OutputStream output = connection.getOutputStream()) {
                output.write(body);
            }

            int status = connection.getResponseCode();
            if (status < 200 || status >= 300) {
                LOGGER.warn("DOORAY_ALERT >>> 알림 전송 실패 >>> botName={}, status={}", botName, status);
            }
        } catch (Exception e) {
            LOGGER.warn("DOORAY_ALERT >>> 알림 전송 실패 >>> botName={}, {}", botName, e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
