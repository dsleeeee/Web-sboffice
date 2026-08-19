package kr.co.solbipos.adi.sms.kcp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertException;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertRegistration;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertResult;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertService;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertTransaction;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import utils.Crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * NHN KCP 본인확인 V2의 거래등록과 서버 간 결과조회를 구현한다.
 * 거래등록 응답을 Redis 거래와 연결하고 콜백에서 인증결과를 조회·복호화한다.
 */
@Service("kcpCertService")
public class KcpCertServiceImpl implements KcpCertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KcpCertServiceImpl.class);
    private static final String SUCCESS_CODE = "0000";
    private static final String POPUP_SUBMIT_YN = "N";
    private static final int MAX_RESPONSE_BYTES = 1024 * 1024;
    /** 기존 CERT_ID 길이(21자)를 유지한다: 시각 14자 + 난수 7자. */
    private static final DateTimeFormatter ORDER_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final SecureRandom ORDER_RANDOM = new SecureRandom();
    private static final TypeReference<Map<String, Object>> MAP_TYPE =
            new TypeReference<Map<String, Object>>() { };

    /** 팝업 요청과 콜백을 연결하는 단기 Redis 거래 저장소. */
    private final KcpCertTransactionStore transactionStore;
    private final ObjectMapper objectMapper = new ObjectMapper();
    /** KCP 가맹점 정보, V2 API 주소 및 콜백/통신 설정은 실행 profile의 config에서 주입된다. */
    private final String siteCd;
    private final String webSiteId;
    private final String encKey;
    private final String registerUrl;
    private final String queryUrl;
    private final String returnBaseUrl;
    private final int connectTimeoutMs;
    private final int readTimeoutMs;

    /** 애플리케이션 시작 시 필수 연동 설정과 거래 저장소를 주입받는다. */
    @Autowired
    public KcpCertServiceImpl(KcpCertTransactionStore transactionStore,
                              @Value("#{config['kcp.cert.v2.siteCd']}") String siteCd,
                              @Value("#{config['kcp.cert.v2.webSiteId']}") String webSiteId,
                              @Value("#{config['kcp.cert.v2.encKey']}") String encKey,
                              @Value("#{config['kcp.cert.v2.registerUrl']}") String registerUrl,
                              @Value("#{config['kcp.cert.v2.queryUrl']}") String queryUrl,
                              @Value("#{config['kcp.cert.v2.returnBaseUrl']}") String returnBaseUrl,
                              @Value("#{config['kcp.cert.v2.connectTimeoutMs']}") int connectTimeoutMs,
                              @Value("#{config['kcp.cert.v2.readTimeoutMs']}") int readTimeoutMs) {
        this.transactionStore = transactionStore;
        this.siteCd = siteCd;
        this.webSiteId = webSiteId;
        this.encKey = encKey;
        this.registerUrl = registerUrl;
        this.queryUrl = queryUrl;
        this.returnBaseUrl = returnBaseUrl;
        this.connectTimeoutMs = connectTimeoutMs;
        this.readTimeoutMs = readTimeoutMs;
    }

    /** KCP 거래등록 API를 호출하고 팝업 호출값과 Redis 임시 거래를 생성한다. */
    @Override
    public KcpCertRegistration register(String purpose, String returnPath, SessionInfoVO sessionInfoVO) {
        // 인증 목적 검증
        validatePurpose(purpose);
        // 로그인 요청자 검증
        validateSession(sessionInfoVO);
        // KCP 연동 설정 검증
        validateConfiguration();

        // 내부 추적 ID와 KCP 주문번호는 별개다. 콜백의 실제 조회 기준은 reg_cert_key이다.
        String transactionId = UUID.randomUUID().toString().replace("-", "");
        // KCP 주문번호 생성
        String ordrIdxx = newOrderId();
        // KCP 콜백 URL 생성
        String returnUrl = makeReturnUrl(returnPath);

        // KCP 거래등록 요청 JSON 구성
        Map<String, Object> request = new LinkedHashMap<String, Object>();
        request.put("site_cd", siteCd);
        request.put("ordr_idxx", ordrIdxx);
        request.put("Ret_URL", returnUrl);
        if (StringUtils.hasText(webSiteId)) {
            request.put("web_siteid", webSiteId);
        }
        request.put("param_opt_1", transactionId);
        request.put("param_opt_2", "");
        request.put("param_opt_3", "");

        try {
            String requestJson = objectMapper.writeValueAsString(request);
            // KCP 거래등록 요청 암호화
            Map<?, ?> encrypted = Crypto.encryptJson(requestJson, encKey, siteCd);
            String encData = encrypted == null ? null : stringValue(encrypted.get("encData"));
            String rv = encrypted == null ? null : stringValue(encrypted.get("rv"));
            // 거래등록 암호문 검증
            requireText(encData, "KCP 거래등록 암호문이 생성되지 않았습니다.");
            // 거래등록 검증값 검증
            requireText(rv, "KCP 거래등록 검증값이 생성되지 않았습니다.");

            // KCP 거래등록 API 호출
            Map<String, Object> response = postJson(registerUrl, encData, rv);
            // KCP 거래등록 결과코드 검증
            verifyKcpSuccess(response, "거래등록");

            String callUrl = stringValue(response.get("call_url"));
            String regCertKey = stringValue(response.get("reg_cert_key"));
            // KCP 인증창 URL 검증
            requireHttpsUrl(callUrl, "KCP 인증창 URL");
            // KCP 거래등록키 검증
            requireText(regCertKey, "KCP 거래등록키가 없습니다.");

            // Redis에 보관할 요청자·목적·주문정보 구성
            long now = System.currentTimeMillis();
            KcpCertTransaction transaction = new KcpCertTransaction(
                    transactionId,
                    purpose,
                    sessionInfoVO.getUserId(),
                    sessionInfoVO.getOrgnCd(),
                    sessionInfoVO.getSessionId(),
                    ordrIdxx,
                    KcpCertTransaction.STATUS_PENDING,
                    now,
                    now + transactionStore.getTransactionTtlMillis());
            // Redis 거래정보 저장
            transactionStore.save(regCertKey, transaction);

            return new KcpCertRegistration(callUrl, regCertKey, ordrIdxx,
                    POPUP_SUBMIT_YN, transactionId);
        } catch (KcpCertException e) {
            throw e;
        } catch (Exception e) {
            throw new KcpCertException("KCP_REGISTRATION_ERROR", "KCP 본인확인 거래등록에 실패했습니다.", e);
        }
    }

    /** Redis 거래를 복원한 뒤 KCP 결과조회 API의 암호화 결과를 조회하고 복호화한다. */
    @Override
    public KcpCertResult getResult(String callbackRegCertKey, String expectedPurpose) {
        // Redis 거래정보 조회 및 인증 목적 검증
        KcpCertTransaction transaction = getTransaction(callbackRegCertKey, expectedPurpose);
        // 중복 콜백 처리권 획득
        String claimToken = transactionStore.claim(callbackRegCertKey);
        boolean success = false;

        try {
            // KCP 결과조회 요청 JSON 구성
            Map<String, Object> request = new LinkedHashMap<String, Object>();
            request.put("reg_cert_key", callbackRegCertKey);
            request.put("ordr_idxx", transaction.getOrdrIdxx());

            // KCP 인증결과 S2S 조회
            Map<String, Object> response = postJson(queryUrl, objectMapper.writeValueAsString(request), null);
            // KCP 결과조회 결과코드 검증
            verifyKcpSuccess(response, "결과조회");

            String encCertData = stringValue(response.get("enc_cert_data"));
            String rv = stringValue(response.get("rv"));
            // 인증결과 암호문 검증
            requireText(encCertData, "KCP 인증결과 암호문이 없습니다.");
            // 인증결과 검증값 검증
            requireText(rv, "KCP 인증결과 검증값이 없습니다.");

            // KCP 인증결과 복호화
            String decrypted = Crypto.decryptJson(encCertData, rv, encKey, siteCd);
            // 복호화 결과 검증
            requireText(decrypted, "KCP 인증결과를 복호화하지 못했습니다.");
            Map<String, Object> certData = objectMapper.readValue(decrypted, MAP_TYPE);
            // 운영 확인을 위해 사용 중인 암호화 응답/복호화 결과 로그는 기존 정책대로 유지한다.
            LOGGER.info("KCP V2 본인인증 결과 [인증목적={}, 주문번호={}] 암호화 응답={}, 복호화 결과={}",
                    transaction.getPurpose(), transaction.getOrdrIdxx(), response, decrypted);
            // DI 값 정규화
            normalizeDi(certData);

            // 처리권은 업무 DB 처리 완료 후 removeByRegCertKey()가 삭제한다.
            success = true;
            return new KcpCertResult(transaction.withStatus(KcpCertTransaction.STATUS_PROCESSING), certData);
        } catch (KcpCertException e) {
            throw e;
        } catch (Exception e) {
            throw new KcpCertException("KCP_RESULT_ERROR", "KCP 본인확인 결과조회에 실패했습니다.", e);
        } finally {
            if (!success) {
                // 실패한 콜백 처리권 해제
                transactionStore.release(callbackRegCertKey, claimToken);
            }
        }
    }

    /** {@code reg_cert_key}로 Redis 거래를 찾아 만료 여부와 인증 목적을 검증한다. */
    @Override
    public KcpCertTransaction getTransaction(String callbackRegCertKey, String expectedPurpose) {
        // 인증 목적 검증
        validatePurpose(expectedPurpose);
        // Redis 거래정보 조회
        KcpCertTransaction transaction = transactionStore.get(callbackRegCertKey);
        if (transaction == null || transaction.getExpiresAt() <= System.currentTimeMillis()) {
            throw new KcpCertException("TRANSACTION_NOT_FOUND", "인증 거래가 없거나 만료되었습니다.");
        }
        if (!expectedPurpose.equals(transaction.getPurpose())) {
            throw new KcpCertException("PURPOSE_MISMATCH", "인증 거래의 사용 목적이 일치하지 않습니다.");
        }
        return transaction;
    }

    /** 업무 처리가 끝난 Redis 거래정보와 중복 처리 방지 claim을 삭제한다. */
    @Override
    public void removeByRegCertKey(String callbackRegCertKey) {
        // Redis 거래정보와 처리권 삭제
        transactionStore.remove(callbackRegCertKey);
    }

    /** KCP 요청과 기존 CERT_ID에 사용할 21자리 주문번호를 생성한다. */
    @Override
    public String newOrderId() {
        // 기존 DB의 CERT_ID 규격과 호환되는 14자리 시각 + 7자리 난수 형식이다.
        String randomPart = String.format(Locale.ROOT, "%07d", ORDER_RANDOM.nextInt(10000000));
        return LocalDateTime.now().format(ORDER_TIME_FORMAT) + randomPart;
    }

    /**
     * KCP 거래등록·결과조회 API에 JSON을 전송한다.
     * HTTPS, timeout, redirect 금지와 응답 크기를 검사한다.
     */
    private Map<String, Object> postJson(String apiUrl, String body, String rv) {
        HttpURLConnection connection = null;
        try {
            // KCP API URL 검증
            requireHttpsUrl(apiUrl, "KCP API URL");
            byte[] requestBody = body.getBytes(StandardCharsets.UTF_8);
            connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(connectTimeoutMs);
            connection.setReadTimeout(readTimeoutMs);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("site_cd", siteCd);
            if (StringUtils.hasText(rv)) {
                connection.setRequestProperty("rv", rv);
            }
            connection.setFixedLengthStreamingMode(requestBody.length);

            try (OutputStream output = connection.getOutputStream()) {
                output.write(requestBody);
            }

            int status = connection.getResponseCode();
            if (status < 200 || status >= 300) {
                // KCP 오류 응답 스트림 정리
                closeQuietly(connection.getErrorStream());
                throw new KcpCertException("KCP_HTTP_ERROR",
                        "KCP API가 HTTP " + status + " 응답을 반환했습니다.");
            }

            // KCP JSON 응답 크기 제한 적용
            String responseBody = readLimited(connection.getInputStream());
            if (!StringUtils.hasText(responseBody)) {
                throw new KcpCertException("KCP_EMPTY_RESPONSE", "KCP API 응답이 비어 있습니다.");
            }
            return objectMapper.readValue(responseBody, MAP_TYPE);
        } catch (KcpCertException e) {
            throw e;
        } catch (SocketTimeoutException e) {
            throw new KcpCertException("KCP_TIMEOUT", "KCP API 응답 시간이 초과되었습니다.", e);
        } catch (IOException e) {
            throw new KcpCertException("KCP_COMMUNICATION_ERROR", "KCP API 통신에 실패했습니다.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /** 비정상적으로 큰 외부 응답이 메모리를 점유하지 않도록 최대 크기까지만 읽는다. */
    private String readLimited(InputStream input) throws IOException {
        try (InputStream stream = input; ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int total = 0;
            int count;
            while ((count = stream.read(buffer)) != -1) {
                total += count;
                if (total > MAX_RESPONSE_BYTES) {
                    throw new KcpCertException("KCP_RESPONSE_TOO_LARGE", "KCP API 응답 크기가 허용 범위를 초과했습니다.");
                }
                output.write(buffer, 0, count);
            }
            return new String(output.toByteArray(), StandardCharsets.UTF_8);
        }
    }

    /** 기존 HTTP 오류를 덮지 않도록 응답 스트림을 조용히 닫는다. */
    private void closeQuietly(InputStream input) {
        if (input != null) {
            try {
                input.close();
            } catch (IOException ignored) {
                // 오류 응답 스트림 정리 실패는 원래 HTTP 오류를 대체하지 않는다.
            }
        }
    }

    /** KCP 공통 결과코드가 성공({@code 0000})인지 확인하고 원래 결과코드를 보존한다. */
    private void verifyKcpSuccess(Map<String, Object> response, String operation) {
        String resultCode = stringValue(response.get("res_cd"));
        if (!SUCCESS_CODE.equals(resultCode)) {
            String resultMessage = stringValue(response.get("res_msg"));
            throw new KcpCertException("KCP_REJECTED",
                    "KCP " + operation + "에 실패했습니다." +
                            (StringUtils.hasText(resultMessage) ? " " + resultMessage : ""),
                    resultCode);
        }
    }

    /** V2 응답의 URL 인코딩된 DI를 가이드 규격에 따라 복원한다. */
    private void normalizeDi(Map<String, Object> certData) throws IOException {
        String encodedDi = stringValue(certData.get("DI_URL"));
        if (!StringUtils.hasText(encodedDi)) {
            encodedDi = stringValue(certData.get("di_url"));
        }
        if (StringUtils.hasText(encodedDi)) {
            certData.put("DI", URLDecoder.decode(encodedDi, StandardCharsets.UTF_8.name()));
        }
    }

    /** 환경별 기준 주소와 화면별 콜백 경로를 결합해 KCP {@code Ret_URL}을 만든다. */
    private String makeReturnUrl(String returnPath) {
        // 콜백 상대경로 검증
        if (!StringUtils.hasText(returnPath) || !returnPath.startsWith("/") || returnPath.startsWith("//") ||
                returnPath.indexOf('\r') >= 0 || returnPath.indexOf('\n') >= 0) {
            throw new KcpCertException("INVALID_RETURN_PATH", "KCP 콜백 경로가 올바르지 않습니다.");
        }
        String base = returnBaseUrl.endsWith("/")
                ? returnBaseUrl.substring(0, returnBaseUrl.length() - 1)
                : returnBaseUrl;
        String returnUrl = base + returnPath;
        // 완성된 콜백 URL 검증
        requireCallbackUrl(returnUrl, "KCP 콜백 URL");
        return returnUrl;
    }

    /** 요청한 인증 목적이 지원하는 SMS 본인인증 흐름인지 확인한다. */
    private void validatePurpose(String purpose) {
        if (!PURPOSE_SMS_USER_REGIST.equals(purpose) &&
                !PURPOSE_SMS_TEL_NO_REGIST.equals(purpose) &&
                !PURPOSE_MARKETING_VERIFY.equals(purpose) &&
                !PURPOSE_MARKETING_VERIFY2.equals(purpose)) {
            throw new KcpCertException("INVALID_PURPOSE", "지원하지 않는 본인확인 사용 목적입니다.");
        }
    }

    /** Redis 거래에 보관할 로그인 사용자와 소속 정보가 있는지 확인한다. */
    private void validateSession(SessionInfoVO sessionInfoVO) {
        if (sessionInfoVO == null || !StringUtils.hasText(sessionInfoVO.getUserId()) ||
                !StringUtils.hasText(sessionInfoVO.getOrgnCd())) {
            throw new KcpCertException("INVALID_SESSION", "본인확인을 요청한 사용자 정보가 없습니다.");
        }
    }

    /** KCP 거래등록 전에 필수 가맹점 정보, API 주소와 타임아웃 설정을 검증한다. */
    private void validateConfiguration() {
        // KCP 사이트코드 검증
        requireText(siteCd, "KCP 사이트코드가 설정되지 않았습니다.");
        // KCP 인증키 검증
        requireText(encKey, "KCP 인증키가 설정되지 않았습니다.");
        // KCP 거래등록 URL 검증
        requireHttpsUrl(registerUrl, "KCP 거래등록 URL");
        // KCP 결과조회 URL 검증
        requireHttpsUrl(queryUrl, "KCP 결과조회 URL");
        // KCP 콜백 기준 URL 검증
        requireCallbackUrl(returnBaseUrl, "KCP 콜백 기준 URL");
        if (connectTimeoutMs <= 0 || readTimeoutMs <= 0) {
            throw new KcpCertException("INVALID_CONFIGURATION", "KCP API 타임아웃 설정이 올바르지 않습니다.");
        }
    }

    /** KCP API 주소가 호스트를 포함한 HTTPS URL인지 검증한다. */
    private void requireHttpsUrl(String value, String name) {
        // 필수 URL 값 검증
        requireText(value, name + "이(가) 없습니다.");
        try {
            URL url = new URL(value);
            if (!"https".equalsIgnoreCase(url.getProtocol()) || !StringUtils.hasText(url.getHost())) {
                throw new KcpCertException("INVALID_CONFIGURATION", name + "은(는) HTTPS URL이어야 합니다.");
            }
        } catch (IOException e) {
            throw new KcpCertException("INVALID_CONFIGURATION", name + "이(가) 올바르지 않습니다.", e);
        }
    }

    /** 콜백 URL은 HTTPS만 허용하고 로컬 테스트용 HTTP loopback만 예외로 허용한다. */
    private void requireCallbackUrl(String value, String name) {
        // 필수 콜백 URL 값 검증
        requireText(value, name + "이(가) 없습니다.");
        try {
            URL url = new URL(value);
            String protocol = url.getProtocol();
            String host = url.getHost();
            boolean localHttp = "http".equalsIgnoreCase(protocol) &&
                    ("localhost".equalsIgnoreCase(host) || "127.0.0.1".equals(host) || "::1".equals(host));
            if ((!"https".equalsIgnoreCase(protocol) && !localHttp) || !StringUtils.hasText(host)) {
                throw new KcpCertException("INVALID_CONFIGURATION",
                        name + "은(는) HTTPS URL이어야 하며, 로컬 테스트에서만 HTTP loopback URL을 사용할 수 있습니다.");
            }
        } catch (IOException e) {
            throw new KcpCertException("INVALID_CONFIGURATION", name + "이(가) 올바르지 않습니다.", e);
        }
    }

    /** KCP 처리에 필요한 문자열이 비어 있으면 지정된 오류로 중단한다. */
    private void requireText(String value, String message) {
        if (!StringUtils.hasText(value)) {
            throw new KcpCertException("INVALID_KCP_DATA", message);
        }
    }

    /** KCP JSON 값을 null-safe 문자열로 변환한다. */
    private String stringValue(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
}
