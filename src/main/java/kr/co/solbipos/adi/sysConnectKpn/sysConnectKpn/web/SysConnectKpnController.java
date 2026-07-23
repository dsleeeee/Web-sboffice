package kr.co.solbipos.adi.sysConnectKpn.sysConnectKpn.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.JsonException;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.adi.sysConnectKpn.sysConnectKpn.service.SysConnectKpnService;
import kr.co.solbipos.adi.sysConnectKpn.sysConnectKpn.service.SysConnectKpnVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sys.link.omsLinkSample.service.ApiLinkVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static kr.co.common.utils.DateUtil.currentDateTimeString;


/**
 * @Class Name : SysConnectKpnController.java
 * @Description : 부가서비스 > 정산 > KPN시스템접속
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.15  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sysConnectKpn/sysConnectKpn")
public class SysConnectKpnController {

    /** TB_CM_API_LINK_INFO 에서 KPN 연동정보를 찾기 위한 API 번호 */
//    private static final String KPN_API_NO = "0026"; // 개발
    private static final String KPN_API_NO = "0027";  // 운영
    /** KPN 토큰발급 API 요청 header 의 서비스 코드 (KPN 측 고정값) */
    private static final String KPN_SVC_CD = "pascer01u0";

    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;
    private final SysConnectKpnService sysConnectKpnService;

    /**
     *  Constructor Injection
     */
    public SysConnectKpnController(SessionService sessionService, CmmEnvUtil cmmEnvUtil, SysConnectKpnService sysConnectKpnService) {
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.sysConnectKpnService = sysConnectKpnService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/sysConnectKpn/sysConnectKpn/sysConnectKpn";
    }

    /**
     * KPN 접속 URL 발급
     * 캐시된 통합인증토큰이 유효하면 그대로, 없거나 만료됐으면 새로 발급받아 usr_id/token 파라미터를 붙인 접속 URL을 만들어 반환한다.
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/getConnectUrl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getConnectUrl(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String kpnBackOfficeUrl = getKpnBackOfficeUrl(sessionInfoVO);
        if (CmmUtil.nvl(kpnBackOfficeUrl, "").isEmpty()) {
            throw new JsonException(Status.SERVER_ERROR, "KPN 백오피스 URL(envst1355)이 설정되지 않았습니다.");
        }

        String token;
        try {
            token = getKpnToken(sessionInfoVO);
        } catch (JsonException je) {
            throw je;
        } catch (Exception e) {
            // e.getMessage()가 null인 예외(예: NPE)도 있어서, 그 경우 안내 메시지로 대체
            String message = CmmUtil.nvl(e.getMessage(), "KPN API 호출 중 오류가 발생했습니다.");
            throw new JsonException(Status.SERVER_ERROR, message);
        }

        String userId = "0001";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            userId += CmmUtil.nvl(sessionInfoVO.getHqOfficeCd(), "");
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            userId += CmmUtil.nvl(sessionInfoVO.getStoreCd(), "");
        }

        try {
            resultMap.put("url", buildKpnConnectUrl(kpnBackOfficeUrl, token, userId));
        } catch (Exception e) {
            throw new JsonException(Status.SERVER_ERROR, "접속 URL 생성에 실패했습니다.");
        }

        return ReturnUtil.returnJson(Status.OK, resultMap);
    }

    /**
     * 환경설정(envst1355) KPN 백오피스 URL 조회
     * 접속 시점에 매번 최신값을 조회한다 (페이지 로드시 값을 클라이언트에 들고 있다가 다시 보내는 방식은 지양).
     */
    private String getKpnBackOfficeUrl(SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            return CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1355"), "");
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            return CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1355"), "");
        }

        return "";
    }

    /**
     * KPN 통합인증토큰 조회
     * 1) TB_CM_API_LINK_INFO에서 캐시된 토큰/만료일시를 읽어 현재시각과 비교
     * 2) 아직 유효하면 캐시된 토큰을 그대로 반환
     * 3) 만료(또는 최초)면 KPN 토큰발급 API를 호출해 새 토큰을 받고, DB 캐시를 갱신한 뒤 반환
     */
    private String getKpnToken(SessionInfoVO sessionInfoVO) throws Exception {

        SysConnectKpnVO sysConnectKpnVO = new SysConnectKpnVO();
        sysConnectKpnVO.setApiNo(KPN_API_NO);

        DefaultMap<Object> apiInfo = sysConnectKpnService.getKpnApiInfo(sysConnectKpnVO, sessionInfoVO);
        if (apiInfo == null) {
            throw new IllegalStateException("KPN 연동정보(API_NO=" + KPN_API_NO + ")가 등록되어 있지 않습니다.");
        }

        // 캐시된 토큰이 만료되지 않았으면 그대로 재사용
        String cachedToken = CmmUtil.nvl(apiInfo.getStr("intgCerTkn"), "");
        String expireDateTime = CmmUtil.nvl(apiInfo.getStr("intgCerTknDdlDate"), "")
                + CmmUtil.nvl(apiInfo.getStr("intgCerTknDdlTime"), "");
        String currentDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        if (!cachedToken.isEmpty() && expireDateTime.length() == 14 && currentDateTime.compareTo(expireDateTime) < 0) {
            return cachedToken;
        }

        // 캐시 만료/없음 : KPN 토큰발급 API 새로 호출
        String apiUrl = apiInfo.getStr("apiUrl");
        String usno = apiInfo.getStr("usno");
        String apiSecretKey = apiInfo.getStr("accessToken");
        String apiAuthKey = apiInfo.getStr("secretKey");

        if (CmmUtil.nvl(apiUrl, "").isEmpty()) {
            throw new IllegalArgumentException("KPN 토큰발급 API URL이 설정되지 않았습니다.");
        }

        if (CmmUtil.nvl(usno, "").isEmpty()) {
            throw new IllegalArgumentException("KPN 사용자번호(usno)가 설정되지 않았습니다.");
        }

        if (CmmUtil.nvl(apiSecretKey, "").isEmpty() || CmmUtil.nvl(apiAuthKey, "").isEmpty()) {
            throw new IllegalArgumentException("KPN API 키가 설정되지 않았습니다.");
        }

        String apiCerTknIsnReqKey = createApiCerTknIsnReqKey(apiSecretKey, apiAuthKey);
        Map<String, Object> requestMap = createKpnTokenRequestBody(usno, apiCerTknIsnReqKey);

        // KPN 토큰발급 API 호출 시도(성공/실패 무관)를 TB_CM_API_LINK 에 이력으로 남긴다.
        ApiLinkVO apiLinkVO = new ApiLinkVO();
        apiLinkVO.setLinkType("KPN_TOKEN");
        Map<String, Object> responseMap;
        try {
            responseMap = postJson(apiUrl, requestMap, apiLinkVO);
        } finally {
            sysConnectKpnService.saveApiLog(apiLinkVO, sessionInfoVO);
        }

        Map<String, Object> rspHdr = (Map<String, Object>) responseMap.get("rspHdr");
        if (rspHdr == null || !"0".equals(String.valueOf(rspHdr.get("rc")))) {
            String message = "KPN 토큰 발급에 실패했습니다.";
            if (rspHdr != null && rspHdr.get("splmMsg") != null) {
                message = String.valueOf(rspHdr.get("splmMsg"));
            }
            throw new IllegalStateException(message);
        }

        Map<String, Object> rspBody = (Map<String, Object>) responseMap.get("rspBody");
        if (rspBody == null) {
            throw new IllegalStateException("KPN 토큰 발급 응답 데이터가 없습니다.");
        }

        Object token = rspBody.get("intgCerTkn");

        if (token == null || CmmUtil.nvl(String.valueOf(token), "").isEmpty()) {
            throw new IllegalStateException("KPN 통합인증토큰이 응답에 없습니다.");
        }

        // 신규 발급 토큰 정보 캐시(DB) 갱신
        SysConnectKpnVO updateVO = new SysConnectKpnVO();
        updateVO.setApiNo(KPN_API_NO);
        updateVO.setIntgCerTkn(String.valueOf(token));
        updateVO.setIntgCerTknPblcDate(String.valueOf(rspBody.get("intgCerTknPblcDate")));
        updateVO.setIntgCerTknPblcTime(String.valueOf(rspBody.get("intgCerTknPblcTime")));
        updateVO.setIntgCerTknDdlDate(String.valueOf(rspBody.get("intgCerTknDdlDate")));
        updateVO.setIntgCerTknDdlTime(String.valueOf(rspBody.get("intgCerTknDdlTime")));
        sysConnectKpnService.updateKpnToken(updateVO, sessionInfoVO);

        return String.valueOf(token);
    }

    /**
     * KPN 토큰 발급요청키(apiCerTknIsnReqKey) 생성
     * "현재일시(yyyyMMddHHmmss) + '.' + SHA-256(현재일시+api비밀키+api인증키)" 형식의 KPN 인증 규격
     */
    private String createApiCerTknIsnReqKey(String apiSecretKey, String apiAuthKey) throws Exception {
        String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return dateTime + "." + sha256Hex(dateTime + apiSecretKey + apiAuthKey);
    }

    /**
     * KPN 토큰 발급 API 요청 body 생성
     * rqsHdr(서비스코드 등 고정값)과 rqsBody(사용자번호, 발급요청키)를 KPN 스펙에 맞춰 Map으로 구성한다.
     */
    private Map<String, Object> createKpnTokenRequestBody(String usno, String apiCerTknIsnReqKey) {

        Map<String, Object> requestMap = new HashMap<String, Object>();

        Map<String, Object> rqsHdr = new HashMap<String, Object>();
        rqsHdr.put("svcCd", KPN_SVC_CD);
        rqsHdr.put("uiCd", "0000000000");
        rqsHdr.put("nocerSvcYn", "Y");
        rqsHdr.put("cerTpcd", "00");
        rqsHdr.put("rqsTpcd", "1");
        rqsHdr.put("rqsTrmDscd", "S");

        Map<String, Object> rqsBody = new HashMap<String, Object>();
        rqsBody.put("usrSvcId", "");
        rqsBody.put("usno", usno);
        rqsBody.put("usrPwd", "");
        rqsBody.put("apiCerTknIsnReqKey", apiCerTknIsnReqKey);

        requestMap.put("rqsHdr", rqsHdr);
        requestMap.put("rqsBody", rqsBody);

        return requestMap;
    }

    /**
     * 외부 API로 JSON POST 호출 후 응답을 Map으로 파싱해서 리턴
     * 연결 5초 / 응답대기 10초 타임아웃, HTTP 에러 응답도 body를 읽어서 그대로 반환한다.
     * apiLinkVO 에 url/요청/응답/상태코드/시각을 채워서 호출부에서 saveApiLog로 이력을 남길 수 있게 한다.
     */
    private Map<String, Object> postJson(String apiUrl, Map<String, Object> requestMap, ApiLinkVO apiLinkVO) throws Exception {

        HttpURLConnection connection = null;
        ObjectMapper mapper = new ObjectMapper();

        apiLinkVO.setUrl(apiUrl);
        apiLinkVO.setRequestMethod("POST");

        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);

            String jsonData = mapper.writeValueAsString(requestMap);
            apiLinkVO.setRequest(jsonData);
            apiLinkVO.setRequestDt(currentDateTimeString());

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
            }

            int responseCode = connection.getResponseCode();
            apiLinkVO.setResponseDt(currentDateTimeString());
            apiLinkVO.setStatusCode(String.valueOf(responseCode));

            BufferedReader br;
            // 응답코드는 무조건 200으로 오고 rspHdr.rc로 오류여부 확인
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                InputStream errorStream = connection.getErrorStream();
                if (errorStream == null) {
                    throw new IllegalStateException("KPN API 호출 실패. HTTP Status: " + responseCode);
                }
                br = new BufferedReader(new InputStreamReader(errorStream, StandardCharsets.UTF_8));
            }

            try (BufferedReader reader = br) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = reader.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                apiLinkVO.setResponse(response.toString());

                if (response.length() == 0) {
                    throw new IllegalStateException("KPN API 응답이 없습니다. HTTP Status: " + responseCode);
                }

                return mapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {});
            }

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * KPN 백오피스 URL(envst1355)에 token/usr_id 쿼리파라미터를 붙여 최종 접속 URL을 만든다.
     * baseUrl에 이미 '?'가 있으면 '&'로, 없으면 '?'로 이어붙인다.
     */
    private String buildKpnConnectUrl(String baseUrl, String token, String userId) throws Exception {

        StringBuilder sb = new StringBuilder(baseUrl);
        sb.append(baseUrl.contains("?") ? "&" : "?");
        sb.append("token=").append(URLEncoder.encode(token, StandardCharsets.UTF_8.toString()));
        sb.append("&usr_id=").append(URLEncoder.encode(userId, StandardCharsets.UTF_8.toString()));

        return sb.toString();
    }


    /**
     * 문자열을 SHA-256으로 해시한 뒤 소문자 16진수 문자열로 변환한다.
     * (createApiCerTknIsnReqKey 에서 발급요청키 생성용으로 사용)
     */
    private String sha256Hex(String plainText) throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
